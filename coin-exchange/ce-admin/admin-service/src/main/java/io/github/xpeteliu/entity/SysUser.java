package io.github.xpeteliu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user")
public class SysUser {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "username")
    @NotBlank
    private String username;

    @Column(value = "password")
    @NotBlank
    private String password;

    @Column(value = "fullname")
    private String fullname;

    @Column(value = "mobile")
    private String mobile;

    @Column(value = "email")
    private String email;

    @Column(value = "status")
    private Byte status;

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
    @JsonProperty("role_strings")
    private String roleStr;

}
