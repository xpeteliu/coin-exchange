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
@Table(name = "coin_withdraw")
@EntityListeners(AuditingEntityListener.class)
public class CoinWithdraw implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coin_id", nullable = false)
    private Long coinId;

    @Column(name = "coin_name", nullable = false)
    private String coinName ;

    @Column(name = "coin_type", nullable = false)
    private String coinType ;

    @Column(name = "address", nullable = false)
    private String address ;

    @Column(name = "txid")
    private String txid;

    @Column(name = "num", nullable = false)
    private BigDecimal num;

    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    @Column(name = "mum", nullable = false)
    private BigDecimal mum;

    @Column(name = "type")
    private Integer type ;

    @Column(name = "chain_fee")
    private BigDecimal chainFee;

    @Column(name = "block_num")
    private Integer blockNum ;

    @Column(name = "remark")
    private String remark;

    @Column(name = "wallet_mark")
    private String walletMark;

    @Column(name = "step")
    private Integer step;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "audit_time")
    private Date auditTime;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

}
