package io.github.xpeteliu.model;

import lombok.Data;

import java.util.List;

@Data
public class TradeAreaMarketResult {

    private String areaName ;

    private List<TradeMarketResult> markets ;
}

