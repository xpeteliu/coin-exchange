package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.Coin;
import io.github.xpeteliu.entity.CoinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long>, QuerydslPredicateExecutor<Coin> {

    @Transactional
    @Modifying
    @Query("update Coin set status=:status where id=:id")
    Integer updateStatusById(Long id, Integer status);

    List<Coin> findAllByStatus(Integer status);

    Coin findByName(String coinName);
}