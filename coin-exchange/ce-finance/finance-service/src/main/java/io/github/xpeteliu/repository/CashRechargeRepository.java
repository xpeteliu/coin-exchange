package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CashRecharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRechargeRepository extends JpaRepository<CashRecharge, Long>, QuerydslPredicateExecutor<CashRecharge> {

    Page<CashRecharge> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);
}