package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.Notice;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//@Component
public class NoticeAutoFillingCallback implements BeforeConvertCallback<Notice> {

    @Override
    public Notice onBeforeConvert(Notice notice) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (notice.getId() == null || notice.getId() == 0) {
            notice.setCreated(currentTime);
        }
        notice.setLastUpdateTime(currentTime);
        return notice;
    }
}
