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
@Table(name = "forex_open_position_order")
@EntityListeners(AuditingEntityListener.class)
public class ForexOpenPositionOrder implements Serializable {

    

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "coin_id")
    private Long coinId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "entrust_order_id")
    private Long entrustOrderId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "num")
    private BigDecimal num ;

    @Column(name = "lock_margin")
    private BigDecimal lockMargin ;

    @Column(name = "close_num")
    private BigDecimal closeNum ;

    @Column(name = "status")
    private Integer status;

    @Column(name = "last_update_time")
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created")
    @CreatedDate
    private Date created ;

}
