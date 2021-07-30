package io.github.xpeteliu.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry.antMatchers(
                                "/login",
                                "/v3/api-docs",
                                "/swagger-resources/configuration/ui",
                                "/swagger-resources",
                                "/swagger-resources/configuration/security",
                                "/webjars/**",
                                "/swagger-ui/**")
                                .permitAll().anyRequest().authenticated())
                .headers(HeadersConfigurer::cacheControl);
    }

    @Bean
    public TokenStore jwtTokenStore() throws IOException {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws IOException {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        byte[] publicKey = FileCopyUtils.copyToByteArray(new ClassPathResource("coinexchange.pub").getInputStream());
        jwtAccessTokenConverter.setVerifierKey(new String(publicKey, StandardCharsets.UTF_8));
        return jwtAccessTokenConverter;
    }
}
