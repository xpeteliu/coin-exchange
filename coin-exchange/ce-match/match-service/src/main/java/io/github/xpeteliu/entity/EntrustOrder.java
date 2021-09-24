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
@Table(name = "entrust_order")
@EntityListeners({AuditingEntityListener.class})
public class EntrustOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "market_id", nullable = false)
    private Long marketId;

    @Column(name = "market_type")
    private Integer marketType;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "merge_low_price")
    private BigDecimal mergeLowPrice;

    @Column(name = "merge_high_price")
    private BigDecimal mergeHighPrice;

    @Column(name = "volume", nullable = false)
    private BigDecimal volume;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "fee_rate", nullable = false)
    private BigDecimal feeRate;

    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    @Column(name = "contract_unit")
    private Integer contractUnit;

    @Column(name = "deal", nullable = false)
    private BigDecimal deal;

    @Column(name = "freeze", nullable = false)
    private BigDecimal freeze;

    @Column(name = "margin_rate")
    private BigDecimal marginRate;

    @Column(name = "base_coin_rate")
    private BigDecimal baseCoinRate;

    @Column(name = "price_coin_rate")
    private BigDecimal priceCoinRate;

    @Column(name = "lock_margin")
    private BigDecimal lockMargin;

    @Column(name = "price_type", nullable = false)
    private Integer priceType;

    @Column(name = "trade_type")
    private Integer tradeType;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "open_order_id")
    private Long openOrderId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created;

}
