package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.SysMenu;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.model.RolePrivilegesParam;
import io.github.xpeteliu.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Management of a role's privileges")
public class SysRolePrivilegeController {

    @Autowired
    SysRolePrivilegeService sysRolePrivilegeService;

    @GetMapping("/roles_privileges")
    public R<List<SysMenu>> findSysMenu(Long roleId) {
        List<SysMenu> menus = sysRolePrivilegeService.findSysMenu(roleId);
        return R.success(menus);
    }

    @PostMapping("/grant_privileges")
    public R grantPrivilege(@RequestBody RolePrivilegesParam param){
        try {
            sysRolePrivilegeService.grantPrivilege(param);
        }catch (Exception e){
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Privilege granted");
    }
}
