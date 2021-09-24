package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.CashRecharge;
import io.github.xpeteliu.entity.CashRechargeAuditRecord;
import io.github.xpeteliu.model.CashParam;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.CashRechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cashRecharge")
@Api(tags = "Controller for GCN recharge")
public class CashRechargeController {


    @Autowired
    private CashRechargeService cashRechargeService;

    @GetMapping("/records")
    @ApiOperation(value = "Paged query on recharge records")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page number"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page"),
            @ApiImplicitParam(name = "coinId", value = "Id of coin type"),
            @ApiImplicitParam(name = "userId", value = "Id of user"),
            @ApiImplicitParam(name = "userName", value = "username"),
            @ApiImplicitParam(name = "mobile", value = "mobile number of user"),
            @ApiImplicitParam(name = "status", value = "status of recharge"),
            @ApiImplicitParam(name = "numMin", value = "Minimum recharge amount"),
            @ApiImplicitParam(name = "numMax", value = "Maximum recharge amount"),
            @ApiImplicitParam(name = "startTime", value = "Lower bound of search interval"),
            @ApiImplicitParam(name = "endTime", value = "Upper bound of search interval"),

    })
    public R<PagedResult<CashRecharge>> findWithPaging(
            Long coinId, Long userId, String userName,
            String mobile, Integer status, String numMin, String numMax,
            String startTime, String endTime, int current, int size
    ) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        PagedResult<CashRecharge> cashRechargePagedResult = new PagedResult<>(cashRechargeService.findWithPaging(
                coinId, userId, userName, mobile, status, numMin, numMax, startTime, endTime, request));
        return R.success(cashRechargePagedResult);
    }

    @PostMapping("/cashRechargeUpdateStatus")
    @ApiOperation(value = "Update the auditing status on a recharge")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cashRechargeAuditRecord", value = "Audit record for cash recharge")
    })
    public R cashRechargeUpdateStatus(@RequestBody CashRechargeAuditRecord cashRechargeAuditRecord) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        try {
            cashRechargeService.cashRechargeUpdateStatus(userId, cashRechargeAuditRecord);
            return R.success("Cash recharge status updated");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
    }

    @GetMapping("/user/records")
    @ApiOperation(value = "Paged query on recharge records")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page number"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page"),
            @ApiImplicitParam(name = "status", value = "status of recharge")
    })
    public R<PagedResult<CashRecharge>> findUserRechargeWithPaging(Integer status, int current, int size) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.DESC, "lastUpdateTime");
        PagedResult<CashRecharge> cashRechargePagedResult = new PagedResult<>(cashRechargeService.findUserRecordWithPaging(userId, status, request));
        return R.success(cashRechargePagedResult);
    }

    @PostMapping("/buy")
    @ApiOperation(value = "Recharge of GCNs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cashParam", value = "Parameters on the recharge")
    })
    public R buy(@RequestBody @Validated CashParam cashParam){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return R.success(cashRechargeService.buy(userId, cashParam));
    }
}

