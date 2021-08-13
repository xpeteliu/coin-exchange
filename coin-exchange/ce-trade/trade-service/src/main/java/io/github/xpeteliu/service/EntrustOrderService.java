package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.EntrustOrder;
import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.entity.TurnoverOrder;
import io.github.xpeteliu.feign.AccountServiceFeignClient;
import io.github.xpeteliu.feign.CoinServiceFeignClient;
import io.github.xpeteliu.model.CoinTransferRequest;
import io.github.xpeteliu.model.EntrustOrderResult;
import io.github.xpeteliu.model.EntrustedOrderParam;
import io.github.xpeteliu.model.TradeRecord;
import io.github.xpeteliu.repository.EntrustOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EntrustOrderService {

    @Autowired
    EntrustOrderRepository entrustOrderRepository;

    @Autowired
    TurnoverOrderService turnoverOrderService;

    @Autowired
    MarketService marketService;

    @Autowired
    CoinServiceFeignClient coinServiceFeignClient;

    @Autowired
    AccountServiceFeignClient accountServiceFeignClient;

    @Autowired
    StreamBridge streamBridge;


    private EntrustOrder findById(Long id) {
        return entrustOrderRepository.findById(id).orElse(null);
    }

    public Page<EntrustOrderResult> findHistoryEntrustOrderWithPaging(String symbol, Pageable pageable) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<EntrustOrder> entrustOrders = entrustOrderRepository.findByUserIdAndSymbol(userId, symbol, pageable);
        Long cnt = entrustOrderRepository.countByUserIdAndSymbol(userId, symbol);
        List<EntrustOrderResult> entrustOrderResults = entrustOrders2entrustOrderResults(entrustOrders);
        return new PageImpl<>(entrustOrderResults, pageable, cnt);
    }

    public Page<EntrustOrderResult> findOngoingEntrustOrderWithPaging(String symbol, Pageable pageable) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<EntrustOrder> entrustOrders = entrustOrderRepository.findByUserIdAndSymbolAndStatus(userId, symbol, 0, pageable);
        Long cnt = entrustOrderRepository.countByUserIdAndSymbolAndStatus(userId, symbol, 0);
        List<EntrustOrderResult> entrustOrderResults = entrustOrders2entrustOrderResults(entrustOrders);
        return new PageImpl<>(entrustOrderResults, pageable, cnt);
    }

    private List<EntrustOrderResult> entrustOrders2entrustOrderResults(List<EntrustOrder> entrustOrders) {
        List<EntrustOrderResult> entrustOrderResults = new ArrayList<>(entrustOrders.size());
        for (EntrustOrder entrustOrder : entrustOrders) {
            entrustOrderResults.add(entrustOrder2EntrustOrderResult(entrustOrder));
        }
        return entrustOrderResults;
    }

    private EntrustOrderResult entrustOrder2EntrustOrderResult(EntrustOrder entrustOrder) {
        EntrustOrderResult entrustOrderResult = new EntrustOrderResult();
        entrustOrderResult.setOrderId(entrustOrder.getId());
        entrustOrderResult.setCreated(entrustOrder.getCreated());
        entrustOrderResult.setStatus(entrustOrder.getStatus());
        entrustOrderResult.setAmount(entrustOrder.getAmount());
        entrustOrderResult.setDealVolume(entrustOrder.getDeal());
        entrustOrderResult.setPrice(entrustOrder.getPrice());
        entrustOrderResult.setVolume(entrustOrder.getVolume());

        entrustOrderResult.setType(entrustOrder.getType());

        BigDecimal dealAmount = BigDecimal.ZERO;
        BigDecimal dealVolume = BigDecimal.ZERO;
        if (entrustOrderResult.getType() == 1) {
            List<TurnoverOrder> buyTurnoverOrders = turnoverOrderService.findBuyTurnoverOrder(entrustOrder.getId(), entrustOrder.getUserId());
            if (!CollectionUtils.isEmpty(buyTurnoverOrders)) {
                for (TurnoverOrder buyTurnoverOrder : buyTurnoverOrders) {
                    BigDecimal amount = buyTurnoverOrder.getAmount();
                    dealAmount = dealAmount.add(amount);
                }
            }

        }
        if (entrustOrderResult.getType() == 2) {
            List<TurnoverOrder> sellTurnoverOrders = turnoverOrderService.findSellTurnoverOrder(entrustOrder.getId(), entrustOrder.getUserId());
            if (!CollectionUtils.isEmpty(sellTurnoverOrders)) {
                for (TurnoverOrder sellTurnoverOrder : sellTurnoverOrders) {
                    BigDecimal amount = sellTurnoverOrder.getAmount();
                    dealAmount = dealAmount.add(amount);
                }
            }
        }


        entrustOrderResult.setDealAmount(dealAmount);
        entrustOrderResult.setDealVolume(entrustOrder.getDeal());
        BigDecimal dealAvgPrice = BigDecimal.ZERO;
        if (dealAmount.compareTo(BigDecimal.ZERO) > 0) {
            dealAvgPrice = dealAmount.divide(entrustOrder.getDeal(), 8, RoundingMode.HALF_UP);
        }
        entrustOrderResult.setDealAvgPrice(dealAvgPrice);
        return entrustOrderResult;
    }

    @Transactional
    public void createEntrustedOrder(EntrustedOrderParam entrustedOrderParam) {
        @NotBlank String symbol = entrustedOrderParam.getSymbol();
        Market marketExample = new Market();
        marketExample.setSymbol(symbol);
        List<Market> markets = marketService.findByExample(Example.of(marketExample));
        if (markets == null || markets.size() != 1) {
            throw new IllegalArgumentException("Non-existent or non-unique symbol");
        }
        Market market = markets.get(0);
        BigDecimal price = entrustedOrderParam.getPrice().setScale(market.getPriceScale(), RoundingMode.HALF_UP);
        BigDecimal volume = entrustedOrderParam.getVolume().setScale(market.getNumScale(), RoundingMode.HALF_UP);

        @NotNull BigDecimal numMax = market.getNumMax();
        @NotNull BigDecimal numMin = market.getNumMin();
        if (volume.compareTo(numMin) < 0 || volume.compareTo(numMax) > 0) {
            throw new IllegalArgumentException("Trade volume out of valid range");
        }

        BigDecimal mum = price.multiply(volume);
        @NotNull BigDecimal mumMax = market.getTradeMax();
        @NotNull BigDecimal mumMin = market.getTradeMin();
        if (mum.compareTo(mumMin) < 0 || mum.compareTo(mumMax) > 0) {
            throw new IllegalArgumentException("Trade value out of valid range");
        }

        BigDecimal feeRate = entrustedOrderParam.getType() == 1 ? market.getFeeBuy() : market.getFeeSell();
        BigDecimal fee = mum.multiply(feeRate);

        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        EntrustOrder entrustOrder = new EntrustOrder();
        entrustOrder.setUserId(userId);
        entrustOrder.setAmount(mum);
        entrustOrder.setPrice(price);
        entrustOrder.setVolume(volume);
        entrustOrder.setFeeRate(feeRate);
        entrustOrder.setFee(fee);
        entrustOrder.setMarketId(market.getId());
        entrustOrder.setMarketName(market.getName());
        entrustOrder.setMarketType(market.getType());
        entrustOrder.setSymbol(market.getSymbol());
        entrustOrder.setDeal(BigDecimal.ZERO);
        entrustOrder.setFreeze(mum.add(fee));
        entrustOrder.setType(entrustedOrderParam.getType());
        entrustOrder.setMarketType(1);
        entrustOrder.setStatus(0);
        entrustOrder.setPriceType(1);

        entrustOrderRepository.save(entrustOrder);

        Long coinId = entrustedOrderParam.getType() == 1 ? market.getBuyCoinId() : market.getSellCoinId();
        accountServiceFeignClient.freezeAccountBalance(userId, coinId, entrustOrder.getFreeze(), "trade_create", entrustOrder.getId(), fee);

        streamBridge.send("createOrder-out-0", entrustOrder);
    }

    @Transactional
    public void postMatchProcess(TradeRecord tradeRecord) {
        EntrustOrder sellOrder = findById(Long.valueOf(tradeRecord.getSellOrderId()));
        EntrustOrder buyOrder = findById(Long.valueOf(tradeRecord.getBuyOrderId()));

        Long marketId = sellOrder.getMarketId();
        Market market = marketService.findById(marketId);

        // createTurnoverRecord(tradeRecord, buyOrder, sellOrder, market);
        updateEntrustOrder(tradeRecord, buyOrder, sellOrder, market);
        updateAccountBalance(tradeRecord, buyOrder, sellOrder, market);
    }

    private void updateAccountBalance(TradeRecord tradeRecord, EntrustOrder buyOrder, EntrustOrder sellOrder, Market market) {

        streamBridge.send("sendCoinTransferRequest-out-0",
                new CoinTransferRequest(
                        buyOrder.getUserId(),
                        sellOrder.getUserId(),
                        market.getBuyCoinId(),
                        market.getSellCoinId(),
                        tradeRecord.getBuyTurnover(),
                        tradeRecord.getAmount(),
                        Long.valueOf(tradeRecord.getBuyOrderId()),
                        Long.valueOf(tradeRecord.getSellOrderId()),
                        "Coin pair trade"
                )
        );

    }

    private void updateEntrustOrder(TradeRecord tradeRecord, EntrustOrder buyOrder, EntrustOrder sellOrder, Market market) {
        sellOrder.setDeal(sellOrder.getDeal().add(tradeRecord.getAmount()));
        if (tradeRecord.getAmount().compareTo(sellOrder.getVolume()) == 0) {
            sellOrder.setStatus(1);
        }
        entrustOrderRepository.save(sellOrder);

        buyOrder.setDeal(buyOrder.getDeal().add(tradeRecord.getAmount()));
        if (tradeRecord.getAmount().compareTo(buyOrder.getVolume()) == 0) {
            buyOrder.setStatus(1);
        }
        entrustOrderRepository.save(buyOrder);
    }

    private void createTurnoverRecord(TradeRecord tradeRecord, EntrustOrder buyOrder, EntrustOrder sellOrder, Market market) {
        TurnoverOrder sellTurnoverOrder = new TurnoverOrder();
        sellTurnoverOrder.setSellOrderId(sellOrder.getId());
        sellTurnoverOrder.setBuyCoinId(buyOrder.getId());
        sellTurnoverOrder.setBuyVolume(tradeRecord.getAmount());
        sellTurnoverOrder.setAmount(tradeRecord.getSellTurnover());

        sellTurnoverOrder.setBuyCoinId(market.getBuyCoinId());
        sellTurnoverOrder.setSellCoinId(market.getSellCoinId());
        sellTurnoverOrder.setCreated(new Date());
        sellTurnoverOrder.setBuyUserId(buyOrder.getUserId());
        sellTurnoverOrder.setSellUserId(sellOrder.getUserId());
        sellTurnoverOrder.setPrice(tradeRecord.getPrice());
        sellTurnoverOrder.setBuyPrice(buyOrder.getPrice());
        sellTurnoverOrder.setTradeType(2);
        turnoverOrderService.save(sellTurnoverOrder);

        TurnoverOrder buyTurnoverOrder = new TurnoverOrder();
        buyTurnoverOrder.setBuyOrderId(buyOrder.getId());
        buyTurnoverOrder.setSellOrderId(sellOrder.getId());
        buyTurnoverOrder.setAmount(tradeRecord.getBuyTurnover());
        buyTurnoverOrder.setBuyVolume(tradeRecord.getAmount());
        buyTurnoverOrder.setSellUserId(sellOrder.getUserId());
        buyTurnoverOrder.setBuyUserId(buyOrder.getUserId());
        buyTurnoverOrder.setSellCoinId(market.getSellCoinId());
        buyTurnoverOrder.setBuyCoinId(market.getBuyCoinId());
        buyTurnoverOrder.setCreated(new Date());
        sellTurnoverOrder.setTradeType(1);
        turnoverOrderService.save(sellTurnoverOrder);
    }

    @Transactional
    public void revokeEntrustedOrder(Long id) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        EntrustOrder cancellingOrder = new EntrustOrder();
        cancellingOrder.setId(id);
        cancellingOrder.setUserId(userId);
        cancellingOrder.setStatus(2);
        boolean result = streamBridge.send("createOrder-out-0", cancellingOrder);
        if (!result) {
            throw new IllegalStateException("Unable to send request to message queue");
        }

        EntrustOrder entrustOrder = entrustOrderRepository.findById(id).orElse(null);
        if (entrustOrder == null) {
            return;
        }
        entrustOrder.setStatus(2);
        entrustOrderRepository.save(entrustOrder);
    }

}
