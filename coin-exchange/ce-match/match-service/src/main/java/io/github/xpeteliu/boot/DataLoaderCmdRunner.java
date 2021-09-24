package io.github.xpeteliu.boot;

import io.github.xpeteliu.disruptor.DisruptorTemplate;
import io.github.xpeteliu.entity.EntrustOrder;
import io.github.xpeteliu.model.Order;
import io.github.xpeteliu.repository.EntrustOrderRepository;
import io.github.xpeteliu.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class DataLoaderCmdRunner implements CommandLineRunner {

    @Autowired
    EntrustOrderRepository entrustOrderRepository;

    @Autowired
    DisruptorTemplate disruptorTemplate;

    @Override
    public void run(String... args) throws Exception {
        List<EntrustOrder> entrustOrders = entrustOrderRepository.findByStatus(0, Sort.by(Sort.Direction.ASC, "created"));
        if(CollectionUtils.isEmpty(entrustOrders)){
            return;
        }
        for (EntrustOrder entrustOrder : entrustOrders) {
            disruptorTemplate.publish(BeanUtils.entrustOrder2Order(entrustOrder));
        }
    }

}
