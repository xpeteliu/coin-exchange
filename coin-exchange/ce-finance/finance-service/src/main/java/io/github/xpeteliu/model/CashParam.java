package io.github.xpeteliu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel("Input object for recharge / withdrawal request")
public class CashParam {

    @ApiModelProperty("ID of coin")
    @NotNull
    private Long coinId;

    @ApiModelProperty("Number of coins")
    @NotNull
    @Min(100)
    private BigDecimal num;

    @ApiModelProperty("Monetary value of coins")
    @NotNull
    private BigDecimal mum;
}
