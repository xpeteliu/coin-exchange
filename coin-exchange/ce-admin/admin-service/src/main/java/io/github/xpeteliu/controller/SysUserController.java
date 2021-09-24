package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.SysUser;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping
    public R<PagedResult<SysUser>> findWithPaging(@RequestParam(defaultValue = "1") int current,
                                                  @RequestParam int size,
                                                  @RequestParam(defaultValue = "") String mobile,
                                                  @RequestParam(defaultValue = "") String fullname) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        Page<SysUser> page = sysUserService.findByNameAndMobileWithPaging(fullname, mobile, request);
        PagedResult<SysUser> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PostMapping
    public R create(@RequestBody @Validated SysUser sysUser) {
        try {
            if (sysUser.getStatus() == null) {
                sysUser.setStatus((byte) 1);
            }
            sysUserService.addUser(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("New user added successfully");
    }

    @PatchMapping
    public R update(@RequestBody @Validated SysUser sysUser) {
        try {
            sysUserService.updateUser(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("User modified successfully");
    }

    @PostMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        if (ids == null) {
            return R.failure("IDs of roles to be deleted cannot be null");
        }

        try {
            sysUserService.deleteUsers(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Role successfully modified");
    }
}
