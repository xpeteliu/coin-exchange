package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.model.DepthResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long>, QuerydslPredicateExecutor<Market> {

    List<Market> findByTradeAreaIdAndStatus(Long tradeAreaId, Integer status);

    Market findBySymbol(String symbol);
}