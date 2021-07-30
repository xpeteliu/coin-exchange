package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.Config;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends PagingAndSortingRepository<Config, Long> {

    List<Config> findByNameContainingAndTypeContainingAndCodeContaining(String name, String type, String code, Pageable pageable);

    Long countByNameContainingAndTypeContainingAndCodeContaining(String name, String type, String code);

    @Modifying
    @Query("delete from config c where c.id in (:ids);")
    void deleteAllById(List<Long> ids);
}
