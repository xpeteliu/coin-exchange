package io.github.xpeteliu.controller;

import io.github.xpeteliu.disruptor.OrderEventHandler;
import io.github.xpeteliu.feign.OrderBookFeignClient;
import io.github.xpeteliu.model.DepthItemResult;
import io.github.xpeteliu.model.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController implements OrderBookFeignClient {

    @Autowired
    OrderEventHandler[] orderEventHandlers;

    @GetMapping("/depth")
    @Override
    public Map<String, List<DepthItemResult>> findMarketDepth(@RequestParam String symbol, @RequestHeader("Authorization") String authorization) {
        for (OrderEventHandler orderEventHandler : orderEventHandlers) {
            if (orderEventHandler.getSymbol().equalsIgnoreCase(symbol)) {
                OrderBook orderBook = orderEventHandler.getOrderBook();
                HashMap<String, List<DepthItemResult>> depthMap = new HashMap<>();
                depthMap.put("asks", orderBook.getSellDepthTable().getRows());
                depthMap.put("bids", orderBook.getBuyDepthTable().getRows());
                return depthMap;
            }
        }
        return null;
    }
}
