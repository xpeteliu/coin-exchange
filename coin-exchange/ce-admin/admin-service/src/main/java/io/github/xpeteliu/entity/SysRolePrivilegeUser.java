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
@Table(value = "sys_role_privilege_user")
public class SysRolePrivilegeUser {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "role_id")
    private Long roleId;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "privilege_id")
    private Long privilegeId;

}
