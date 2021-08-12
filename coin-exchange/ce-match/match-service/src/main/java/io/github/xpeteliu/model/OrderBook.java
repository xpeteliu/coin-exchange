package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Data
public class OrderBook {

    /**
     * 买入的限价交易 价格从高到底
     * eg: 价格越高，越容易买到
     * Key: 价格
     * MergeOrder 同价格的订单，订单按照时间排序
     */
    private TreeMap<BigDecimal, MergedOrders> buyLimitPrice;

    /**
     * 卖出的限价交易，价格从低到高
     * eg: 价格越低，卖出的越容易
     */
    private TreeMap<BigDecimal, MergedOrders> sellLimitPrice;
    /**
     * 交易的币种
     */
    private String symbol;

    /**
     * 交易币种的精度
     */
    private int coinScale;

    /**
     * 基币的精度
     */
    private int baseCoinScale;

    private MarketDepthTable buyDepthTable;

    private MarketDepthTable sellDepthTable;


    /**
     * 日期格式器
     */
    private SimpleDateFormat dateTimeFormat;

    public OrderBook(String coinPairSymbol) {
        this(coinPairSymbol, 4, 4);
    }

    public OrderBook(String coinPairSymbol, int coinScale, int baseCoinScale) {
        this.symbol = coinPairSymbol;
        this.coinScale = coinScale;
        this.baseCoinScale = baseCoinScale;
        this.initialize();
    }

    public void initialize() {
        buyLimitPrice = new TreeMap<>(Comparator.reverseOrder());
        sellLimitPrice = new TreeMap<>(Comparator.naturalOrder());
        buyDepthTable = new MarketDepthTable(OrderDirection.BUY, symbol);
        sellDepthTable = new MarketDepthTable(OrderDirection.SELL, symbol);
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public TreeMap<BigDecimal, MergedOrders> getCurrentOrders(OrderDirection orderDirection) {
        return orderDirection == OrderDirection.BUY ? buyLimitPrice : sellLimitPrice;
    }

    public Iterator<Map.Entry<BigDecimal, MergedOrders>> getCurrentOrdersIterator(OrderDirection orderDirection) {
        return getCurrentOrders(orderDirection).entrySet().iterator();
    }

    public MarketDepthTable getMarketDepthTable(OrderDirection orderDirection) {
        return orderDirection == OrderDirection.BUY ? buyDepthTable : sellDepthTable;
    }

    public void addOrder(Order order) {
        TreeMap<BigDecimal, MergedOrders> currentOrders = getCurrentOrders(order.getOrderDirection());
        MergedOrders mergedOrders = currentOrders.get(order.getPrice());

        if (mergedOrders == null) {
            mergedOrders = new MergedOrders();
            currentOrders.put(order.getPrice(), mergedOrders);
        }
        mergedOrders.add(order);
    }

    public void cancelOrder(Order order) {
        TreeMap<BigDecimal, MergedOrders> currentOrders = getCurrentOrders(order.getOrderDirection());
        MergedOrders mergedOrders = currentOrders.get(order.getPrice());
        if (mergedOrders == null) {
            return;
        }
        Iterator<Order> iterator = mergedOrders.iterator();
        while (iterator.hasNext()) {
            Order o = iterator.next();
            if (order.getOrderId().equals(o.getOrderId())) {
                iterator.remove();
            }
        }
        if (mergedOrders.size() <= 0) {
            currentOrders.remove(order.getPrice());
        }
    }

    public Map.Entry<BigDecimal, MergedOrders> getFirstEntry(OrderDirection orderDirection) {
        return getCurrentOrders(orderDirection).firstEntry();
    }
}

