package io.github.xpeteliu.match;

import io.github.xpeteliu.model.Order;
import io.github.xpeteliu.model.OrderBook;

public interface MatchService {

    void match(OrderBook orderBook, Order order);
}
