package io.github.xpeteliu.service;

import io.github.xpeteliu.feign.JwtToken;
import io.github.xpeteliu.feign.OAuthFeignClient;
import io.github.xpeteliu.model.LoggedInUser;
import io.github.xpeteliu.model.LoginFormParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserLoginService {

    @Autowired
    OAuthFeignClient oAuthFeignClient;

    @Value("${basic-auth.token:Basic dGVzdGNsaTp0ZXN0c2N0}")
    String basicAuthToken;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public LoggedInUser login(LoginFormParameter loginForm) throws Exception {
        checkFormData(loginForm);
        ResponseEntity<JwtToken> response = oAuthFeignClient.getToken(
                loginForm.getUsername(),
                loginForm.getPassword(),
                "password",
                "member_login",
                basicAuthToken);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Failed to connect to the authorization server");
        }
        JwtToken jwtToken = response.getBody();
        LoggedInUser loggedInUser = new LoggedInUser(
                loginForm.getUsername(),
                jwtToken.getExpiresIn(),
                jwtToken.getTokenType() + " " + jwtToken.getAccessToken(),
                jwtToken.getRefreshToken());
        redisTemplate.opsForValue().set(
                jwtToken.getAccessToken(),
                "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        return loggedInUser;
    }

    private void checkFormData(LoginFormParameter loginForm) {
        //TODO: validate captcha here
    }
}
