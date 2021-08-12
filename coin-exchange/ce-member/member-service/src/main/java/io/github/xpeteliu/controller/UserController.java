package io.github.xpeteliu.controller;

import io.github.xpeteliu.dto.UserDto;
import io.github.xpeteliu.entity.User;
import io.github.xpeteliu.entity.UserAuthAuditRecord;
import io.github.xpeteliu.entity.UserAuthInfo;
import io.github.xpeteliu.feign.UserServiceFeign;
import io.github.xpeteliu.model.*;
import io.github.xpeteliu.service.UserAuthAuditRecordService;
import io.github.xpeteliu.service.UserAuthInfoService;
import io.github.xpeteliu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnegative;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/users")
@Api("User Management")
public class UserController implements UserServiceFeign {

    @Autowired
    UserService userService;

    @Autowired
    UserAuthAuditRecordService userAuthAuditRecordService;

    @Autowired
    UserAuthInfoService userAuthInfoService;

    @GetMapping
    @ApiOperation("Paged query on users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "User mobile number to search for"),
            @ApiImplicitParam(name = "username", value = "Username to search for"),
            @ApiImplicitParam(name = "realName", value = "User real name to search for"),
            @ApiImplicitParam(name = "userId", value = "User ID to search for"),
            @ApiImplicitParam(name = "status", value = "User status to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<User>> findWithPaging(String mobile, @RequestParam(name = "userName", required = false) String username,
                                               String realName, Long userId, Byte status,
                                               @Positive int current, @Nonnegative int size) {

        Page<User> page = userService.findWithPaging(mobile, username, realName, userId, status, null, current - 1, size);
        PagedResult<User> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @GetMapping("/info")
    @ApiOperation("Query on details of users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User ID to search for"),
    })
    public R<User> findInfo(Long id) {
        User user = userService.findById(id);
        return R.success(user);
    }

