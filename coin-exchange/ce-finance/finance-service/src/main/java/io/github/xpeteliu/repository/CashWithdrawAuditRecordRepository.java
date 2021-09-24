package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.CashWithdrawAuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CashWithdrawAuditRecordRepository extends JpaRepository<CashWithdrawAuditRecord, Long>, QuerydslPredicateExecutor<CashWithdrawAuditRecord> {

}