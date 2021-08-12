package io.github.xpeteliu.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinDto {

    private Long id;

    private String name;

    private String title;

    private String img;

    private BigDecimal baseAmount;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;
}
