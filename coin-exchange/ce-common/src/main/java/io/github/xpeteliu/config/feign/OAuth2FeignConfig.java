package io.github.xpeteliu.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Slf4j
public class OAuth2FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes==null){
            return;
        }
        String token = ((ServletRequestAttributes) requestAttributes).getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasLength(token)){
            requestTemplate.header(HttpHeaders.AUTHORIZATION,token);
        }
    }
}
