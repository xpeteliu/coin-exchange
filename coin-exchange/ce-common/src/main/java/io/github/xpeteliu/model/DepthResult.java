package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
public class DepthResult {

    private List<DepthItemResult> bids = Collections.emptyList();

    private List<DepthItemResult> asks = Collections.emptyList();

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal cnyPrice = BigDecimal.ZERO;

}
