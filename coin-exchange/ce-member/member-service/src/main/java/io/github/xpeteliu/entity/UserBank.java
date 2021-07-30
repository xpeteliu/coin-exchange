package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_bank")
public class UserBank {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "remark")
    private String remark;

    @Column(value = "real_name")
    private String realName;

    @Column(value = "bank")
    private String bank;

    @Column(value = "bank_prov")
    private String bankProv;

    @Column(value = "bank_city")
    private String bankCity;

    @Column(value = "bank_addr")
    private String bankAddr;

    @Column(value = "bank_card")
    private String bankCard;

    @Column(value = "status")
    private Byte status;

    @Column(value = "last_update_time")
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    private java.sql.Timestamp created;

}
