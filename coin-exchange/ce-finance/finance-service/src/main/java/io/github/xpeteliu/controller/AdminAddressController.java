package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.AdminAddress;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.AdminAddressService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminAddress")
public class AdminAddressController {

    @Autowired
    private AdminAddressService adminAddressService;

    @GetMapping
    @ApiOperation("Paged query on admin address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinId", value = "ID of current coin type"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<AdminAddress>> findWithPaging(Long coinId, int current, int size) {
        PageRequest request = PageRequest.of(current - 1, size);
        PagedResult<AdminAddress> adminAddressPagedResult = new PagedResult<>(adminAddressService.findWithPaging(coinId, request));
        return R.success(adminAddressPagedResult);
    }

    @PostMapping
    @ApiOperation("Create a new admin address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of admin address to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R add(@RequestBody @Validated AdminAddress adminAddress) {
        try {
            adminAddressService.save(adminAddress);
            return R.success("Admin address saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PatchMapping
    @ApiOperation("Create a new admin address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of admin address to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R update(@RequestBody @Validated AdminAddress adminAddress) {
        try {
            adminAddressService.update(adminAddress);
            return R.success("Admin address saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

}
