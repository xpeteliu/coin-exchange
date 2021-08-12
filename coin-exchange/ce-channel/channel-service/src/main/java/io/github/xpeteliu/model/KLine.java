package io.github.xpeteliu.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
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

    public KLine() {
    }

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
    public String toKline() {
        // 时间，开，高，低，收，量
        JSONArray array = new JSONArray();
        array.add(time.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        array.add(open);
        array.add(high);
        array.add(low);
        array.add(close);
        array.add(volume);
        return array.toJSONString();
    }
}

