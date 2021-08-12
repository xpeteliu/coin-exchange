package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.CoinType;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.CoinTypeService;
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
@RequestMapping("/coinTypes")
public class CoinTypeController {

    @Autowired
    private CoinTypeService coinTypeService;

    @GetMapping
    @ApiOperation("Paged query on coin types")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of coin type to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<CoinType>> findWithPaging(String code, int current, int size) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        PagedResult<CoinType> coinTypePagedResult = new PagedResult<>(coinTypeService.findWithPaging(code, request));
        return R.success(coinTypePagedResult);
    }

    @PostMapping
    @ApiOperation("Create a new coin type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of coin type to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R add(@RequestBody @Validated CoinType coinType) {
        try {
            coinTypeService.save(coinType);
            return R.success("Coin type saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PatchMapping
    @ApiOperation("Create a new coin type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of coin type to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R update(@RequestBody @Validated CoinType coinType) {
        try {
            coinTypeService.update(coinType);
            return R.success("Coin type saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PostMapping("/setStatus")
    @ApiOperation("Create a new coin type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of coin type to be updated"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    public R updateStatus(@RequestBody Map<String, Long> param) {
        try {
            coinTypeService.updateStatus(param.get("id"), param.get("status").intValue());
            return R.success("Coin type saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @GetMapping("/all")
    @ApiOperation("Non-paged query on coin types")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Current status as a filtering condition")
    })
    public R<List<CoinType>> findAll(int status) {
        return R.success(coinTypeService.findAll(status));
    }
}
