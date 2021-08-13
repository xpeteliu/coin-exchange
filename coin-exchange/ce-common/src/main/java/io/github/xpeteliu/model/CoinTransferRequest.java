package io.github.xpeteliu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinTransferRequest {

    Long buyUserId;

    Long sellUserId;

    Long buyCoinId;

    Long sellCoinId;

    BigDecimal buyAmount;

    BigDecimal sellAmount;

    Long buyOrderId;

    Long sellOrderId;

    String businessType;

}
