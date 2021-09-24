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
@Table(name = "coin")
@EntityListeners(AuditingEntityListener.class)
public class Coin implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "img", nullable = false)
    private String img;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "wallet", nullable = false)
    private String wallet;

    @Column(name = "round", nullable = false)
    private Integer round;

    @Column(name = "base_amount")
    private BigDecimal baseAmount;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    @Column(name = "day_max_amount")
    private BigDecimal dayMaxAmount;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "auto_out")
    private Double autoOut;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "min_fee_num")
    private BigDecimal minFeeNum;

    @Column(name = "withdraw_flag")
    private Integer withdrawFlag;

    @Column(name = "recharge_flag")
    private Integer rechargeFlag;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

}
