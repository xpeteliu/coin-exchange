package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.entity.TradeArea;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.model.TradeAreaMarketResult;
import io.github.xpeteliu.service.TradeAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tradeAreas")
@Api(tags = {"Controller for ops on tradeArea"})
public class TradeAreaController {

    @Autowired
    TradeAreaService tradeAreaService;

    @GetMapping
    @ApiOperation("Paged query on trade areas")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Name of trade area"),
            @ApiImplicitParam(name = "status", value = "Status of trade area"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<TradeArea>> findWithPaging(String name, Integer status, int current, int size) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        PagedResult<TradeArea> tradeAreaPagedResult = new PagedResult<>(tradeAreaService.findWithPaging(name, status, request));
        return R.success(tradeAreaPagedResult);
    }

    @PostMapping
    @ApiOperation("Add a trade area")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tradeArea", value = "JSON object representing tradeArea"),
    })
    public R add(@RequestBody @Validated TradeArea tradeArea) {
        try {
            tradeAreaService.save(tradeArea);
            return R.success("Trade area successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PatchMapping
    @ApiOperation("Update a trade area")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tradeArea", value = "JSON object representing tradeArea"),
    })
    public R update(@RequestBody TradeArea tradeArea) {
        try {
            tradeAreaService.update(tradeArea);
            return R.success("Trade area successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PostMapping("/status")
    @ApiOperation("Update the status of a trade areas")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tradeArea", value = "JSON object representing tradeArea"),
    })
    public R UpdateStatus(@RequestBody TradeArea tradeArea) {
        try {
            tradeAreaService.update(tradeArea);
            return R.success("Trade area successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @GetMapping("/all")
    @ApiOperation("Paged query on trade areas")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Status of trade area")
    })
    public R<List<TradeArea>> findByStatus(Integer status) {
        List<TradeArea> results = tradeAreaService.findByStatus(status);
        return R.success(results);
    }

    @GetMapping("/markets")
    @ApiOperation("Query on all trade areas with markets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Status of market")
    })
    public R<List<TradeAreaMarketResult>> findAllTradeAreaWithMarket() {
        List<TradeAreaMarketResult> results = tradeAreaService.findAllTradeAreaWithMarket();
        return R.success(results);
    }

    @GetMapping("/market/favorite")
    @ApiOperation("Query on user's favorite markets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Status of market")
    })
    public R<List<TradeAreaMarketResult>> findUserFavoriteMarket() {
        List<TradeAreaMarketResult> results = tradeAreaService.findUserFavoriteMarket();
        return R.success(results);
    }
}
