package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.EntrustOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrustOrderRepository extends JpaRepository<EntrustOrder, Long>, QuerydslPredicateExecutor<EntrustOrder> {

    List<EntrustOrder> findByUserIdAndSymbol(Long userId, String symbol, Pageable pageable);

    Long countByUserIdAndSymbol(Long userId, String symbol);

    List<EntrustOrder> findByUserIdAndSymbolAndStatus(Long userId, String symbol, Integer status, Pageable pageable);

    Long countByUserIdAndSymbolAndStatus(Long userId, String symbol, Integer status);
}