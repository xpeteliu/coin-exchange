package io.github.xpeteliu.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_auth_audit_record")
public class UserAuthAuditRecord {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "auth_code")
    private Long authCode;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "status")
    private Byte status;

    @Column(value = "remark")
    private String remark;

    @Column(value = "step")
    private Byte step;

    @Column(value = "audit_user_id")
    private Long auditUserId;

    @Column(value = "audit_user_name")
    private String auditUserName;

    @Column(value = "created")
    @CreatedDate
    private java.sql.Timestamp created;

}
