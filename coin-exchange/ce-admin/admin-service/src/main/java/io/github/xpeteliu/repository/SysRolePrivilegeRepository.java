package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysRolePrivilege;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface SysRolePrivilegeRepository extends CrudRepository<SysRolePrivilege, Long> {

    @Query("select privilege_id from sys_role_privilege rp where rp.role_id=:role_id")
    Stream<Long> findAllPrivilegeIdByRoleId(@Param("role_id") Long roleId);

    @Modifying
    @Query("delete from sys_role_privilege rp where rp.role_id=:role_id")
    Long deleteByRoleId(@Param("role_id") Long roleId);
}
