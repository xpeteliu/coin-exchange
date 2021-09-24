package io.github.xpeteliu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepthItemResult {

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal volume = BigDecimal.ZERO;
}
