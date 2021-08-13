package io.github.xpeteliu.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {
    final StringRedisTemplate redisTemplate;

    @Value("${public.uri:/admin/login,/user/login}")
    Set<String> tokenNotRequiredUris;

    public JwtCheckFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!isTokenRequired(exchange)) {
            return chain.filter(exchange);
        }

        String token = getUserToken(exchange);
        if (!StringUtils.hasLength(token)) {
            return buildUnauthorizedAccessResponse(exchange);
        }

        Boolean hasKey = redisTemplate.hasKey(token);

        if (hasKey != null && hasKey) {
            return chain.filter(exchange);
        }

        return buildUnauthorizedAccessResponse(exchange);
    }

    private Mono<Void> buildUnauthorizedAccessResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject body = new JSONObject();
        body.put("error", "Unauthorized");
        body.put("errorMsg", "Token is null or invalid");
        DataBuffer buffer = response.bufferFactory().wrap(body.toJSONString().getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replaceFirst("^bearer ", "");
    }

    private boolean isTokenRequired(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().toString();
        return !(tokenNotRequiredUris.contains(path) || path.startsWith("/trade/markets/kline/"));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
