package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.ForexAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexAccountRepository extends JpaRepository<ForexAccount, Long>, QuerydslPredicateExecutor<ForexAccount> {

}