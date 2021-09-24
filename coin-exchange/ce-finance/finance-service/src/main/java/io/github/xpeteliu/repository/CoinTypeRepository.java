package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CoinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CoinTypeRepository extends JpaRepository<CoinType, Long>, QuerydslPredicateExecutor<CoinType> {

    @Transactional
    @Modifying
    @Query("update CoinType set status=:status where id=:id")
    Integer updateStatusById(Long id, Integer status);

    List<CoinType> findAllByStatus(Integer status);
}