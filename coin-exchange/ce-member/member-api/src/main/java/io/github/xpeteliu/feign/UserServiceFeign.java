package io.github.xpeteliu.feign;

import io.github.xpeteliu.config.feign.OAuth2FeignConfig;
import io.github.xpeteliu.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "member-service", configuration = OAuth2FeignConfig.class, path = "/users")
public interface UserServiceFeign {

    @GetMapping("/basic/users")
    Map<Long, UserDto> getUserBasics(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String mobile);
}
