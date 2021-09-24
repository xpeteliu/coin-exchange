package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository extends PagingAndSortingRepository<SysRole, Long> {

    @Query("select r.`code` " +
            "from sys_role r left join sys_user_role ur on r.id=ur.role_id " +
            "where ur.user_id=:userId")
    String findCodeByUserId(Long userId);

    // TODO: Directly use Page<SysRole> as return type when spring data JDBC 2.2+ available
    List<SysRole> findByNameContaining(String name, Pageable pageable);

    Long countByNameContaining(String name);

    // TODO: Add deleteAllById here after updated to new version
}
