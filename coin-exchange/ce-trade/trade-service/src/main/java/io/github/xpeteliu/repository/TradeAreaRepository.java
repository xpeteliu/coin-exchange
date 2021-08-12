package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.TradeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeAreaRepository extends JpaRepository<TradeArea, Long>, QuerydslPredicateExecutor<TradeArea> {

}