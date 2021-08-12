package io.github.xpeteliu.disruptor;

import io.github.xpeteliu.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
public class OrderEvent implements Serializable {

    private final Instant timestamp;

    protected transient Order source;

    public OrderEvent() {
        this.timestamp = Instant.now();
    }

    public OrderEvent(Instant timestamp) {
        this.timestamp = timestamp;
    }

}
