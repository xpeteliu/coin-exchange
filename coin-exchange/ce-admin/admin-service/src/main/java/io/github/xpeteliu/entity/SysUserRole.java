package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user_role")
public class SysUserRole {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "role_id")
    private Long roleId;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "create_by")
    @CreatedBy
    private Long createBy;

    @Column(value = "modify_by")
    @LastModifiedBy
    private Long modifyBy;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

}
