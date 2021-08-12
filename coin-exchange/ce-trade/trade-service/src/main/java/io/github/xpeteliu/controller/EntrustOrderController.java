package io.github.xpeteliu.controller;

import io.github.xpeteliu.model.EntrustOrderResult;
import io.github.xpeteliu.model.EntrustedOrderParam;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.EntrustOrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrustOrders")
public class EntrustOrderController {

    @Autowired
    EntrustOrderService entrustOrderService;

    @GetMapping("/history/{symbol}")
    @ApiOperation(value = "Query on history entrusted order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<EntrustOrderResult>> findHistoryEntrustOrder(@PathVariable("symbol") String symbol, int current, int size) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.ASC, "lastUpdateTime");
        PagedResult<EntrustOrderResult> pagedResult = new PagedResult<>(entrustOrderService.findHistoryEntrustOrderWithPaging(symbol, request));
        return R.success(pagedResult);
    }

    @GetMapping("/{symbol}")
    @ApiOperation(value = "Query on history entrusted order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page")
    })
    public R<PagedResult<EntrustOrderResult>> finOngoingEntrustOrder(@PathVariable("symbol") String symbol, int current, int size) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.ASC, "lastUpdateTime");
        PagedResult<EntrustOrderResult> pagedResult = new PagedResult<>(entrustOrderService.findOngoingEntrustOrderWithPaging(symbol, request));
        return R.success(pagedResult);
    }

    @PostMapping
    @ApiOperation(value = "Submit an entrusted trade order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderParam", value = "JSON object representing an entrusted order")
    })
    public R createEntrustedOrder(@RequestBody EntrustedOrderParam entrustedOrderParam) {
        try {
            entrustOrderService.createEntrustedOrder(entrustedOrderParam);
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public R revokeEntrustedOrder(@PathVariable Long id) {
        try {
            entrustOrderService.revokeEntrustedOrder(id);
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }
}
