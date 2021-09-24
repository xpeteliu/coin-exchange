package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.UserAddress;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.UserAddressService;
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
@RequestMapping("/userAddress")
@Api("UserAddress Management")
public class UserAddressController {

    @Autowired
    UserAddressService userAddressService;

    @GetMapping
    @ApiOperation("Paged query on user addresss")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID", value = "User ID to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page"),
    })
    public R<PagedResult<UserAddress>> findWithPaging(@RequestParam Long userId,
                                               @Positive int current, @Nonnegative int size) {

        Page<UserAddress> page = userAddressService.findWithPaging(userId, current - 1, size);
        PagedResult<UserAddress> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

//    @GetMapping("/info")
//    @ApiOperation("Query on details of userAddresss")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "UserAddress ID to search for"),
//    })
//    public R<UserAddress> findInfo(Long id) {
//        UserAddress userAddress = userAddressService.findById(id);
//        return R.success(userAddress);
//    }
//
//    @PostMapping
//    @ApiOperation("Add a userAddress")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userAddress", value = "JSON object representing userAddress")
//    })
//    @PreAuthorize("hasAuthority('user_address_create')")
//    public R create(@RequestBody UserAddress userAddress) {
//        try {
//            userAddressService.saveUserAddress(userAddress);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.failure(e.getMessage());
//        }
//        return R.success("UserAddress successfully added");
//    }

    @PatchMapping
    @ApiOperation("Update a user address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAddress", value = "JSON object representing userAddress")
    })
    public R update(@RequestBody @Validated UserAddress userAddress) {
        try {
            userAddressService.updateUserAddress(userAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("UserAddress successfully updated");
    }

    @PostMapping("/status")
    @ApiOperation("Update the status of a user address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the user address whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    public R updateStatus(Long id, Integer status) {
        try {
            userAddressService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("UserAddress status updated successfully");
    }

}
