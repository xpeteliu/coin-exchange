package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.User;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.UserService;
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
@RequestMapping("/users")
@Api("User Management")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @ApiOperation("Paged query on users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Total number of pages"),
            @ApiImplicitParam(name = "mobile", value = "User mobile number to search for"),
            @ApiImplicitParam(name = "username", value = "Username to search for"),
            @ApiImplicitParam(name = "realName", value = "User real name to search for"),
            @ApiImplicitParam(name = "userId", value = "User ID to search for"),
            @ApiImplicitParam(name = "status", value = "User status to search for")
    })
    @PreAuthorize("hasAuthority('user_query')")
    public R<PagedResult<User>> findWithPaging(String mobile, @RequestParam(name = "userName", required = false) String username,
                                               String realName, Long userId, Byte status,
                                               @Positive int current, @Nonnegative int size) {

        Page<User> page = userService.findWithPaging(mobile, username, realName, userId, status, current - 1, size);
        PagedResult<User> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PostMapping
    @ApiOperation("Add a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "JSON object representing user")
    })
    @PreAuthorize("hasAuthority('user_create')")
    public R create(@RequestBody User user) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User successfully added");
    }

    @PatchMapping
    @ApiOperation("Update a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "JSON object representing user")
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R update(@RequestBody @Validated User user) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User successfully updated");
    }

    @PostMapping("/status")
    @ApiOperation("Update the status of a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the user whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R updateStatus(@RequestParam("bankId") Long id, Integer status) {
        try {
            userService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User status updated successfully");
    }

}
