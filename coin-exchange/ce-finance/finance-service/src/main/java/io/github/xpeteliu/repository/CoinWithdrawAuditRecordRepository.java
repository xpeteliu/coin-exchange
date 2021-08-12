package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CoinWithdrawAuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinWithdrawAuditRecordRepository extends JpaRepository<CoinWithdrawAuditRecord, Long>, QuerydslPredicateExecutor<CoinWithdrawAuditRecord> {

}