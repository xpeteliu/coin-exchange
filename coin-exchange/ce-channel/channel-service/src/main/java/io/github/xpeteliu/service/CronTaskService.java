package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.dto.TradeAreaDto;
import io.github.xpeteliu.dto.TradeMarketDto;
import io.github.xpeteliu.feign.MarketServiceFeignClient;
import io.github.xpeteliu.feign.OrderBookFeignClient;
import io.github.xpeteliu.feign.TradeAreaFeignClient;
import io.github.xpeteliu.model.DepthItemResult;
import io.github.xpeteliu.model.DepthResult;
import io.github.xpeteliu.model.KLine;
import io.github.xpeteliu.model.TradeRecordResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CronTaskService {

    public final String token = "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2Mjk0MjQ5NzYsInVzZXJfbmFtZSI6IjEwMTQwNjY5MDkyODAzNzQ3ODUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYzQ5YWQzNzEtYzZkYy00NDM1LWIxMzItZGVmYzFlYzRmYWExIiwiY2xpZW50X2lkIjoidGVzdGNsaSIsInNjb3BlIjpbImFsbCJdfQ.NjEyDV53MZr_vmvC5TidVUaTn9TDkRvSQ33cztQAhuxwkoxDamZuAYPWRCgKjSUJizv0W4024rCIOzNVWeRiDDnl2mNQ8HkUJkSHqCg7q1c8n8axdFtXuTlU_v6iRRsJ88rDGl5idfyLagG-Bkd4-7mjZZw_n4sbMAuDWLXAa1j9wZzOYMbWAQMatPuKgRc4-bjoRF-Jbe8EqbzYWgYe1iZlhB8QmIksrMU4HEu-_nfUSRaI_tPQ_vPlfBuYPZ4USJllTvQik0b9VwFFlZQkPHkbFo01UmvQAY0dUZlsRJqM4DMXUlGr8Sco2WFBUNPBaS0hGAg3cfg5TdH8pT-5FQ";
    public Map<String, KLine> candlestickMap = new ConcurrentHashMap<>();
    public Map<String, LinkedList<TradeRecordResult>> tradeRecordResultsMap = new ConcurrentHashMap<>();

    @Autowired
    MarketServiceFeignClient marketServiceFeignClient;

    @Autowired
    OrderBookFeignClient orderBookFeignClient;

    @Autowired
    TradeAreaFeignClient tradeAreaFeignClient;

    public List<MarketDto> findAllMarkets() {
        return marketServiceFeignClient.findAllMarkets(token);
    }

    public List<DepthResult> getDepthResults(List<MarketDto> markets) {
        List<String> symbols = markets.stream().map(MarketDto::getSymbol).collect(Collectors.toList());
        Map<String, List<DepthItemResult>> marketDepth = orderBookFeignClient.findMarketDepth(symbols, token);
        List<DepthResult> depthResults = new ArrayList<>();
        for (MarketDto market : markets) {
            String symbol = market.getSymbol();
            DepthResult depthResult = new DepthResult();
            depthResult.setCnyPrice(market.getOpenPrice());
            depthResult.setPrice(market.getOpenPrice());
            depthResult.setAsks(marketDepth.get("asks-" + symbol));
            depthResult.setBids(marketDepth.get("bids-" + symbol));
            depthResults.add(depthResult);
        }
        return depthResults;
    }

    public List<TradeAreaDto> findAllTradeAreas() {
        return tradeAreaFeignClient.findAllTradeAreas(token);
    }

    public List<TradeMarketDto> findMarketsByTradeAreaId(Long tradeAreaId) {
        return marketServiceFeignClient.findTradeMarketsByTradeAreaId(tradeAreaId, token);
    }

}
