package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EntrustedOrderParam {

    private String symbol;

    private BigDecimal price;

    private BigDecimal volume;

    private Integer type;

}
