package io.github.xpeteliu.controller;

import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.dto.TradeAreaDto;
import io.github.xpeteliu.dto.TradeMarketDto;
import io.github.xpeteliu.model.DepthResult;
import io.github.xpeteliu.service.CronTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CronTaskController {

    List<MarketDto> markets;

    List<TradeAreaDto> tradeAreas;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    CronTaskService cronTaskService;

    @PostConstruct
    @Scheduled(fixedRate = 500000)
    public void refreshBasicInfo() {
        this.markets = cronTaskService.findAllMarkets();
        this.tradeAreas = cronTaskService.findAllTradeAreas();
    }

    @Scheduled(fixedRate = 10000)
    public void publishMarketDepth() {
        List<DepthResult> depthResults = cronTaskService.getDepthResults(markets);
        assert depthResults.size() == markets.size();
        for (int i = 0; i < markets.size(); i++) {
            String symbol = markets.get(i).getSymbol().toLowerCase();
            log.debug("Sending to {}", "/topic/market-depth-" + symbol);
            simpMessagingTemplate.convertAndSend("/topic/market-depth-" + symbol, Map.of("tick", depthResults.get(i)));
        }
    }

    @Scheduled(fixedRate = 10000)
    public void publishKLine() {
        cronTaskService.candlestickMap.forEach(
                (key, candlestick) -> {
                    simpMessagingTemplate.convertAndSend(
                            "/topic/" + key, Map.of("tick", candlestick.toOutputList()));
                    System.out.println(candlestick.toOutputList());
                }
        );
    }

    @Scheduled(fixedRate = 10000)
    public void publishMarketInfo() {
        for (TradeAreaDto tradeArea : tradeAreas) {
            String code = tradeArea.getCode().toLowerCase();
            Long tradeAreaId = tradeArea.getId();
            List<TradeMarketDto> markets = cronTaskService.findMarketsByTradeAreaId(tradeAreaId);
            Map<String, List<TradeMarketDto>> result = Map.of("markets", markets);
            simpMessagingTemplate.convertAndSend("/topic/market-area-" + code, result);
            simpMessagingTemplate.convertAndSend("/topic/all-market-area", result);
        }
    }

    @Scheduled(fixedRate = 3000)
    public void publishTradeRecordResult() {
        cronTaskService.tradeRecordResultsMap.forEach(
                (symbol, tradeRecordResults) -> simpMessagingTemplate.convertAndSend(
                        "/topic/market-trade-" + symbol, Map.of("data", tradeRecordResults))
        );
    }
}
