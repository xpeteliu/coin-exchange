package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.WorkOrder;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//@Component
public class WorkOrderAutoFillingCallback implements BeforeConvertCallback<WorkOrder> {

    @Override
    public WorkOrder onBeforeConvert(WorkOrder workOrder) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        workOrder.setAnswerUserId(userId);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (workOrder.getId() == null || workOrder.getId() == 0) {
            workOrder.setCreated(currentTime);
        }
        workOrder.setLastUpdateTime(currentTime);
        return workOrder;
    }
}
