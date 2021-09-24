package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TradeMarketResult {

    private String symbol;

    private String name;

    private String image;

    private BigDecimal buyFeeRate;

    private BigDecimal sellFeeRate;

    private int priceScale;

    private int numScale;

    private BigDecimal numMin;

    private BigDecimal numMax;

    private BigDecimal tradeMin;

    private BigDecimal tradeMax;

    private BigDecimal price;

    private String priceUnit;

    private BigDecimal cnyPrice;

    private BigDecimal coinCnyPrice;

    private List<MergeDepthResult> mergeDepth;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal volume = BigDecimal.ZERO;

    private BigDecimal amount = BigDecimal.ZERO;

    private double change;

    private int sort;
}
