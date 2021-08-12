package io.github.xpeteliu.feign;

import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import io.github.xpeteliu.dto.AdminBankDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="admin-service",path="/adminBanks",configuration = {OAuth2FeignConfig.class})
public interface AdminBankServiceFeign {

    @GetMapping("/list")
    List<AdminBankDto> findAllAdminBanks();
}
