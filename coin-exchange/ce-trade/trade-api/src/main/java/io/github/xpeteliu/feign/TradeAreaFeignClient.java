package io.github.xpeteliu.feign;

import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import io.github.xpeteliu.dto.TradeAreaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "trade-service", contextId = "tradeAreaFeignClient", configuration = OAuth2FeignConfig.class, path = "/tradeAreas")
public interface TradeAreaFeignClient {

    @GetMapping("/findAllTradeAreas")
    List<TradeAreaDto> findAllTradeAreas(@RequestHeader("Authorization") String authorization);
}
