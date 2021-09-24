package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.AddressPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressPoolRepository extends JpaRepository<AddressPool, Long>, QuerydslPredicateExecutor<AddressPool> {

}