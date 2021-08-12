package io.github.xpeteliu.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.querydsl.core.BooleanBuilder;
import io.github.xpeteliu.dto.AdminBankDto;
import io.github.xpeteliu.entity.*;
import io.github.xpeteliu.feign.AdminBankServiceFeign;
import io.github.xpeteliu.feign.UserServiceFeign;
import io.github.xpeteliu.model.CashParam;
import io.github.xpeteliu.model.CashTradeResult;
import io.github.xpeteliu.repository.CashRechargeAuditRecordRepository;
import io.github.xpeteliu.repository.CashRechargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CashRechargeService {

    @Autowired
    CashRechargeRepository cashRechargeRepository;

    @Autowired
    UserServiceFeign userServiceFeign;

    @Autowired
    AccountService accountService;

    @Autowired
    CoinService coinService;

    @Autowired
    CashRechargeAuditRecordRepository cashRechargeAuditRecordRepository;

    @Autowired
    AdminBankServiceFeign adminBankServiceFeign;

    @Autowired
    Snowflake snowflake;

    @Autowired
    ConfigService configService;

    @CreateCache(name = "CASH_RECHARGE_LOCK:", expire = 100, cacheType = CacheType.BOTH)
    Cache<String, String> cache;

    public Page<CashRecharge> findWithPaging(
            Long coinId, Long userId, String userName,
            String mobile, Integer status, String numMin, String numMax,
            String startTime, String endTime, Pageable pageable) {

        QCashRecharge cashRecharge = QCashRecharge.cashRecharge;
        BooleanBuilder predicate = new BooleanBuilder();
        if (userId != null || StringUtils.hasLength(userName) || StringUtils.hasLength(mobile)) {
            Set<Long> ids = userServiceFeign.getUserBasics(
                    userId == null ? null : Collections.singletonList(userId),
                    userName, mobile)
                    .keySet();
            if (!CollectionUtils.isEmpty(ids)) {
                predicate.and(cashRecharge.userId.in(ids));
            }
        }
        if (coinId != null) {
            predicate.and(cashRecharge.coinId.eq(coinId));
        }
        if (status != null) {
            predicate.and(cashRecharge.status.eq(status));
        }
        if (numMin != null) {
            predicate.and(cashRecharge.num.goe(new BigDecimal(numMin)));
        }
        if (numMax != null) {
            predicate.and(cashRecharge.num.loe(new BigDecimal(numMax)));
        }
        if (startTime != null) {
            predicate.and(cashRecharge.created.after(Timestamp.valueOf(startTime + " 00:00:00")));
        }
        if (endTime != null) {
            predicate.and(cashRecharge.created.before(Timestamp.valueOf(endTime + " 23:59:59")));
        }
        return cashRechargeRepository.findAll(predicate, pageable);
    }

    public void cashRechargeUpdateStatus(Long userId, CashRechargeAuditRecord cashRechargeAuditRecord) {
        Long cashRechargeAuditRecordId = cashRechargeAuditRecord.getId();
        cache.tryLockAndRun(cashRechargeAuditRecordId.toString(), 300, TimeUnit.SECONDS, () -> {
            CashRecharge cashRecharge = cashRechargeRepository.findById(cashRechargeAuditRecordId).orElse(null);
            if (cashRecharge == null) {
                throw new IllegalArgumentException("Non-existent cashRechargeAuditRecord");
            }

            if (cashRecharge.getStatus() == 1) {
                throw new IllegalArgumentException("Auditing already completed");
            }

            Integer auditResult = cashRechargeAuditRecord.getStatus();
            CashRechargeAuditRecord cashRechargeAuditRecordDb = new CashRechargeAuditRecord();

            cashRechargeAuditRecordDb.setAuditUserId(userId);
            cashRechargeAuditRecordDb.setStatus(auditResult);
            cashRechargeAuditRecordDb.setRemark(cashRechargeAuditRecord.getRemark());
            cashRechargeAuditRecordDb.setStep(cashRecharge.getStep() + 1);
            cashRechargeAuditRecordRepository.save(cashRechargeAuditRecordDb);

            cashRecharge.setStatus(auditResult);
            cashRecharge.setAuditRemark(cashRechargeAuditRecord.getRemark());
            cashRecharge.setStep(cashRecharge.getStep() + 1);

            if (auditResult != 2) {
                accountService.transferToAccount(userId, cashRecharge.getUserId(), cashRecharge.getCoinId(),
                        cashRecharge.getId(), cashRecharge.getNum(), cashRecharge.getFee(),
                        "RECHARGE", "recharge_into", 1);
                cashRecharge.setLastTime(new Date());
            }

            cashRechargeRepository.save(cashRecharge);
        });
    }

    public Page<CashRecharge> findUserRecordWithPaging(Long userId, Integer status, Pageable pageable) {
        return cashRechargeRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public CashTradeResult buy(Long userId, CashParam cashParam) {
        List<AdminBankDto> adminBanks = adminBankServiceFeign.findAllAdminBanks();
        AdminBankDto adminBank = loadBalance(adminBanks);
        String orderId = String.valueOf(snowflake.nextId());

        // we provide a 6-digit code to the payer,
        // and the payer should put the code in the payment remark
        // so that we can identify the payer
        String remark = RandomUtil.randomNumbers(6);

        Coin coin = coinService.findById(cashParam.getCoinId());

        if (coin == null) {
            throw new IllegalArgumentException("Non-existent coin");
        }

        CashRecharge cashRecharge = new CashRecharge();
        cashRecharge.setTradeno(orderId);
        cashRecharge.setUserId(userId);
        cashRecharge.setName(adminBank.getName());
        cashRecharge.setBankName(adminBank.getBankName());
        cashRecharge.setBankCard(adminBank.getBankCard());
        cashRecharge.setCoinId(cashParam.getCoinId());
        cashRecharge.setCoinName(coin.getName());
        cashRecharge.setNum(cashParam.getNum());

        // received cashParam.getMum() is not trusted
        // need to calculate the monetary value again here
        Config buyRateConfig = configService.findByCode("CNY2USDT");
        BigDecimal realMum = cashRecharge.getNum()
                .multiply(new BigDecimal(buyRateConfig.getValue()))
                .setScale(2, RoundingMode.HALF_UP);
        cashRecharge.setMum(realMum);

        cashRecharge.setFee(BigDecimal.ZERO);
        cashRecharge.setType("online-pay");
        cashRecharge.setStatus(0);
        cashRecharge.setStep(1);

        cashRechargeRepository.save(cashRecharge);

        CashTradeResult cashTradeResult = new CashTradeResult();
        cashTradeResult.setAmount(realMum);
        cashTradeResult.setName(adminBank.getName());
        cashTradeResult.setBankName(adminBank.getBankName());
        cashTradeResult.setBankCard(adminBank.getBankCard());
        cashTradeResult.setRemark(remark);
        cashTradeResult.setStatus((byte) 0);

        return cashTradeResult;
    }

    private AdminBankDto loadBalance(List<AdminBankDto> adminBanks) {
        if (CollectionUtils.isEmpty(adminBanks)) {
            throw new IllegalStateException("No administration bank account available");
        }
        int size = adminBanks.size();
        if (size == 1) {
            return adminBanks.get(0);
        } else {
            return adminBanks.get(new Random().nextInt(size));
        }
    }
}
