package io.github.xpeteliu.controller;

import io.github.xpeteliu.dto.KLineType;
import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.feign.MarketServiceFeignClient;
import io.github.xpeteliu.feign.OrderBookFeignClient;
import io.github.xpeteliu.model.DepthItemResult;
import io.github.xpeteliu.model.DepthResult;
import io.github.xpeteliu.model.KLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CronTaskController {

    public static final String token = "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2Mjg4MjQ3NTksInVzZXJfbmFtZSI6IjEwMTQwNjY5MDkyODAzNzQ3ODUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMjU0ZmMwOTYtYjZjYi00ZGQxLWIwM2ItNmZlZWQ4Zjg4OTlhIiwiY2xpZW50X2lkIjoidGVzdGNsaSIsInNjb3BlIjpbImFsbCJdfQ.dGjDuh07djM3T2BIrK3-O4lnwHw8v7qU-slfvd27SMByBsdcYkXCidlQhab9ooZVweoPtLB6WEhiNDVmoQ3P9mG6kXsAWqQJiG1dQo1whdqFGlT39kUvL-alh9ksfJiRt2pi-IYUaeOL704YGwyprQWPCgfcLTOQwgp-BlYuWzk3TrKDbHg_hKOUybdMB2kBSQVwazKgpVxDaF0U-m3wg7VF0pVg8Jw_J_XRhz3Ppgbdmg9CchSjGzbS98vv8iEJb_p7yeCGcx0LX6EneEHqyWFwFCbglnuID-vOOpFn0iBRCG1AYSYfE32ohoAAweEdx-lXoL38DfXkp1RUpkGQ1A";
    public static Map<String, KLine> candlestickMap = new HashMap<>();
    List<MarketDto> markets;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    MarketServiceFeignClient marketServiceFeignClient;

    @Autowired
    OrderBookFeignClient orderBookFeignClient;

    @PostConstruct
    @Scheduled(fixedRate = 500000)
    public void refreshMarkets() {
        this.markets = marketServiceFeignClient.findAllMarkets(token);
    }

    @Scheduled(fixedRate = 10000)
    public void publishMarketDepth() {
        for (MarketDto market : markets) {
            String symbol = market.getSymbol();
            Map<String, List<DepthItemResult>> marketDepth = orderBookFeignClient.findMarketDepth(symbol, token);
            if (marketDepth == null) {
                //log.warn("No market depth info for {}", symbol);
                return;
            }
            DepthResult result = new DepthResult();
            result.setCnyPrice(market.getOpenPrice());
            result.setPrice(market.getOpenPrice());
            result.setAsks(marketDepth.get("asks"));
            result.setBids(marketDepth.get("bids"));
            log.debug("Sending to {}", "/topic/market-depth-" + symbol.toLowerCase());
            simpMessagingTemplate.convertAndSend("/topic/market-depth-" + symbol.toLowerCase(), Map.of("tick", result));
        }
    }

    @Scheduled(fixedRate = 10000)
    public void publishKLine() {
        KLineType kLineType = KLineType.ONE_MINUTE;
        for (MarketDto market : markets) {
            String key = String.format("market-kline-%s-%s", market.getSymbol().toLowerCase(), kLineType.getValue());
            KLine candlestick = candlestickMap.get(key);
            if (candlestick != null) {
                simpMessagingTemplate.convertAndSend("/topic/" + key, candlestick.toKline());
            }
        }
    }

}
