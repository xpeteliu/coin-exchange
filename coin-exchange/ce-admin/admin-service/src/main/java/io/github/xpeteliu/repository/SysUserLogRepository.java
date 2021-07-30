package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysUserLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserLogRepository extends PagingAndSortingRepository<SysUserLog,Long> {
}
