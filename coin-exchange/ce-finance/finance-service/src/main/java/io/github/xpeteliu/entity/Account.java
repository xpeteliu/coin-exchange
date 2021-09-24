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
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class Account implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coin_id", nullable = false)
    private Long coinId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "balance_amount", nullable = false)
    private BigDecimal balanceAmount;

    @Column(name = "freeze_amount", nullable = false)
    private BigDecimal freezeAmount;

    @Column(name = "recharge_amount", nullable = false)
    private BigDecimal rechargeAmount;

    @Column(name = "withdrawals_amount", nullable = false)
    private BigDecimal withdrawalsAmount;

    @Column(name = "net_value", nullable = false)
    private BigDecimal netValue;

    @Column(name = "lock_margin", nullable = false)
    private BigDecimal lockMargin;

    @Column(name = "float_profit", nullable = false)
    private BigDecimal floatProfit;

    @Column(name = "total_profit", nullable = false)
    private BigDecimal totalProfit;

    @Column(name = "rec_addr")
    private String recAddr ;

    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

    @Transient
    private BigDecimal sellRate;

    @Transient
    private BigDecimal buyRate;

}
