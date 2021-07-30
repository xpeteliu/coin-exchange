package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.SysUserLog;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.SysUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysUserLog")
public class UserLogController {

    @Autowired
    SysUserLogService sysUserLogService;

    @GetMapping
    public R<PagedResult<SysUserLog>> findWithPaging(int current,int size){
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "created");
        Page<SysUserLog> page = sysUserLogService.findAllWithPaging(request);
        PagedResult<SysUserLog> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }
}
