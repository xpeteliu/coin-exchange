package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.TurnoverOrder;
import io.github.xpeteliu.repository.TurnoverOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoverOrderService {

    @Autowired
    TurnoverOrderRepository turnoverOrderRepository;

    public List<TurnoverOrder> findBuyTurnoverOrder(Long orderId, Long userId) {
        return turnoverOrderRepository.findByOrderIdAndBuyUserId(orderId, userId);
    }

    public List<TurnoverOrder> findSellTurnoverOrder(Long orderId, Long userId) {
        return turnoverOrderRepository.findByOrderIdAndSellUserId(orderId, userId);
    }

    public List<TurnoverOrder> findNewestNRecordsBySymbol(String symbol, int n) {
        return turnoverOrderRepository.findBySymbol(symbol, PageRequest.of(0, n, Sort.Direction.DESC, "created"));
    }

    public void save(TurnoverOrder turnoverOrder) {
        turnoverOrderRepository.save(turnoverOrder);
    }
}
