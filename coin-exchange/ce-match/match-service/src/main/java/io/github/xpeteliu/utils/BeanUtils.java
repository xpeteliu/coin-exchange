package io.github.xpeteliu.utils;

import io.github.xpeteliu.entity.EntrustOrder;
import io.github.xpeteliu.model.Order;
import io.github.xpeteliu.model.OrderDirection;

public class BeanUtils {

    static public Order entrustOrder2Order(EntrustOrder entrustOrder) {
        Order order = new Order();
        order.setOrderId(entrustOrder.getId().toString());
        order.setUserId(entrustOrder.getUserId());
        order.setOrderStatus(entrustOrder.getStatus());

        if (entrustOrder.getStatus() == 2) {
            return order;
        }

        order.setSymbol(entrustOrder.getSymbol());
        order.setAmount(entrustOrder.getVolume().subtract(entrustOrder.getDeal()));
        order.setPrice(entrustOrder.getPrice());
        order.setTime(entrustOrder.getCreated().getTime());
        order.setOrderDirection(OrderDirection.getOrderDirectionByCode(entrustOrder.getType()));
        return order;
    }
}
