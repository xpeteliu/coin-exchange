package io.github.xpeteliu.controller;

import io.github.xpeteliu.model.SysLoginResult;
import io.github.xpeteliu.service.SysLoginService;
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
    SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation("Login of Administrators")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username of Administrator"),
            @ApiImplicitParam(name = "password", value = "Password of Administrator")
    })
    public SysLoginResult login(@RequestParam String username, String password) {
        return sysLoginService.login(username, password);
    }
}
