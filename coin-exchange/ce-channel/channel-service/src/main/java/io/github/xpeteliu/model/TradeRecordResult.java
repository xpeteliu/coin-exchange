package io.github.xpeteliu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRecordResult {

    private BigDecimal price;

    private BigDecimal volume;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, HH:mm:ss")
    private LocalDateTime time;

    public static TradeRecordResult fromTradeRecord(TradeRecord tr) {
        return new TradeRecordResult(tr.getPrice(), tr.getAmount(), tr.getTime());
    }
}
