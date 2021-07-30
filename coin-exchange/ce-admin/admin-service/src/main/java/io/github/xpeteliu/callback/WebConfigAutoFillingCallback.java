package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.WebConfig;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//@Component
public class WebConfigAutoFillingCallback implements BeforeConvertCallback<WebConfig> {

    @Override
    public WebConfig onBeforeConvert(WebConfig webConfig) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (webConfig.getId() == null || webConfig.getId() == 0) {
            webConfig.setCreated(currentTime);
        }
        return webConfig;
    }
}
