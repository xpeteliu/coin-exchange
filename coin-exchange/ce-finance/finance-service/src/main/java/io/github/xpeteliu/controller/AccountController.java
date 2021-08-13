package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.Account;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.model.SymbolAssetResult;
import io.github.xpeteliu.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

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

    @PostMapping("/freezeAccountBalance")
    public void freezeAccountBalance(Long userId, Long coinId, BigDecimal mum, String type, Long orderId, BigDecimal fee) {
        accountService.freezeAccountBalance(userId, coinId, mum, type, orderId, fee);
    }

    @GetMapping("/transferBalance")
    public void transferBalance(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        accountService.transferBalance(fromUserId, toUserId, coinId, amount, businessType, orderId);
    }
}
