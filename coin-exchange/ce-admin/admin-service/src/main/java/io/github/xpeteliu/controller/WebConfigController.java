package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.WebConfig;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.WebConfigService;
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

import java.util.List;

@RestController
@RequestMapping("/webConfigs")
@Api(tags = "Controller for WebConfig")
public class WebConfigController {

    @Autowired
    WebConfigService webConfigService;

    @GetMapping
    @ApiOperation("Find webConfigs with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Index of current page"),
            @ApiImplicitParam(name = "size", value = "Number of webConfigs on each page"),
            @ApiImplicitParam(name = "name", value = "Name of WebConfig"),
            @ApiImplicitParam(name = "type", value = "Type of WebConfig")
    })
    @PreAuthorize("hasAuthority('web_config_query')")
    public R<PagedResult<WebConfig>> findWithPaging(int current, int size,
                                                    @RequestParam(defaultValue = "") String name,
                                                    @RequestParam(defaultValue = "") String type) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.ASC, "sort");
        Page<WebConfig> page = webConfigService.findWithPaging(name, type, request);
        PagedResult<WebConfig> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }


    @PostMapping("/delete")
    @ApiOperation("Delete a webConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "IDs of webConfigs to be deleted")
    })
    @PreAuthorize("hasAuthority('web_config_delete')")
    public R delete(@RequestBody List<Long> ids) {
        try {
            webConfigService.deleteWebConfig(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }

        return R.success("WebConfig successfully deleted");
    }

    @PostMapping
    @ApiOperation("Create a webConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig", value = "JSON object representing webConfig")
    })
    @PreAuthorize("hasAuthority('web_config_create')")
    public R create(@RequestBody @Validated WebConfig webConfig) {
        try {
            if (webConfig.getStatus() == null) {
                webConfig.setStatus((byte) 1);
            }
            webConfigService.saveWebConfig(webConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("WebConfig successfully created");
    }

    @PatchMapping
    @ApiOperation("Update a webConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig", value = "JSON object representing webConfig")
    })
    @PreAuthorize("hasAuthority('web_config_update')")
    public R update(@RequestBody @Validated WebConfig webConfig) {
        try {
            webConfigService.saveWebConfig(webConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("WebConfig successfully updated");
    }

    @GetMapping("/banners")
    @ApiOperation("Get banners for PC clients")
    public R<List<WebConfig>> findBanners(){
        return R.success(webConfigService.findBanners());
    }
}
