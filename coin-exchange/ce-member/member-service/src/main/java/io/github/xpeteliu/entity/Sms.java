package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sms")
public class Sms {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "template_code")
    private String templateCode;

    @Column(value = "country_code")
    private String countryCode;

    @Column(value = "mobile")
    private String mobile;

    @Column(value = "content")
    private String content;

    @Column(value = "status")
    private Integer status;

    @Column(value = "remark")
    private String remark;

    @Column(value = "last_update_time")
    @LastModifiedDate
    private java.sql.Timestamp lastUpdateTime;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

}
