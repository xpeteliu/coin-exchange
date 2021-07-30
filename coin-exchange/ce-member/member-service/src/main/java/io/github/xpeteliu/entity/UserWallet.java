package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_wallet")
public class UserWallet {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "coin_id")
    private Long coinId;

    @Column(value = "coin_name")
    private String coinName;

    @Column(value = "name")
    private String name;

    @Column(value = "addr")
    private String addr;

    @Column(value = "sort")
    private Integer sort;

    @Column(value = "status")
    private Byte status;

    @Column(value = "last_update_time")
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    private java.sql.Timestamp created;

}
