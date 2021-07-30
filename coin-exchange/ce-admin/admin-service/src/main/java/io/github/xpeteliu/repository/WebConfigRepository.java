package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.WebConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebConfigRepository extends PagingAndSortingRepository<WebConfig, Long> {

    List<WebConfig> findByNameContainingAndTypeContaining(String name, String type, Pageable pageable);

    Long countByNameContainingAndTypeContaining(String name, String type);

    @Modifying
    @Query("delete from web_config wc where wc.id in (:ids);")
    void deleteAllById(List<Long> ids);
}
