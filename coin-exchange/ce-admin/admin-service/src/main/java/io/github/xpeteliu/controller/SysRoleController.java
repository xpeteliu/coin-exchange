package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.SysRole;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@Api("Role Management")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @GetMapping
    @ApiOperation("Paged query on roles")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Total number of pages")
    })
    @PreAuthorize("hasAuthority('sys_role_query')")
    public R<PagedResult<SysRole>> findWithPaging(int current, int size,
                                                  @RequestParam(defaultValue = "") String name) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        Page<SysRole> page = sysRoleService.findByNameWithPaging(name, request);
        PagedResult<SysRole> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PostMapping
    @ApiOperation("Add a role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "JSON object representing role")
    })
    @PreAuthorize("hasAuthority('sys_role_create')")
    public R create(@RequestBody @Validated SysRole sysRole) {
        try {
            if (sysRole.getStatus() == null) {
                sysRole.setStatus((byte) 1);
            }
            sysRoleService.saveRole(sysRole);
        } catch (Exception e) {
            return R.failure(e.getMessage());
        }
        return R.success("Role successfully added");
    }

    @PostMapping("/delete")
    @ApiOperation("Delete a role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "IDs of roles to be deleted")
    })
    @PreAuthorize("hasAuthority('sys_role_delete')")
    public R delete(@RequestBody String[] ids) {
        if (ids == null) {
            return R.failure("IDs of roles to be deleted cannot be null");
        }

        try {
            sysRoleService.deleteRoles(ids);
        } catch (Exception e) {
            return R.failure(e.getMessage());
        }
        return R.success("Role successfully deleted");
    }

}
