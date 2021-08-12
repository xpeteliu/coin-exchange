package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.TurnoverOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoverOrderRepository extends JpaRepository<TurnoverOrder, Long>, QuerydslPredicateExecutor<TurnoverOrder> {

    List<TurnoverOrder> findByOrderIdAndBuyUserId(Long orderId, Long userId);

    List<TurnoverOrder> findByOrderIdAndSellUserId(Long orderId, Long userId);

    List<TurnoverOrder> findBySymbol(String symbol, Pageable pageable);
}