package io.github.xpeteliu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_privilege")
public class SysPrivilege {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "menu_id")
    @NotNull
    private Long menuId;

    @Column(value = "name")
    @NotNull
    private String name;

    @Column(value = "description")
    private String description;

    @Column(value = "url")
    private String url;

    @Column(value = "method")
    private String method;

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

    @Transient
    private int own;
}
