package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CoinBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinBalanceRepository extends JpaRepository<CoinBalance, Long>, QuerydslPredicateExecutor<CoinBalance> {

}