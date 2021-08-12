package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserCoinFreeze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCoinFreezeRepository extends JpaRepository<UserCoinFreeze, Long>, QuerydslPredicateExecutor<UserCoinFreeze> {

}