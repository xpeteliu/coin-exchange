package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.Account;
import io.github.xpeteliu.feign.AccountServiceFeignClient;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.model.SymbolAssetResult;
import io.github.xpeteliu.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController implements AccountServiceFeignClient {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{coinName}")
    @ApiOperation("Get info on one coin account for current user")
    public R<Account> findUserAccount(@PathVariable("coinName") String coinName) {
        return R.success(accountService.findCoinAccountWithCoinName(coinName));
    }

    @GetMapping("/asset/{symbol}")
    @ApiOperation("Get user asset info concerning a coin pair")
    public R<SymbolAssetResult> findSymbolAsset(@PathVariable String symbol) {
        return R.success(accountService.findSymbolAsset(symbol));
    }

    @Override
    @PostMapping("/freezeAccountBalance")
    public void freezeAccountBalance(Long userId, Long coinId, BigDecimal mum, String type, Long orderId, BigDecimal fee) {
        accountService.freezeAccountBalance(userId, coinId, mum, type, orderId, fee);
    }

    @Override
    @GetMapping("/transferSellAmount")
    public void transferSellAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        accountService.transferSellAmount(fromUserId, toUserId, coinId, amount, businessType, orderId);
    }

    @Override
    @GetMapping("/transferBuyAmount")
    public void transferBuyAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        accountService.transferBuyAmount(fromUserId, toUserId, coinId, amount, businessType, orderId);
    }
}
