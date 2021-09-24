package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysUserRole;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysUserRoleRepository extends CrudRepository<SysUserRole,Long> {
    List<SysUserRole> findByUserId(Long userId);

    @Modifying
    @Query("delete from sys_user_role ur where ur.user_id=:user_id;")
    Long deleteByUserId(@Param("user_id") Long userId);

    @Modifying
    @Query("delete from sys_user_role ur where ur.user_id in (:user_id);")
    void deleteAllByUserId(@Param("user_id") List<Long> ids);
}
