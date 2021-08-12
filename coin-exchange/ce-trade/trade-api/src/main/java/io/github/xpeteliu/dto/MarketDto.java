package io.github.xpeteliu.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class MarketDto {

    private Long id;

    private Byte type;

    @NotNull
    private Long tradeAreaId;

    @NotBlank
    private Long sellCoinId;

    @NotNull
    private Long buyCoinId;

    @NotNull
    private String symbol;

    private String name;

    private String title;

    private String img;

    @NotNull
    private BigDecimal openPrice;

    @NotNull
    private BigDecimal feeBuy;

    @NotNull
    private BigDecimal feeSell;

    private BigDecimal marginRate;

    @NotNull
    private BigDecimal numMin;

    @NotNull
    private BigDecimal numMax;

    private BigDecimal tradeMin;

    private BigDecimal tradeMax;

    @NotNull
    private Byte priceScale;

    private Byte numScale;

    private Integer contractUnit;

    private BigDecimal pointValue;

    @NotNull
    private String mergeDepth;

    @NotNull
    private String tradeTime;

    @NotNull
    private String tradeWeek;

    private Integer sort;

    private Integer status;

}
