package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long>, QuerydslPredicateExecutor<AccountDetail> {

}