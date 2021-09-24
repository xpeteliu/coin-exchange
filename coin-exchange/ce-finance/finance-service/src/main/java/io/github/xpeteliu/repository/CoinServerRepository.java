package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CoinServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinServerRepository extends JpaRepository<CoinServer, Long>, QuerydslPredicateExecutor<CoinServer> {

}