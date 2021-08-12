package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserAccountFreeze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountFreezeRepository extends JpaRepository<UserAccountFreeze, Long>, QuerydslPredicateExecutor<UserAccountFreeze> {

}