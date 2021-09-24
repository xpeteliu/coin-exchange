package io.github.xpeteliu.controller;

import io.github.xpeteliu.dto.AdminBankDto;
import io.github.xpeteliu.entity.AdminBank;
import io.github.xpeteliu.feign.AdminBankServiceFeign;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.AdminBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminBanks")
@Api("Bank Card Management")
public class AdminBankController implements AdminBankServiceFeign {

    @Autowired
    AdminBankService adminBankService;

    @GetMapping
    @ApiOperation("Paged query on bank cards")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Current page"),
            @ApiImplicitParam(name = "size", value = "Number of items on every page"),
            @ApiImplicitParam(name = "bankCard", value = "Bank card number to search for")
    })
    @PreAuthorize("hasAuthority('admin_bank_query')")
    public R<PagedResult<AdminBank>> findWithPaging(int current, int size,
                                                  @RequestParam(defaultValue = "") String bankCard) {
        PageRequest request = PageRequest.of(current - 1, size);
        Page<AdminBank> page = adminBankService.findByBankCardWithPaging(bankCard, request);
        PagedResult<AdminBank> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PostMapping
    @ApiOperation("Add a bank card")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank", value = "JSON object representing bank card")
    })
    @PreAuthorize("hasAuthority('admin_bank_create')")
    public R create(@RequestBody @Validated AdminBank adminBank) {
        try {
            adminBankService.saveBankCard(adminBank);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Bank card successfully added");
    }

    @PatchMapping
    @ApiOperation("Update a bank card")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank", value = "JSON object representing bank card")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R update(@RequestBody @Validated AdminBank adminBank) {
        try {
            adminBankService.saveBankCard(adminBank);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Bank card successfully updated");
    }

    @PostMapping("/adminUpdateBankStatus")
    @ApiOperation("Update the status of a bank card")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the adminBank whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R updateStatus(@RequestParam("bankId") Long id, Integer status) {
        try {
            adminBankService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("AdminBank status updated successfully");
    }

    @Override
    @GetMapping("/list")
    public List<AdminBankDto> findAllAdminBanks() {
        return adminBankService.findAllAvailable();
    }
}
