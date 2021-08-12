package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.User;
import io.github.xpeteliu.model.LoginFormParameter;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.model.LoggedInUser;
import io.github.xpeteliu.service.UserLoginService;
import io.github.xpeteliu.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    @ApiOperation("Login of Users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginForm", value = "Parameters in the login form"),
    })
    public R<LoggedInUser> login(@RequestBody LoginFormParameter loginForm) {
        try {
            return R.success(userLoginService.login(loginForm));
        } catch (Exception e) {
            return R.failure(e.getMessage());
        }
    }

}
