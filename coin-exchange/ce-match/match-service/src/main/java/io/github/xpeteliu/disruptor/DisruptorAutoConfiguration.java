package io.github.xpeteliu.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.openhft.affinity.AffinityThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;

@Configuration
@EnableConfigurationProperties(DisruptorProperties.class)
public class DisruptorAutoConfiguration {

    @Autowired
    DisruptorProperties disruptorProperties;

    @Bean
    public EventFactory<OrderEvent> eventFactory() {
        return OrderEvent::new;
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadFactory orderHandlingThreadFactory() {
        return new AffinityThreadFactory("Match-handler:", true);
    }

    @Bean
    @ConditionalOnMissingBean
    public WaitStrategy orderHandlingWaitStrategy() throws Exception {
        return (WaitStrategy) Class
                .forName(DisruptorProperties.STRATEGY_CLASS_NAME_PREFIX + disruptorProperties.getWaitStrategy())
                .getDeclaredConstructor().newInstance();
    }

    @Bean
    public RingBuffer<OrderEvent> orderEventRingBuffer(EventFactory<OrderEvent> eventFactory,
                                                       @Qualifier("orderHandlingThreadFactory") ThreadFactory threadFactory,
                                                       @Qualifier("orderHandlingWaitStrategy") WaitStrategy waitStrategy,
                                                       ExceptionHandler<OrderEvent> exceptionHandler,
                                                       EventHandler<OrderEvent>[] eventHandlers) {

        ProducerType producerType = disruptorProperties.isMultiProducer() ? ProducerType.MULTI : ProducerType.SINGLE;

        Disruptor<OrderEvent> disruptor = new Disruptor<>(eventFactory,
                disruptorProperties.getRingBufferSize(),
                threadFactory, producerType, waitStrategy);

        disruptor.setDefaultExceptionHandler(exceptionHandler);
        disruptor.handleEventsWith(eventHandlers);
        disruptor.start();

        return disruptor.getRingBuffer();
    }

    @Bean
    public DisruptorTemplate orderEventDisruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        return new DisruptorTemplate(ringBuffer);
    }
}
