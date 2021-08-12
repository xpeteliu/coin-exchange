package io.github.xpeteliu.match.impl;

import io.github.xpeteliu.match.MatchService;
import io.github.xpeteliu.match.MatchServiceFactory;
import io.github.xpeteliu.match.MatchStrategy;
import io.github.xpeteliu.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LimitOrderMatchService implements MatchService, InitializingBean {

    @Autowired
    StreamBridge streamBridge;

    @Override
    public void match(OrderBook orderBook, Order order) {
        log.info("Begin to match");
        if (order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        if (order.getOrderStatus() == 2) {
            orderBook.cancelOrder(order);
            return;
        }

        OrderDirection direction = order.getOrderDirection();

        List<Order> completedOrders = new ArrayList<>();
        List<TradeRecord> tradeRecords = new ArrayList<>();
        Iterator<Map.Entry<BigDecimal, MergedOrders>> entryIterator = orderBook.getCurrentOrdersIterator(OrderDirection.getReverseOrderDirection(direction));
        matchLoop:
        while (entryIterator.hasNext()) {
            Map.Entry<BigDecimal, MergedOrders> entry = entryIterator.next();
            BigDecimal price = entry.getKey();

            if (direction == OrderDirection.BUY && order.getPrice().compareTo(price) < 0
                    || direction == OrderDirection.SELL && order.getPrice().compareTo(price) > 0) {
                break;
            }

            MergedOrders mergedOrders = entry.getValue();
            Iterator<Order> orderIterator = mergedOrders.iterator();
            while (orderIterator.hasNext()) {
                Order previousOrder = orderIterator.next();
                TradeRecord tradeRecord = processMatch(order, previousOrder, orderBook);
                if (tradeRecord != null) {
                    tradeRecords.add(tradeRecord);
                    if (previousOrder.isCompleted()) {
                        completedOrders.add(previousOrder);
                        orderIterator.remove();
                        orderBook.getMarketDepthTable(direction).remove(previousOrder, tradeRecord.getAmount());
                    }
                    if (order.isCompleted()) {
                        completedOrders.add(order);
                        break matchLoop;
                    }
                }
            }
            if (mergedOrders.size() == 0) {
                entryIterator.remove();
            }
        }

        if (!order.isCompleted()) {
            orderBook.addOrder(order);
            orderBook.getMarketDepthTable(order.getOrderDirection()).add(order);
        }
        if (!CollectionUtils.isEmpty(tradeRecords)) {
            sendTradeRecords(tradeRecords);
        }
        if (!CollectionUtils.isEmpty(completedOrders)) {
            sendCompletedOrders(completedOrders);
            sendMarketDepth(orderBook.getMarketDepthTable(direction));
        }
    }

    private void sendTradeRecords(List<TradeRecord> tradeRecords) {
        streamBridge.send("sendTradeRecords-out-0", tradeRecords);
    }

    private void sendCompletedOrders(List<Order> completedOrders) {
        streamBridge.send("sendCompletedOrders-out-0", completedOrders);
    }

    private void sendMarketDepth(MarketDepthTable marketDepthTable) {
        streamBridge.send("sendMarketDepth-out-0", marketDepthTable);
    }

    private TradeRecord processMatch(Order taker, Order maker, OrderBook orderBook) {
        BigDecimal dealPrice = maker.getPrice();
        BigDecimal needAmount = calcTradableAmount(taker);
        BigDecimal providerAmount = calcTradableAmount(maker);
        BigDecimal turnoverAmount = needAmount.min(providerAmount);
        if (turnoverAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        BigDecimal turnover = turnoverAmount.multiply(dealPrice);

        taker.setTradedAmount(taker.getTradedAmount().add(turnoverAmount));
        taker.setTurnover(taker.getTurnover().add(turnover.setScale(orderBook.getCoinScale(), RoundingMode.HALF_UP)));

        maker.setTradedAmount(maker.getTradedAmount().add(turnoverAmount));
        maker.setTurnover(maker.getTurnover().add(turnover.setScale(orderBook.getBaseCoinScale(), RoundingMode.HALF_UP)));

        Order buyOrder, sellOrder;
        if (taker.getOrderDirection() == OrderDirection.BUY) {
            buyOrder = taker;
            sellOrder = maker;
        } else {
            buyOrder = maker;
            sellOrder = taker;
        }

        TradeRecord tradeRecord = new TradeRecord();
        tradeRecord.setTime(LocalDateTime.now());
        tradeRecord.setAmount(turnoverAmount);
        tradeRecord.setPrice(dealPrice);
        tradeRecord.setSymbol(orderBook.getSymbol());
        tradeRecord.setDirection(taker.getOrderDirection());
        tradeRecord.setBuyOrderId(buyOrder.getOrderId());
        tradeRecord.setSellOrderId(sellOrder.getOrderId());
        tradeRecord.setBuyTurnover(buyOrder.getTurnover());
        tradeRecord.setSellTurnover(sellOrder.getTurnover());

        orderBook.getMarketDepthTable(maker.getOrderDirection()).remove(maker, turnoverAmount);

        return tradeRecord;
    }

    private BigDecimal calcTradableAmount(Order order) {
        return order.getAmount().subtract(order.getTradedAmount());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MatchServiceFactory.addMatchService(MatchStrategy.LIMIT_ORDER_MATCH, this);
    }
}
