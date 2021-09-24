package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_address")
public class UserAddress {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "coin_id")
    private Long coinId;

    @Column(value = "address")
    private String address;

    @Column(value = "keystore")
    private String keystore;

    @Column(value = "pwd")
    private String pwd;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

    @Column(value = "markid")
    private Long markid;

    @Transient
    private String coinName;

}
