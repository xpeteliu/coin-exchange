package io.github.xpeteliu.stream;

import io.github.xpeteliu.controller.CronTaskController;
import io.github.xpeteliu.dto.KLineType;
import io.github.xpeteliu.model.KLine;
import io.github.xpeteliu.model.TradeRecord;
import io.github.xpeteliu.utils.KLineUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class ChannelConsumerConfig {

    @Bean
    public Consumer<List<TradeRecord>> refreshCandlestick() {
        return tradeRecords -> {
            for (TradeRecord tradeRecord : tradeRecords) {
                log.info("Received trade record: {}", tradeRecord);
                for (KLineType kLineType : KLineType.values()) {
                    LocalDateTime newTime = KLineUtils.getKLineTime(tradeRecord.getTime(), kLineType);
                    String key = String.format("market-kline-%s-%s", tradeRecord.getSymbol().toLowerCase(), kLineType.getValue());
                    KLine kLine = CronTaskController.candlestickMap.get(key);
                    if (kLine == null || newTime.compareTo(kLine.getTime()) > 0) {
                        CronTaskController.candlestickMap.put(key, new KLine(newTime, tradeRecord.getPrice(), tradeRecord.getAmount()));
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
            }
        };
    }
}
