package io.github.xpeteliu.disruptor;

import com.lmax.disruptor.EventHandler;
import io.github.xpeteliu.match.MatchServiceFactory;
import io.github.xpeteliu.match.MatchStrategy;
import io.github.xpeteliu.model.OrderBook;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OrderEventHandler implements EventHandler<OrderEvent> {

    OrderBook orderBook;

    String symbol;

    public OrderEventHandler(OrderBook orderBook) {
        this.orderBook = orderBook;
        this.symbol = orderBook.getSymbol();
    }

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        if (!orderBook.getSymbol().equals(event.getSource().getSymbol())) {
            return;
        }
        log.info("Received order {}..., sending to match service", event.getSource());
        MatchServiceFactory.getMatchService(MatchStrategy.LIMIT_ORDER_MATCH).match(orderBook, event.getSource());
    }
}
