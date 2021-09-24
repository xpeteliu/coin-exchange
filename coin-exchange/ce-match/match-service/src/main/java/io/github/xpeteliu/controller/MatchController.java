package io.github.xpeteliu.controller;

import io.github.xpeteliu.disruptor.OrderEventHandler;
import io.github.xpeteliu.model.DepthItemResult;
import io.github.xpeteliu.model.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    OrderEventHandler[] orderEventHandlers;

    @GetMapping("/depth")
    public Map<String, List<DepthItemResult>> findMarketDepth(@RequestParam List<String> symbols) {
        Map<String, List<DepthItemResult>> depthMap = new HashMap<>();
        for (String symbol : symbols) {
            for (OrderEventHandler orderEventHandler : orderEventHandlers) {
                if (orderEventHandler.getSymbol().equalsIgnoreCase(symbol)) {
                    OrderBook orderBook = orderEventHandler.getOrderBook();
                    depthMap.put("asks-" + symbol, orderBook.getSellDepthTable().getRows());
                    depthMap.put("bids-" + symbol, orderBook.getBuyDepthTable().getRows());
                }
            }
        }
        return depthMap;
    }
}
