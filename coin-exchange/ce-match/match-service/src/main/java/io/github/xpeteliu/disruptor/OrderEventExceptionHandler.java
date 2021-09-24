package io.github.xpeteliu.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventExceptionHandler implements ExceptionHandler<OrderEvent> {
    @Override
    public void handleEventException(Throwable ex, long sequence, OrderEvent event) {
        log.info("Event Exception: Exception:{} === Seq:{} === Event:{}", ex.getMessage(), sequence, event.toString());
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        log.info("On start: {}", ex.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        log.info("On shutdown: {}", ex.getMessage());
    }
}
