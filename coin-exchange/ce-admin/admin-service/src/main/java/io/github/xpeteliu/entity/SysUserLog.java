package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user_log")
public class SysUserLog {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "group")
    private String group;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "type")
    private Integer type;

    @Column(value = "method")
    private String method;

    @Column(value = "params")
    private String params;

    @Column(value = "time")
    private Long time;

    @Column(value = "ip")
    private String ip;

    @Column(value = "description")
    private String description;

    @Column(value = "remark")
    private String remark;

    @Column(value = "created")
    private java.sql.Timestamp created;

}
