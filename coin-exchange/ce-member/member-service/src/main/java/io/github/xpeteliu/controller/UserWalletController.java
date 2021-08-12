package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.UserWallet;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.UserWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnegative;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/userWallets")
@Api("UserWallet Management")
public class UserWalletController {

    @Autowired
    UserWalletService userWalletService;

    @GetMapping
    @ApiOperation("Paged query on user wallets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID", value = "User ID to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page"),
    })
    public R<PagedResult<UserWallet>> findWithPaging(@RequestParam Long userId,
                                               @Positive int current, @Nonnegative int size) {

        Page<UserWallet> page = userWalletService.findWithPaging(userId, current - 1, size);
        PagedResult<UserWallet> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

//    @GetMapping("/info")
//    @ApiOperation("Query on details of userWallets")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "UserWallet ID to search for"),
//    })
//    public R<UserWallet> findInfo(Long id) {
//        UserWallet userWallet = userWalletService.findById(id);
//        return R.success(userWallet);
//    }
//
//    @PostMapping
//    @ApiOperation("Add a userWallet")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userWallet", value = "JSON object representing userWallet")
//    })
//    @PreAuthorize("hasAuthority('user_wallet_create')")
//    public R create(@RequestBody UserWallet userWallet) {
//        try {
//            userWalletService.saveUserWallet(userWallet);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.failure(e.getMessage());
//        }
//        return R.success("UserWallet successfully added");
//    }

    @PatchMapping
    @ApiOperation("Update a user wallet")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userWallet", value = "JSON object representing userWallet")
    })
    public R update(@RequestBody @Validated UserWallet userWallet) {
        try {
            userWalletService.updateUserWallet(userWallet);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("UserWallet successfully updated");
    }

    @PostMapping("/status")
    @ApiOperation("Update the status of a user wallet")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the user wallet whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    public R updateStatus(Long id, Integer status) {
        try {
            userWalletService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("UserWallet status updated successfully");
    }

}
