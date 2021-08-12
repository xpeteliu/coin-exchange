package io.github.xpeteliu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Data
@NoArgsConstructor
public class MarketDepthTable {

    /**
     * 判断数据的详情
     */
    private List<DepthItemResult> rows = new LinkedList<>();
    /**
     * 最大支持的深度
     */
    private int maxDepth = 100;

    /**
     * 订单的方向
     */
    private OrderDirection direction;

    /**
     * 交易对
     */
    private String symbol;

    public MarketDepthTable(OrderDirection direction, String symbol) {
        this.direction = direction;
        this.symbol = symbol;
    }

    public void remove(Order order, BigDecimal tradedAmount) {
        if (rows.size() == 0 || order.getOrderDirection() != direction) {
            return;
        }
        Iterator<DepthItemResult> iterator = rows.iterator();
        while (iterator.hasNext()) {
            DepthItemResult row = iterator.next();
            if (row.getPrice().equals(order.getPrice())) {
                row.setVolume(row.getVolume().subtract(tradedAmount));
                if (row.getVolume().compareTo(BigDecimal.ZERO) <= 0) {
                    iterator.remove();
                }
            }
        }
    }

    public void add(Order order) {
        if (order.getOrderDirection() != direction) {
            return;
        }
        ListIterator<DepthItemResult> iterator = rows.listIterator();
        while (iterator.hasNext()) {
            DepthItemResult row = iterator.next();
            int comparison = row.getPrice().compareTo(order.getPrice());
            if (comparison == 0) {
                row.setVolume(row.getVolume().add(order.getAmount().subtract(order.getTradedAmount())));
                return;
            }
            if (direction == OrderDirection.BUY && comparison < 0
                    || direction == OrderDirection.SELL && comparison > 0) {
                iterator.previous();
                break;
            }
        }
        iterator.add(
                new DepthItemResult(
                        order.getPrice(),
                        order.getAmount().subtract(order.getTradedAmount())
                ));
    }
}

