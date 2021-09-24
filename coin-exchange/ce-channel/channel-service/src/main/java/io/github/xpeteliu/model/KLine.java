package io.github.xpeteliu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
@NoArgsConstructor
public class KLine {

    /**
     * 时间
     */
    private
    LocalDateTime time;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 收盘价
     */
    private BigDecimal close;

    /**
     * 总交易量
     */
    private BigDecimal volume;


    /**
     * 通过价格构造
     *
     * @param time   时间
     * @param price  成交价
     * @param volume 成交量
     */
    public KLine(LocalDateTime time, BigDecimal price, BigDecimal volume) {
        this.time = time;
        this.open = price;
        this.high = price;
        this.low = price;
        this.close = price;
        this.volume = volume;
    }

    /**
     * 格式化称kline
     *
     * @return
     */
    public String toCompactString() {
        List outputList = toOutputList();
        StringBuilder stringBuilder = new StringBuilder(outputList.get(0).toString());
        for (int i = 1; i <= 5; i++) {
            stringBuilder.append(",").append(outputList.get(i));
        }
        return stringBuilder.toString();
    }

    public List<BigDecimal> toOutputList() {
        return List.of(new BigDecimal(time.toInstant(ZoneOffset.of("+8")).toEpochMilli()),
                open, high, low, close, volume);
    }
}

