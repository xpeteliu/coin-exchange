package io.github.xpeteliu.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import io.github.xpeteliu.model.Order;

/**
 * 在boot里面使用它发送消息
 */
public class DisruptorTemplate {

    private static final EventTranslatorOneArg<OrderEvent, Order> TRANSLATOR = (event, sequence, input) -> event.setSource(input);

    private final RingBuffer<OrderEvent> ringBuffer;

    public DisruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(Order input) {
        ringBuffer.publishEvent(TRANSLATOR, input);
    }
}
