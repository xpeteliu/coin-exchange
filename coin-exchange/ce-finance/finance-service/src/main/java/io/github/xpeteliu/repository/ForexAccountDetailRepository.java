package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.ForexAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexAccountDetailRepository extends JpaRepository<ForexAccountDetail, Long>, QuerydslPredicateExecutor<ForexAccountDetail> {

}