package io.github.xpeteliu.feign;

import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "finance-service", contextId = "AccountService", path = "/account", configuration = {OAuth2FeignConfig.class})
public interface AccountServiceFeignClient {

    @PostMapping("/freezeAccountBalance")
    void freezeAccountBalance(@RequestParam Long userId,
                              @RequestParam Long coinId,
                              @RequestParam BigDecimal mum,
                              @RequestParam String type,
                              @RequestParam Long orderId,
                              @RequestParam BigDecimal fee);

    @GetMapping("/transferSellAmount")
    public void transferSellAmount(@RequestParam Long fromUserId,
                                   @RequestParam Long toUserId,
                                   @RequestParam Long coinId,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam String businessType,
                                   @RequestParam Long orderId);

    @GetMapping("/transferBuyAmount")
    public void transferBuyAmount(@RequestParam Long fromUserId,
                                  @RequestParam Long toUserId,
                                  @RequestParam Long coinId,
                                  @RequestParam BigDecimal amount,
                                  @RequestParam String businessType,
                                  @RequestParam Long orderId);
}
