package io.github.xpeteliu.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EntrustOrderResult {

        private Long orderId;

        private Integer type;

        private BigDecimal price;

        private BigDecimal dealAvgPrice;

        private BigDecimal volume;

        private BigDecimal dealVolume;

        private BigDecimal amount;

        private BigDecimal dealAmount;

        private Integer status;

        private Date created;
    }

