package io.github.xpeteliu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "Result of cash trade")
public class CashTradeResult {

    @ApiModelProperty(value = "Bank account name of recipient")
    private String name ;

    @ApiModelProperty(value = "Bank of the recipient's bank card")
    private String bankName ;

    @ApiModelProperty(value = "Bank card number of recipient")
    private String bankCard ;

    @ApiModelProperty(value = "Amount of transferring")
    private BigDecimal amount ;

    @ApiModelProperty(value = "Remark")
    private String remark ;

    @ApiModelProperty(value = "Status")
    private Byte status ;
}
