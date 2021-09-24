package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.SysPrivilege;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.SysPrivilegeService;
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
@RequestMapping("/privileges")
@Api(tags = "Management of Privileges")
public class SysPrivilegeController {

    @Autowired
    SysPrivilegeService sysPrivilegeService;

    @GetMapping
    @ApiOperation("Paged query on privileges")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    public R<PagedResult<SysPrivilege>> findWithPaging(int current, int size) {
        Page<SysPrivilege> page = sysPrivilegeService.findAllWithPaging(PageRequest.of(current - 1, size,
                Sort.Direction.DESC, "lastUpdateTime"));
        PagedResult<SysPrivilege> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PostMapping
    @ApiOperation("Create a privilege")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "JSON object representing privilege")
    })
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    public R create(@RequestBody @Validated SysPrivilege sysPrivilege) {
        try {
            sysPrivilegeService.savePrivilege(sysPrivilege);
        } catch (Exception e) {
            return R.failure(e.getMessage());
        }
        return R.success("Privilege successfully created");
    }

    @PatchMapping
    @ApiOperation("Modify a privilege")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "JSON object representing privilege")
    })
    @PreAuthorize("hasAuthority('sys_privilege_update')")
    public R update(@RequestBody @Validated SysPrivilege sysPrivilege) {
        try {
            sysPrivilegeService.savePrivilege(sysPrivilege);
        } catch (Exception e) {
            return R.failure(e.getMessage());
        }
        return R.success("Privilege successfully modified");
    }
}
