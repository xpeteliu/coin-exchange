package io.github.xpeteliu.feign;

import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import io.github.xpeteliu.model.DepthItemResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "match-service", contextId = "orderBookFeignClient", configuration = OAuth2FeignConfig.class, path = "/match")
public interface OrderBookFeignClient {

    @GetMapping("/depth")
    Map<String, List<DepthItemResult>> findMarketDepth(@RequestParam List<String> symbols, @RequestHeader("Authorization") String authorization);
}
