package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.ForexOpenPositionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexOpenPositionOrderRepository extends JpaRepository<ForexOpenPositionOrder, Long>, QuerydslPredicateExecutor<ForexOpenPositionOrder> {

}