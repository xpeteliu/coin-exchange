package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.UserBank;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.UserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnegative;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/userBanks")
@Api("UserBank Management")
public class UserBankController {

    @Autowired
    UserBankService userBankService;

    @GetMapping
    @ApiOperation("Paged query on userBanks")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usrID", value = "User ID to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page"),
    })
    @PreAuthorize("hasAuthority('user_bank_query')")
    public R<PagedResult<UserBank>> findWithPaging(@RequestParam(name = "usrId") Long userID,
                                               @Positive int current, @Nonnegative int size) {

        Page<UserBank> page = userBankService.findWithPaging(userID, current - 1, size);
        PagedResult<UserBank> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @GetMapping("/current")
    @ApiOperation("Find the primary bank card of current user")
    public R<UserBank> findUserBank(){
        return R.success(userBankService.findOneAvailableByUserId());
    }

    @PatchMapping
    @ApiOperation("Update a user bank")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userBank", value = "JSON object representing userBank")
    })
    public R update(@RequestBody @Validated UserBank userBank) {
        try {
            userBankService.updateUserBank(userBank);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("UserBank successfully updated");
    }

    @PostMapping("/status")
    @ApiOperation("Update the status of a user bank")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the user bank whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    public R updateStatus(Long id, Integer status) {
        try {
            userBankService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User bank status updated successfully");
    }

}
