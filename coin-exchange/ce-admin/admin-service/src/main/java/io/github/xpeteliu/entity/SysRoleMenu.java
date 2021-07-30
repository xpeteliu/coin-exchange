package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_role_menu")
public class SysRoleMenu {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "role_id")
    private Long roleId;

    @Column(value = "menu_id")
    private Long menuId;

    @Column(value = "create_by")
    private Long createBy;

    @Column(value = "modify_by")
    private Long modifyBy;

    @Column(value = "created")
    private java.sql.Timestamp created;

    @Column(value = "last_update_time")
    private java.sql.Timestamp lastUpdateTime;

}
