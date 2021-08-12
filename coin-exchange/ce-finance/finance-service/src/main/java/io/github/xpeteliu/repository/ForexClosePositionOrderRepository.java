package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.ForexClosePositionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexClosePositionOrderRepository extends JpaRepository<ForexClosePositionOrder, Long>, QuerydslPredicateExecutor<ForexClosePositionOrder> {

}