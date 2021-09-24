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
@Table(name = "coin_balance")
@EntityListeners(AuditingEntityListener.class)
public class CoinBalance implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "coin_name")
    private String coinName;

    @Column(name = "system_balance")
    private BigDecimal systemBalance;

    @Column(name = "coin_type")
    private String coinType;

    @Column(name = "collect_account_balance")
    private BigDecimal collectAccountBalance;

    @Column(name = "loan_account_balance")
    private BigDecimal loanAccountBalance;

    @Column(name = "fee_account_balance")
    private BigDecimal feeAccountBalance;

    @Column(name = "last_update_time")
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created")
    @CreatedDate
    private Date created ;

    @Column(name = "recharge_account_balance")
    private BigDecimal rechargeAccountBalance;

}
