package io.github.xpeteliu.controller;

import io.github.xpeteliu.dto.CoinDto;
import io.github.xpeteliu.entity.Coin;
import io.github.xpeteliu.feign.CoinServiceFeignClient;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.CoinService;
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
@RequestMapping("/coins")
public class CoinController implements CoinServiceFeignClient {

    @Autowired
    private CoinService coinService;

    @GetMapping
    @ApiOperation("Paged query on coins")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coin", value = "Example coin to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on one page")
    })
    public R<PagedResult<Coin>> findWithPaging(Coin coin, int current, int size) {
        System.out.println(coin);
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        PagedResult<Coin> coinPagedResult = new PagedResult<>(coinService.findWithPaging(coin, request));
        return R.success(coinPagedResult);
    }

    @PostMapping
    @ApiOperation("Create a new coin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of coin to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R add(@RequestBody @Validated Coin coin) {
        try {
            coinService.save(coin);
            return R.success("Coin saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PatchMapping
    @ApiOperation("Create a new coin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Code of coin to search for"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R update(@RequestBody @Validated Coin coin) {
        try {
            coinService.update(coin);
            return R.success("Coin saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PostMapping("/setStatus")
    @ApiOperation("Create a new coin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of coin to be updated"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    public R updateStatus(@RequestBody Map<String, Long> param) {
        try {
            coinService.updateStatus(param.get("id"), param.get("status").intValue());
            return R.success("Coin saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @GetMapping("/all")
    @ApiOperation("Non-paged query on coins")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Current status as a filtering condition")
    })
    public R<List<Coin>> findAll(int status) {
        return R.success(coinService.findAll(status));
    }

    @Override
    @GetMapping("/list")
    public Map<Long, CoinDto> findCoins(List<Long> coinIds) {
        return coinService.findAllById(coinIds);
    }
}
