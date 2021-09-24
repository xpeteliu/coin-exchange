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
@Table(name = "forex_close_position_order")
@EntityListeners(AuditingEntityListener.class)
public class ForexClosePositionOrder implements Serializable {

    

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
    private BigDecimal num;

    @Column(name = "open_id")
    private Long openId;

    @Column(name = "profit")
    private BigDecimal profit;

    @Column(name = "unlock_margin")
    private BigDecimal unlockMargin;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime ;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created ;

}
