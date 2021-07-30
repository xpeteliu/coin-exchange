package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysPrivilege;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPrivilegeRepository extends PagingAndSortingRepository<SysPrivilege, Long> {
    List<SysPrivilege> findByMenuId(Long menuId);
}
