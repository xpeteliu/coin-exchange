package io.github.xpeteliu.stream;

import io.github.xpeteliu.model.TradeRecord;
import io.github.xpeteliu.service.EntrustOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class AfterMatchHandlerConfig {

    @Autowired
    EntrustOrderService entrustOrderService;

    @Bean
    public Consumer<List<TradeRecord>> receiveTradeRecords() {
        return tradeRecords -> {
            tradeRecords.forEach(tradeRecord -> entrustOrderService.postMatchProcess(tradeRecord));
        };
    }

}
