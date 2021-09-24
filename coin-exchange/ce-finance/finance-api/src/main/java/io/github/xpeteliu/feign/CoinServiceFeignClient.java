package io.github.xpeteliu.feign;

import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import io.github.xpeteliu.dto.CoinDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "finance-service", contextId = "CoinService", configuration = OAuth2FeignConfig.class, path = "/coins")
public interface CoinServiceFeignClient {

    @GetMapping("/list")
    Map<Long, CoinDto> findCoins(@RequestParam List<Long> coinIds);
}
