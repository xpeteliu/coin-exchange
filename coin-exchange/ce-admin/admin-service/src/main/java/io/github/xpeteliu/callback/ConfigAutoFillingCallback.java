package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.Config;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//@Component
public class ConfigAutoFillingCallback implements BeforeConvertCallback<Config> {

    @Override
    public Config onBeforeConvert(Config config) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (config.getId() == null || config.getId() == 0) {
            config.setCreated(currentTime);
        }
        return config;
    }
}
