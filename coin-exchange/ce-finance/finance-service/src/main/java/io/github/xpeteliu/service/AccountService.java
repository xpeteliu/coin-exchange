package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.entity.Account;
import io.github.xpeteliu.entity.AccountDetail;
import io.github.xpeteliu.entity.Coin;
import io.github.xpeteliu.entity.Config;
import io.github.xpeteliu.feign.MarketServiceFeignClient;
import io.github.xpeteliu.model.SymbolAssetResult;
import io.github.xpeteliu.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountDetailService accountDetailService;

    @Autowired
    CoinService coinService;

    @Autowired
    ConfigService configService;

    @Autowired
    MarketServiceFeignClient marketServiceFeignClient;

    public Account findCoinAccount(Long userId, Long coinId) {
        return accountRepository.findByUserIdAndCoinId(userId, coinId);
    }

    @Transactional
    public void transferToAccount(Long auditorUserId, Long userId, Long coinId, Long orderId,
                                  BigDecimal num, BigDecimal fee, String remark,
                                  String businessType, Integer direction) {
        Account coinAccount = findCoinAccount(userId, coinId);
        if (coinAccount == null) {
            throw new IllegalArgumentException("Non-existent coin account");
        }

        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setAccountId(coinAccount.getId());
        accountDetail.setRefAccountId(auditorUserId);
        accountDetail.setUserId(userId);
        accountDetail.setCoinId(coinId);
        accountDetail.setAmount(num);
        accountDetail.setFee(fee);
        accountDetail.setOrderId(orderId);
        accountDetail.setRemark(remark);
        accountDetail.setBusinessType(businessType);
        accountDetail.setDirection(direction);
        accountDetailService.save(accountDetail);

        coinAccount.setBalanceAmount(coinAccount.getBalanceAmount().add(num));
        accountRepository.save(coinAccount);
    }

    public Account findCoinAccountWithCoinName(String coinName) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Coin coin = coinService.findByName(coinName);
        Account account = findCoinAccount(userId, coin.getId());
        if (account == null) {
            throw new IllegalArgumentException("Non-existent account");
        }
        Config sellRateConfig = configService.findByCode("USDT2CNY");
        account.setSellRate(new BigDecimal(sellRateConfig.getValue()));
        Config buyRateConfig = configService.findByCode("CNY2USDT");
        account.setBuyRate(new BigDecimal(buyRateConfig.getValue()));
        return account;
    }

    public void freezeAccountBalance(Long userId, Long coinId, BigDecimal mum, String type, Long orderId, BigDecimal fee) {
        Account account = accountRepository.findByUserIdAndCoinId(userId, coinId);
        if (account == null) {
            throw new IllegalArgumentException("Non-existent account");
        }
        BigDecimal balanceAmount = account.getBalanceAmount();
        if (balanceAmount.compareTo(mum) < 0) {
            throw new IllegalArgumentException("Balance not enough");
        }
        account.setBalanceAmount(balanceAmount.subtract(mum));
        account.setFreezeAmount(account.getFreezeAmount().add(mum));
        accountRepository.save(account);

        AccountDetail accountDetail = new AccountDetail(
                null,
                userId,
                coinId,
                account.getId(),
                null,
                orderId,
                2,
                type,
                mum,
                fee,
                "Freeze Balance",
                null
        );
        accountDetailService.save(accountDetail);
    }

    public SymbolAssetResult findSymbolAsset(String symbol) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MarketDto marketDto = marketServiceFeignClient.findBySymbol(symbol);

        SymbolAssetResult symbolAssetResult = new SymbolAssetResult();

        @NotNull Long buyCoinId = marketDto.getBuyCoinId();
        Account buyCoinAccount = findCoinAccount(userId, buyCoinId);
        symbolAssetResult.setBuyAmount(buyCoinAccount.getBalanceAmount());
        symbolAssetResult.setBuyLockAmount(buyCoinAccount.getFreezeAmount());

        symbolAssetResult.setBuyFeeRate(marketDto.getFeeBuy());
        Coin buyCoin = coinService.findById(buyCoinId);
        symbolAssetResult.setBuyUnit(buyCoin.getName());

        @NotBlank Long sellCoinId = marketDto.getSellCoinId();
        Account coinAccount = findCoinAccount(userId, sellCoinId);
        symbolAssetResult.setSellAmount(coinAccount.getBalanceAmount());
        symbolAssetResult.setSellLockAmount(coinAccount.getFreezeAmount());

        symbolAssetResult.setSellFeeRate(marketDto.getFeeSell());
        Coin sellCoin = coinService.findById(sellCoinId);
        symbolAssetResult.setSellUnit(sellCoin.getName());

        return symbolAssetResult;
    }

    public void transferBuyAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        Account fromAccount = findCoinAccount(coinId, fromUserId);
        if (fromAccount == null) {
            log.error("资金划转-Invalid coin account，userId:{}, coinId:{}", fromUserId, coinId);
            throw new IllegalArgumentException("Invalid coin account");
        } else {
            Account toAccount = findCoinAccount(toUserId, coinId);
            if (toAccount == null) {
                throw new IllegalArgumentException("Invalid coin account");
            } else {
                decreaseAmount(fromAccount, amount);
                addAmount(toAccount, amount);

                List<AccountDetail> accountDetails = new ArrayList(2);
                AccountDetail fromAccountDetail = new AccountDetail(null, fromUserId, coinId, fromAccount.getId(), toAccount.getId(), orderId, 2, businessType, amount, BigDecimal.ZERO, businessType, null);
                AccountDetail toAccountDetail = new AccountDetail(null, toUserId, coinId, toAccount.getId(), fromAccount.getId(), orderId, 1, businessType, amount, BigDecimal.ZERO, businessType, null);
                accountDetails.add(fromAccountDetail);
                accountDetails.add(toAccountDetail);

                accountDetailService.savaAll(accountDetails);
            }
        }
    }

    public void transferSellAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        Account fromAccount = findCoinAccount(coinId, fromUserId);
        if (fromAccount == null) {
            log.error("Invalid coin account，userId:{}, coinId:{}", fromUserId, coinId);
            throw new IllegalArgumentException("Invalid coin account");
        } else {
            Account toAccount = findCoinAccount(toUserId, coinId);
            if (toAccount == null) {
                throw new IllegalArgumentException("Invalid coin account");
            } else {
                addAmount(fromAccount, amount);
                decreaseAmount(toAccount, amount);
                List<AccountDetail> accountDetails = new ArrayList(2);
                AccountDetail fromAccountDetail = new AccountDetail(null, fromUserId, coinId, fromAccount.getId(), toAccount.getId(), orderId, 2, businessType, amount, BigDecimal.ZERO, businessType, null);
                AccountDetail toAccountDetail = new AccountDetail(null, toUserId, coinId, toAccount.getId(), fromAccount.getId(), orderId, 1, businessType, amount, BigDecimal.ZERO, businessType, null);
                accountDetails.add(fromAccountDetail);
                accountDetails.add(toAccountDetail);

                accountDetailService.savaAll(accountDetails);
            }
        }
    }

    private void addAmount(Account account, BigDecimal amount) {
        account.setBalanceAmount(account.getBalanceAmount().add(amount));
        accountRepository.save(account);
    }

    private void decreaseAmount(Account account, BigDecimal amount) {
        account.setBalanceAmount(account.getBalanceAmount().subtract(amount));
        accountRepository.save(account);
    }

}
