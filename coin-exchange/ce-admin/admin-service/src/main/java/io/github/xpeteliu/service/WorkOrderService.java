package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.UserDto;
import io.github.xpeteliu.entity.WorkOrder;
import io.github.xpeteliu.feign.UserServiceFeign;
import io.github.xpeteliu.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {

    @Autowired
    WorkOrderRepository workOrderRepository;

    @Autowired
    UserServiceFeign userServiceFeign;

    public Page<WorkOrder> findWithPaging(String startTimeStr, String endTimeStr, Integer statusMin, Integer statusMax, PageRequest request) {
        Timestamp startTime = Timestamp.valueOf(startTimeStr + " 00:00:00");
        Timestamp endTime = Timestamp.valueOf(endTimeStr + " 23:59:59");
        List<WorkOrder> resultList = workOrderRepository.findByCreatedBetweenAndStatusBetween(startTime, endTime, statusMin, statusMax, request);
        Long cnt = workOrderRepository.countByCreatedBetweenAndStatusBetween(startTime, endTime, statusMin, statusMax);

        List<Long> ids = resultList.stream().map(WorkOrder::getUserId).collect(Collectors.toList());
        Map<Long, UserDto> id2UserBasics = userServiceFeign.getUserBasics(ids, null, null);
        resultList.forEach(workOrder -> {
            UserDto userDto = id2UserBasics.get(workOrder.getUserId());
            if (userDto != null) {
                workOrder.setUsername(userDto.getUsername());
                workOrder.setRealName(userDto.getRealName());
            }
        });

        return new PageImpl<>(resultList, request, cnt);
    }

    public void replyById(Long id, String answer) {
        workOrderRepository.replyById(id, answer);
    }

    public void saveWorkOrder(WorkOrder workOrder) {
        workOrderRepository.save(workOrder);
    }
}


