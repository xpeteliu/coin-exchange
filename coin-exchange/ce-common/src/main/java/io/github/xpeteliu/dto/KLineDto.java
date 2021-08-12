package io.github.xpeteliu.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KLineDto {

    /**
     * 交易对名称
     */
    private String symbol;

    /**
     * 交易的价格
     */
    private BigDecimal price;

    /**
     * 交易的数量
     */
    private BigDecimal volume;

}

