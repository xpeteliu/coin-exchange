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
@Table(name = "coin_recharge")
@EntityListeners(AuditingEntityListener.class)
public class CoinRecharge implements Serializable {

    

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

    @Column(name = "address")
    private String address;

    @Column(name = "confirm", nullable = false)
    private Integer confirm;

    @Column(name = "status")
    private Integer status ;

    @Column(name = "txid")
    private String txid;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

}
