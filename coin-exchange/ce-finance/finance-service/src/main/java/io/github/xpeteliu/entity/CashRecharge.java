package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "cash_recharge")
@EntityListeners(AuditingEntityListener.class)
public class CashRecharge implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coin_id", nullable = false)
    private Long coinId;

    @Column(name = "coin_name", nullable = false)
    private String coinName;

    @Column(name = "num", nullable = false)
    private BigDecimal num;

    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    @Column(name = "feecoin")
    private String feecoin;

    @Column(name = "mum", nullable = false)
    private BigDecimal mum;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "tradeno", nullable = false)
    private String tradeno;

    @Column(name = "outtradeno")
    private String outtradeno;

    @Column(name = "remark")
    private String remark;

    @Column(name = "audit_remark")
    private String auditRemark;

    @Column(name = "step")
    private Integer step;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "name")
    private String name;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_card")
    private String bankCard;

    @Column(name = "last_time")
    private Date lastTime;

}
