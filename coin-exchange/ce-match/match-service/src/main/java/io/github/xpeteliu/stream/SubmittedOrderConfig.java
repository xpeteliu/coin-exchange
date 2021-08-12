package io.github.xpeteliu.stream;

import io.github.xpeteliu.disruptor.DisruptorTemplate;
import io.github.xpeteliu.entity.EntrustOrder;
import io.github.xpeteliu.model.Order;
import io.github.xpeteliu.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class SubmittedOrderConfig {

    @Autowired
    DisruptorTemplate disruptorTemplate;

    @Bean
    public Consumer<EntrustOrder> forwardSubmittedOrder() {
        return entrustOrder -> {
            Order order = BeanUtils.entrustOrder2Order(entrustOrder);
            disruptorTemplate.publish(order);
            log.info("Order [{}] forwarded to disruptor", order);
        };
    }
}
