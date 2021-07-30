package io.github.xpeteliu.controller;

import io.github.xpeteliu.model.LoginResult;
import io.github.xpeteliu.service.UserLoginService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    @ApiOperation("Login of Administrators")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username of Administrator"),
            @ApiImplicitParam(name = "password", value = "Password of Administrator")
    })
    public LoginResult login(@RequestParam String username, String password) {
        return userLoginService.login(username, password);
    }
}
