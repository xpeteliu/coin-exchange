package io.github.xpeteliu.stream;

import io.github.xpeteliu.model.CoinTransferRequest;
import io.github.xpeteliu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class FinanceHandlerConfig {

    @Autowired
    AccountService accountService;

    @Bean
    public Consumer<CoinTransferRequest> receiveCoinTransferRequest() {
        return (transferRequest) -> {
            accountService.handleCoinTransferRequest(transferRequest);
        };
    }
}
