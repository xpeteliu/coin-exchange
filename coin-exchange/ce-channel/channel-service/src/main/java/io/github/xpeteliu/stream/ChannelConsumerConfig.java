package io.github.xpeteliu.stream;

import io.github.xpeteliu.dto.KLineType;
import io.github.xpeteliu.model.KLine;
import io.github.xpeteliu.model.TradeRecord;
import io.github.xpeteliu.model.TradeRecordResult;
import io.github.xpeteliu.service.CronTaskService;
import io.github.xpeteliu.utils.SupplementaryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class ChannelConsumerConfig {

    @Autowired
    CronTaskService cronTaskService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean
    public Consumer<List<TradeRecord>> refreshCandlestick() {
        return tradeRecords -> {
            for (TradeRecord tradeRecord : tradeRecords) {
                log.info("Received trade record: {}", tradeRecord);
                String symbol = tradeRecord.getSymbol().toLowerCase();

                for (KLineType kLineType : KLineType.values()) {
                    LocalDateTime newTime = SupplementaryUtils.getKLineTime(tradeRecord.getTime(), kLineType);
                    String key = String.format("market-kline-%s-%s", symbol, kLineType.getValue());
                    KLine kLine = cronTaskService.candlestickMap.get(key);
                    if (kLine == null || newTime.compareTo(kLine.getTime()) > 0) {
                        if (kLine != null) {
                            redisTemplate.opsForList().rightPush(key, kLine.toCompactString());
                        }
                        cronTaskService.candlestickMap.put(key,
                                new KLine(newTime, tradeRecord.getPrice(), tradeRecord.getAmount()));
                    } else {
                        if (newTime.compareTo(kLine.getTime()) == 0) {
                            BigDecimal dealPrice = tradeRecord.getPrice();
                            kLine.setClose(dealPrice);
                            if (dealPrice.compareTo(kLine.getHigh()) > 0) {
                                kLine.setHigh(dealPrice);
                            }
                            if (dealPrice.compareTo(kLine.getLow()) < 0) {
                                kLine.setLow(dealPrice);
                            }
                            kLine.setVolume(kLine.getVolume().add(tradeRecord.getAmount()));
                        }
                    }
                }

                cronTaskService.tradeRecordResultsMap.computeIfAbsent(symbol, k -> new LinkedList<>())
                        .addFirst(TradeRecordResult.fromTradeRecord(tradeRecord));
            }
        };
    }
}
