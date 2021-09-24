package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.EntrustOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrustOrderRepository extends JpaRepository<EntrustOrder, Long> {

    List<EntrustOrder> findByStatus(Integer status, Sort sort);
}