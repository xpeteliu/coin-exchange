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
@Table(name = "turnover_order")
@EntityListeners({AuditingEntityListener.class})
public class TurnoverOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "market_id", nullable = false)
    private Long marketId;

    @Column(name = "market_type")
    private Integer marketType;

    @Column(name = "trade_type", nullable = false)
    private Integer tradeType;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "sell_user_id", nullable = false)
    private Long sellUserId;

    @Column(name = "sell_coin_id", nullable = false)
    private Long sellCoinId;

    @Column(name = "sell_order_id", nullable = false)
    private Long sellOrderId;

    @Column(name = "sell_price", nullable = false)
    private BigDecimal sellPrice;

    @Column(name = "sell_fee_rate", nullable = false)
    private BigDecimal sellFeeRate;

    @Column(name = "sell_volume", nullable = false)
    private BigDecimal sellVolume;

    @Column(name = "buy_user_id", nullable = false)
    private Long buyUserId;

    @Column(name = "buy_coin_id", nullable = false)
    private Long buyCoinId;

    @Column(name = "buy_order_id", nullable = false)
    private Long buyOrderId;

    @Column(name = "buy_volume", nullable = false)
    private BigDecimal buyVolume;

    @Column(name = "buy_price", nullable = false)
    private BigDecimal buyPrice;

    @Column(name = "buy_fee_rate", nullable = false)
    private BigDecimal buyFeeRate;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "volume", nullable = false)
    private BigDecimal volume;

    @Column(name = "deal_sell_fee", nullable = false)
    private BigDecimal dealSellFee;

    @Column(name = "deal_sell_fee_rate", nullable = false)
    private BigDecimal dealSellFeeRate;

    @Column(name = "deal_buy_fee", nullable = false)
    private BigDecimal dealBuyFee;

    @Column(name = "deal_buy_fee_rate", nullable = false)
    private BigDecimal dealBuyFeeRate;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created;

}
