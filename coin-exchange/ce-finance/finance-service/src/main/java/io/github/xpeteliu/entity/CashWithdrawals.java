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
@Table(name = "cash_withdrawals")
@EntityListeners(AuditingEntityListener.class)
public class CashWithdrawals implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coin_id", nullable = false)
    private Long coinId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "num", nullable = false)
    private BigDecimal num;

    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    @Column(name = "mum", nullable = false)
    private BigDecimal mum;

    @Column(name = "truename", nullable = false)
    private String truename;

    @Column(name = "bank", nullable = false)
    private String bank;

    @Column(name = "bank_prov")
    private String bankProv;

    @Column(name = "bank_city")
    private String bankCity;

    @Column(name = "bank_addr")
    private String bankAddr;

    @Column(name = "bank_card", nullable = false)
    private String bankCard;

    @Column(name = "remark")
    private String remark;

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

    @Column(name = "last_time")
    private Date lastTime;

}
