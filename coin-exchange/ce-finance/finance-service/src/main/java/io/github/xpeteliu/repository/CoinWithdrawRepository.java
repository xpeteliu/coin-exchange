package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CoinWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinWithdrawRepository extends JpaRepository<CoinWithdraw, Long>, QuerydslPredicateExecutor<CoinWithdraw> {

}