package io.github.xpeteliu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.github.xpeteliu.entity.SysMenu;
import io.github.xpeteliu.feign.JwtToken;
import io.github.xpeteliu.feign.OAuthFeignClient;
import io.github.xpeteliu.model.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserLoginService {

    @Autowired
    OAuthFeignClient oAuthFeignClient;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("${basic-auth.token:Basic dGVzdGNsaTp0ZXN0c2N0}")
    String basicAuthToken;

    public LoginResult login(String username, String password) {
        log.info("User {} begins to log in", username);
        log.warn("Basic Auth: {}", basicAuthToken);
        ResponseEntity<JwtToken> responseEntity = oAuthFeignClient.getToken(
                username, password, "password", "admin_login", basicAuthToken);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new AccessDeniedException("Admin login failed");
        }

        JwtToken jwtToken = responseEntity.getBody();
        log.info("Successfully called remote authorization server. Retrieved jwtToken: {}",
                JSON.toJSONString(jwtToken, true));

        String token = jwtToken.getAccessToken();

        redisTemplate.opsForValue().set(token, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);

        JSONObject jwtJson = JSON.parseObject(JwtHelper.decode(token).getClaims());

        List<SysMenu> menus = Lists.newArrayList(
                sysMenuService.findMenusByUserId(jwtJson.getLong("user_name"))
        );

        List<SimpleGrantedAuthority> authorities = jwtJson.getJSONArray("authorities")
                .stream()
                .map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString()))
                .collect(Collectors.toList());

        return new LoginResult(jwtToken.getTokenType() + " " + token, menus, authorities);
    }
}
