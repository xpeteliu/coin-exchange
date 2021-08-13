package io.github.xpeteliu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.github.xpeteliu.constant.CommonConstant;
import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.dto.TradeMarketDto;
import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.entity.TurnoverOrder;
import io.github.xpeteliu.model.DepthResult;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.MarketService;
import io.github.xpeteliu.service.TurnoverOrderService;
import io.github.xpeteliu.utils.SupplementaryUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/markets")
@Api(tags = {"Controller for ops on market"})
public class MarketController {

    @Autowired
    MarketService marketService;

    @Autowired
    TurnoverOrderService turnoverOrderService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping
    @ApiOperation("Paged query on markets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tradeAreaId", value = "Trade area ID of market"),
            @ApiImplicitParam(name = "status", value = "Status of market"),
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<Market>> findWithPaging(Long tradeAreaId, Integer status, int current, int size) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.ASC, "sort");
        PagedResult<Market> marketPagedResult = new PagedResult<>(marketService.findWithPaging(tradeAreaId, status, request));
        return R.success(marketPagedResult);
    }

    @PostMapping
    @ApiOperation("Add a market")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "JSON object representing market"),
    })
    public R add(@RequestBody @Validated Market market) {
        try {
            marketService.saveSubmittedMarket(market);
            return R.success("Market successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PatchMapping
    @ApiOperation("Update a market")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "JSON object representing market"),
    })
    public R update(@RequestBody Market market) {
        try {
            marketService.update(market);
            return R.success("Market successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @PostMapping("/setStatus")
    @ApiOperation("Update the status of a markets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "JSON object representing market"),
    })
    public R UpdateStatus(@RequestBody Market market) {
        try {
            marketService.update(market);
            return R.success("Market successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @GetMapping("/all")
    @ApiOperation("Paged query on markets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Status of market")
    })
    public R<List<Market>> findByStatus(Integer status) {
        List<Market> results = marketService.findByStatus(status);
        return R.success(results);
    }

    @GetMapping("/depth/{symbol}/{depth}")
    @ApiOperation("Query on info in depth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "symbol", value = "Symbol of coin pair"),
            @ApiImplicitParam(name = "depth", value = "Specified depth"),
    })
    public R<DepthResult> findDepthInfo(@PathVariable String symbol, @PathVariable String depth) {
        return R.success(marketService.findDepthInfo(symbol, depth));
    }

    @GetMapping("/trades/{symbol}")
    @ApiOperation("Query on trading record")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "symbol", value = "Symbol of coin pair"),
    })
    public R<List<TurnoverOrder>> findTradingRecords(@PathVariable String symbol) {
        return R.success(turnoverOrderService.findNewestNRecordsBySymbol(symbol, 60));
    }

    @GetMapping("/kline/{symbol}/{type}")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "symbol", value = "Symbol of coin pair"),
                    @ApiImplicitParam(name = "type", value = "Type of KLine")}
    )
    public R<List<List>> queryKLine(@PathVariable String symbol, @PathVariable String type) {
        String key = String.format("market-kline-%s-%s", symbol, type);
        List<String> KLineStrings = redisTemplate.opsForList().range(key, 0, CommonConstant.REDIS_MAX_CACHE_KLINE_SIZE - 1);

        if (CollectionUtils.isEmpty(KLineStrings)) {
            return R.success(Collections.emptyList());
        }

        List<List> result = KLineStrings.stream().map(SupplementaryUtils::kLineString2OutputList).collect(Collectors.toList());

        return R.success(result);
    }

    @GetMapping("/findBySymbol")
    public MarketDto findBySymbol(String symbol) {
        return marketService.findBySymbol(symbol);
    }

    @GetMapping(value = "/allMarkets")
    public List<MarketDto> findAllMarkets() {
        return marketService.findAllMarkets();
    }

    @GetMapping(value = "/findTradeMarketsByTradeAreaId")
    List<TradeMarketDto> findTradeMarketsByTradeAreaId(@RequestParam Long tradeAreaId) {
        return marketService.findTradeMarketsByTradeAreaId(tradeAreaId);
    }
}
