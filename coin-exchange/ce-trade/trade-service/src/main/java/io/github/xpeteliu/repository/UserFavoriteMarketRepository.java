package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserFavoriteMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserFavoriteMarketRepository extends JpaRepository<UserFavoriteMarket, Long>, QuerydslPredicateExecutor<UserFavoriteMarket> {

    List<UserFavoriteMarket> findByUserId(Long userId);

    @Transactional
    void deleteByUserIdAndMarketId(Long userId, Long marketId);
}