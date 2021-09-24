package io.github.xpeteliu.feign;


import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.dto.TradeMarketDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trade-service", contextId = "marketServiceFeignClient", configuration = OAuth2FeignConfig.class, path = "/markets")
public interface MarketServiceFeignClient {

    @GetMapping("/findBySymbol")
    MarketDto findBySymbol(@RequestParam("symbol") String symbol);

    @GetMapping(value = "/allMarkets")
    List<MarketDto> findAllMarkets(@RequestHeader("Authorization") String authorization);

    @GetMapping(value = "/findTradeMarketsByTradeAreaId")
    List<TradeMarketDto> findTradeMarketsByTradeAreaId(@RequestParam Long tradeAreaId, @RequestHeader("Authorization") String authorization);
}