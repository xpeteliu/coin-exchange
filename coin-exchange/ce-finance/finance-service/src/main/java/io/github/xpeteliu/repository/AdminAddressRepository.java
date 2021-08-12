package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.AdminAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAddressRepository extends JpaRepository<AdminAddress, Long>, QuerydslPredicateExecutor<AdminAddress> {

    Page<AdminAddress> findAllByCoinId(Long coinId, Pageable pageable);
}