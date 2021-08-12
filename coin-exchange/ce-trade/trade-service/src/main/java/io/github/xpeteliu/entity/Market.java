package io.github.xpeteliu.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "market")
@EntityListeners({AuditingEntityListener.class})
public class Market implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "trade_area_id", nullable = false)
    @NotNull
    private Long tradeAreaId;

    @Column(name = "sell_coin_id")
    @NotNull
    private Long sellCoinId;

    @Column(name = "buy_coin_id", nullable = false)
    @NotNull
    private Long buyCoinId;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "img")
    private String img;

    @Column(name = "open_price", nullable = false)
    @NotNull
    private BigDecimal openPrice;

    @Column(name = "fee_buy", nullable = false)
    @NotNull
    private BigDecimal feeBuy;

    @Column(name = "fee_sell", nullable = false)
    @NotNull
    private BigDecimal feeSell;

    @Column(name = "margin_rate")
    private BigDecimal marginRate;

    @Column(name = "num_min", nullable = false)
    @NotNull
    private BigDecimal numMin;

    @Column(name = "num_max", nullable = false)
    @NotNull
    private BigDecimal numMax;

    @Column(name = "trade_min", nullable = false)
    private BigDecimal tradeMin;

    @Column(name = "trade_max", nullable = false)
    private BigDecimal tradeMax;

    @Column(name = "price_scale")
    @NotNull
    private Integer priceScale;

    @Column(name = "num_scale", nullable = false)
    private Integer numScale;

    @Column(name = "contract_unit")
    private Integer contractUnit;

    @Column(name = "point_value")
    private BigDecimal pointValue;

    @Column(name = "merge_depth")
    @NotNull
    private String mergeDepth;

    @Column(name = "trade_time")
    @NotNull
    private String tradeTime;

    @Column(name = "trade_week")
    @NotNull
    private String tradeWeek;

    @Column(name = "sort", nullable = false)
    private Integer sort;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "fxcm_symbol")
    private String fxcmSymbol;

    @Column(name = "yahoo_symbol")
    private String yahooSymbol;

    @Column(name = "aliyun_symbol")
    private String aliyunSymbol;

    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime;

    @Column(name = "created", nullable = false)
    @CreatedDate
    private Date created;

}
