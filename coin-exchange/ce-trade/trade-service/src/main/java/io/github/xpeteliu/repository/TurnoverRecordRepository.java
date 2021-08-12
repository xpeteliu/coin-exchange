package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.TurnoverRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoverRecordRepository extends JpaRepository<TurnoverRecord, Long>, QuerydslPredicateExecutor<TurnoverRecord> {

}