package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long> {

    List<Notice> findByTitleContainingAndCreatedBetweenAndStatusBetween(String title, Timestamp startTime, Timestamp endTime, Integer statusMin, Integer statusMax, Pageable pageable);

    Long countByTitleContainingAndCreatedBetweenAndStatusBetween(String title, Timestamp startTime, Timestamp endTime, Integer statusMin, Integer statusMax);

    @Modifying
    @Query("delete from notice n where n.id in (:ids);")
    void deleteAllById(List<Long> ids);

    @Modifying
    @Query("update notice n set n.status=:status where n.id=:id;")
    void updateStatusById(Long id, Integer status);
}
