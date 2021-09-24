package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysMenu;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuRepository extends CrudRepository<SysMenu,Long> {

    @Query("select m.* from sys_menu m left join sys_role_menu rm on m.id=rm.menu_id " +
            "left join sys_user_role ur on rm.role_id=ur.role_id " +
            "where ur.user_id=:userId")
    Iterable<SysMenu> findAllByUserId(Long userId);
}
