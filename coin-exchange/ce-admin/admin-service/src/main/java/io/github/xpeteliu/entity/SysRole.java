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
@Table(value = "sys_role")
public class SysRole {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "name")
    @NotNull
    private String name;

    @Column(value = "code")
    @NotNull
    private String code;

    @Column(value = "description")
    private String description;

    @Column(value = "create_by")
    @CreatedBy
    private Long createBy;

    @Column(value = "modify_by")
    @LastModifiedBy
    private Long modifyBy;

    @Column(value = "status")
    private Byte status;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

}
