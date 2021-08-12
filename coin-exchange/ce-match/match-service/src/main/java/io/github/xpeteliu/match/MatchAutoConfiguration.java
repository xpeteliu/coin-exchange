package io.github.xpeteliu.match;

import io.github.xpeteliu.disruptor.OrderEventHandler;
import io.github.xpeteliu.model.OrderBook;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(MatchEngineProperties.class)
public class MatchAutoConfiguration {

    final MatchEngineProperties matchEngineProperties;

    public MatchAutoConfiguration(MatchEngineProperties matchEngineProperties) {
        this.matchEngineProperties = matchEngineProperties;
    }

    @Bean
    public OrderEventHandler[] orderEventHandlers() {
        Set<Map.Entry<String, MatchEngineProperties.CoinScale>> scaleEntries = matchEngineProperties.getScaleInfo().entrySet();
        OrderEventHandler[] orderEventHandlers = new OrderEventHandler[scaleEntries.size()];
        int i = 0;
        for (Map.Entry<String, MatchEngineProperties.CoinScale> scaleEntry : scaleEntries) {
            String symbol = scaleEntry.getKey();
            MatchEngineProperties.CoinScale coinScale = scaleEntry.getValue();
            OrderBook orderBook;
            if (coinScale == null) {
                orderBook = new OrderBook(symbol);
            } else {
                orderBook = new OrderBook(symbol, coinScale.getCoinScale(), coinScale.getBaseCoinScale());
            }
            orderEventHandlers[i++] = new OrderEventHandler(orderBook);
        }
        return orderEventHandlers;
    }
}
