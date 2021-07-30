package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_login_log")
public class UserLoginLog {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "client_type")
    private Byte clientType;

    @Column(value = "login_ip")
    private String loginIp;

    @Column(value = "login_address")
    private String loginAddress;

    @Column(value = "login_time")
    private java.sql.Timestamp loginTime;

}
