package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.TradeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeAreaRepository extends JpaRepository<TradeArea, Long>, QuerydslPredicateExecutor<TradeArea> {
    List<TradeArea> findByStatus(Integer status);
}