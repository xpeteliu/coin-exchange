package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MergeDepthResult {

    private String mergeType;

    private BigDecimal value;
}
