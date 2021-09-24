package io.github.xpeteliu.controller;


import io.github.xpeteliu.entity.Config;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configs")
@Api(tags = "Controller for Config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping
    @ApiOperation("Find configs with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Index of current page"),
            @ApiImplicitParam(name = "size", value = "Number of configs on each page"),
            @ApiImplicitParam(name = "name", value = "Name of Config"),
            @ApiImplicitParam(name = "type", value = "Type of Config"),
            @ApiImplicitParam(name = "code", value = "Code of Config")
    })
    @PreAuthorize("hasAuthority('config_query')")
    public R<PagedResult<Config>> findWithPaging(int current, int size,
                                                 @RequestParam(defaultValue = "") String name,
                                                 @RequestParam(defaultValue = "") String type,
                                                 @RequestParam(defaultValue = "") String code) {
        PageRequest request = PageRequest.of(current - 1, size);
        Page<Config> page = configService.findWithPaging(name, type, code, request);
        PagedResult<Config> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }


    @PostMapping("/delete")
    @ApiOperation("Delete a config")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "IDs of configs to be deleted")
    })
    @PreAuthorize("hasAuthority('config_delete')")
    public R delete(@RequestBody List<Long> ids) {
        try {
            configService.deleteConfig(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }

        return R.success("Config successfully deleted");
    }

    @PostMapping
    @ApiOperation("Create a config")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "config", value = "JSON object representing config")
    })
    @PreAuthorize("hasAuthority('config_create')")
    public R create(@RequestBody @Validated Config config) {
        try {
            configService.saveConfig(config);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Config successfully created");
    }

    @PatchMapping
    @ApiOperation("Update a config")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "config", value = "JSON object representing config")
    })
    @PreAuthorize("hasAuthority('config_update')")
    public R update(@RequestBody @Validated Config config) {
        try {
            configService.saveConfig(config);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Config successfully updated");
    }
}
