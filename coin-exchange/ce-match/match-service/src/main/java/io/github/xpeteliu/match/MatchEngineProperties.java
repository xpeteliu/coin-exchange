package io.github.xpeteliu.match;

import io.github.xpeteliu.disruptor.OrderEventHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "match")
public class MatchEngineProperties {

    private Map<String, CoinScale> scaleInfo;

    @Data
    static class CoinScale {

        private int coinScale;

        private int baseCoinScale;
    }

}
