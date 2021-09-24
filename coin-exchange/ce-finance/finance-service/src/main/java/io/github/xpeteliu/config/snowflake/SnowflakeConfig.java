package io.github.xpeteliu.config.snowflake;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Value("${snowflake.appId:1}")
    private Integer appId;

    @Value("${snowflake.dataCenterId:1}")
    private Integer dataCenterId;

    @Bean
    public Snowflake snowflakeIdGenerator(){
        return new Snowflake();
    }
}
