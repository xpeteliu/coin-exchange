package io.github.xpeteliu.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MergeDepthDTO {

    /**
     * 合并类型
     */
    private String mergeType;

    /**
     * 合并精度
     */
    private BigDecimal value;
}

