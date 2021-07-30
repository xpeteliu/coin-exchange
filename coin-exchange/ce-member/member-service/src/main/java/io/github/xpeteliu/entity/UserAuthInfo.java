package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_auth_info")
public class UserAuthInfo {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "image_url")
    private String imageUrl;

    @Column(value = "serialno")
    private Integer serialno;

    @Column(value = "last_update_time")
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    private java.sql.Timestamp created;

    @Column(value = "auth_code")
    private Long authCode;

}