    @PostMapping
    @ApiOperation("Add a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "JSON object representing user")
    })
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
    public R update(@RequestBody @Validated User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User successfully updated");
    }

    @PostMapping("/status")
    @ApiOperation("Update the status of a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the user whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    public R updateStatus(Long id, Integer status) {
        try {
            userService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User status updated successfully");
    }

    @GetMapping("/directInvites")
    @ApiOperation("Find invitees of current user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the user whose status is to be modified"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<User>> getDirectInvites(Long userId, @Positive int current, @Nonnegative int size) {
        Page<User> page = userService.findInviteeWithPaging(userId, current - 1, size);
        PagedResult<User> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @GetMapping("/auths")
    @ApiOperation("Paged query on users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "User mobile number to search for"),
            @ApiImplicitParam(name = "username", value = "Username to search for"),
            @ApiImplicitParam(name = "realName", value = "User real name to search for"),
            @ApiImplicitParam(name = "userId", value = "User ID to search for"),
            @ApiImplicitParam(name = "reviewsStatus", value = "User review status to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<User>> findWithPaging(String mobile, @RequestParam(name = "userName", required = false) String username,
                                               String realName, Long userId, @RequestParam(value = "reviewsStatus", required = false) Integer reviewStatus,
                                               @Positive int current, @Nonnegative int size) {

        Page<User> page = userService.findWithPaging(mobile, username, realName, userId, null, reviewStatus, current - 1, size);
        PagedResult<User> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @GetMapping("/auth/info")
    @ApiOperation("Paged query on users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User ID to search for"),
    })
    public R<UserAuthInfoResult> getUserAutoInfo(Long id) {
        User user = userService.findById(id);
        List<UserAuthAuditRecord> userAuthAuditRecordList = null;
        List<UserAuthInfo> userAuthInfoList = null;
        if (user != null && user.getReviewsStatus() != null && user.getReviewsStatus() != 0) {
            userAuthAuditRecordList = userAuthAuditRecordService.findAllById(id);
            Long authCode = userAuthAuditRecordList.get(0).getAuthCode();
            userAuthInfoList = userAuthInfoService.findAuthInfoByCode(authCode);
        } else {
            userAuthAuditRecordList = Collections.emptyList();
            userAuthInfoList = Collections.emptyList();
        }
        return R.success(new UserAuthInfoResult(user, userAuthInfoList, userAuthAuditRecordList));
    }

    @PostMapping("/auths/status")
    @ApiOperation("Update the status of a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of the user whose status is to be modified"),
            @ApiImplicitParam(name = "authStatus", value = "Target auth status"),
            @ApiImplicitParam(name = "authCode", value = "Auth code to be set"),
            @ApiImplicitParam(name = "remark", value = "Remark on the authentication")
    })
    public R updateAuthStatus(@RequestParam Long id,
                              @RequestParam Byte authStatus,
                              Long authCode, String remark) {
        try {
            userService.updateAuthStatus(id, authStatus, authCode, remark);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User auth status updated successfully");
    }

    @GetMapping("/current/info")
    @ApiOperation("Get current user info")
    public R<User> obtainUserInfo() {
        try {
            String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = userService.findById(Long.valueOf(idStr));
            user.setPassword("*");
            user.setPaypassword("*");
            user.setAccessKeyId("*");
            user.setAccessKeySecret("*");
            return R.success(user);
        } catch (Exception e) {
            return R.failure(e.getMessage());
        }
    }

    @PostMapping("/authAccount")
    @ApiOperation("Verify authentication info of current user ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "Real name of current user"),
            @ApiImplicitParam(name = "idCardType", value = "Type of ID card used for authentication"),
            @ApiImplicitParam(name = "idCard", value = "Number on ID card used for authentication")
    })
    public R verifyAuthInfo(@RequestBody AuthAccountParam authAccountParam) {
        if (userService.verifyAndUpdateAuthInfo(authAccountParam)) {
            return R.success();
        } else {
            return R.failure();
        }
    }

    @PostMapping("/updatePhone")
    @ApiOperation("Update phone number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updatePhoneParam", value = "JSON object representing info on mobile-number-updating request")
    })
    public R updatePhone(@RequestBody UpdatePhoneParam updatePhoneParam) {
        try {
            userService.updatePhone(updatePhoneParam);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User successfully updated");
    }

    @GetMapping("/checkTel")
    @ApiOperation("Check phone number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "Mobile number to be checked"),
            @ApiImplicitParam(name = "countryCode", value = "Country code of mobile number")
    })
    public R checkPhoneNumber(@RequestParam String mobile, @RequestParam String countryCode) {
        return userService.isPhoneNumberAvailable(mobile, countryCode) ? R.success() : R.failure("Phone number not available");
    }

    @PostMapping("/updateLoginPassword")
    @ApiOperation("Update a password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updatePasswordParam", value = "JSON object representing info on password-updating request")
    })
    public R updatePassword(@RequestBody UpdatePasswordParam updatePasswordParam) {
        try {
            userService.updatePassword(updatePasswordParam);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Password successfully updated");
    }

    @PostMapping("/updatePayPassword")
    @ApiOperation("Update a pay password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updatePayPasswordParam", value = "JSON object representing info on password-updating request")
    })
    public R updatePayPassword(@RequestBody UpdatePasswordParam updatePayPasswordParam) {
        try {
            userService.updatePayPassword(updatePayPasswordParam);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Password successfully updated");
    }

    @PostMapping("/setPayPassword")
    @ApiOperation("Reset a pay password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "setPayPasswordParam", value = "JSON object representing info on password-reset request")
    })
    public R resetPayPassword(@RequestBody Map<String, String> setPayPasswordParam) {
        try {
            userService.resetPayPassword(setPayPasswordParam);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Password successfully updated");
    }

    @GetMapping("/invites")
    @ApiOperation("Get the list of invitees for current user")
    public R<List<User>> findInvitees() {
        List<User> invitees = userService.findInvitees();
        invitees.forEach(user -> {
            user.setPassword("*");
            user.setPaypassword("*");
            user.setAccessKeyId("*");
            user.setAccessKeySecret("*");
        });
        return R.success(invitees);
    }

    @Override
    @GetMapping("/basic/users")
    public Map<Long, UserDto> getUserBasics(List<Long> ids, String username, String mobile) {
        return userService.findAllBasics(ids,username,mobile);
    }
}
