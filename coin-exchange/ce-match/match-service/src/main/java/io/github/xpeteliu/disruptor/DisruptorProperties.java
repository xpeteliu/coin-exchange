package io.github.xpeteliu.disruptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.disruptor")
public class DisruptorProperties {

    public static final String STRATEGY_CLASS_NAME_PREFIX = "com.lmax.disruptor.";

    private int ringBufferSize = 1024 * 1024;

    private boolean isMultiProducer = false;

    private String waitStrategy = "YieldingWaitStrategy";
}
