package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SymbolAssetResult {

    private BigDecimal buyAmount;

    private String buyUnit;

    private BigDecimal buyLockAmount;

    private BigDecimal buyFeeRate;

    private BigDecimal sellAmount;

    private String sellUnit;

    private BigDecimal sellLockAmount;

    private BigDecimal sellFeeRate;

}

