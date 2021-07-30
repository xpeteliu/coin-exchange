package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.User;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class UserAutoFillingCallback implements BeforeConvertCallback<User> {

    @Override
    public User onBeforeConvert(User user) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (user.getId() == null || user.getId() == 0) {
            user.setCreated(currentTime);
        }
        return user;
    }
}