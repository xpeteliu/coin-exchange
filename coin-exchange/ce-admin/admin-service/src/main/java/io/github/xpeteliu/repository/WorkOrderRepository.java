package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.WorkOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WorkOrderRepository extends PagingAndSortingRepository<WorkOrder, Long> {

    List<WorkOrder> findByCreatedBetweenAndStatusBetween(Timestamp startTime, Timestamp endTime, Integer statusMin, Integer statusMax, Pageable pageable);

    Long countByCreatedBetweenAndStatusBetween(Timestamp startTime, Timestamp endTime, Integer statusMin, Integer statusMax);

    @Modifying
    @Query("update work_order wo set wo.status=2,wo.answer=:answer where wo.id=:id;")
    void replyById(Long id, String answer);
}
