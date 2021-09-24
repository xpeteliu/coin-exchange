package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "cash_recharge_audit_record")
@EntityListeners(AuditingEntityListener.class)
public class CashRechargeAuditRecord implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "step")
    private Integer step;

    @Column(name = "audit_user_id")
    private Long auditUserId;

    @Column(name = "audit_user_name")
    private String auditUserName;

    @Column(name = "created")
    @CreatedDate
    private Date created ;

}
