package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.WorkOrder;
import io.github.xpeteliu.entity.WorkOrder;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workIssues")
@Api(tags="Controller for handling work orders")
public class WorkOrderController {

    @Autowired
    WorkOrderService workOrderService;

    @GetMapping
    @PreAuthorize("hasAuthority('work_issue_query')")
    @ApiOperation("Find workOrders with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Index of current page"),
            @ApiImplicitParam(name = "size", value = "Number of work orders on each page"),
            @ApiImplicitParam(name = "title", value = "Title of work orders to be searched"),
            @ApiImplicitParam(name = "startTime", value = "Lower bound of work order creation time"),
            @ApiImplicitParam(name = "endTime", value = "Upper bound of work order creation time"),
            @ApiImplicitParam(name = "status", value = "Current status of work order"),
    })
    public R<PagedResult<WorkOrder>> findWithPaging(int current, int size,
                                                 @RequestParam(defaultValue = "1990-01-01") String startTime,
                                                 @RequestParam(defaultValue = "9999-12-31") String endTime,
                                                 @RequestParam(name = "status", defaultValue = "0") Integer statusMin,
                                                 @RequestParam(name = "status", defaultValue = "2") Integer statusMax) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.ASC, "lastUpdateTime");
        Page<WorkOrder> page = workOrderService.findWithPaging(startTime, endTime, statusMin, statusMax, request);
        PagedResult<WorkOrder> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PatchMapping
    @ApiOperation("Update the status of a workOrder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the work order whose status is to be modified"),
            @ApiImplicitParam(name = "answer", value = "reply to the work order")
    })
    @PreAuthorize("hasAuthority('work_issue_update')")
    public R replyToWorkOrder(Long id, String answer) {
        try {
            workOrderService.replyById(id, answer);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("WorkOrder status updated successfully");
    }

}
