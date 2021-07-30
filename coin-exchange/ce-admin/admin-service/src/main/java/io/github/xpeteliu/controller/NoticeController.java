package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.Notice;
import io.github.xpeteliu.model.PagedResult;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.NoticeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping
    @PreAuthorize("hasAuthority('notice_query')")
    @ApiOperation("Find notices with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "Index of current page"),
            @ApiImplicitParam(name = "size", value = "Number of notices on each page"),
            @ApiImplicitParam(name = "title", value = "Title of notices to be searched"),
            @ApiImplicitParam(name = "startTime", value = "Lower bound of notice creation time"),
            @ApiImplicitParam(name = "endTime", value = "Upper bound of notice creation time"),
            @ApiImplicitParam(name = "status", value = "Current status of notice"),
    })
    public R<PagedResult<Notice>> findWithPaging(int current, int size,
                                                 @RequestParam(defaultValue = "") String title,
                                                 @RequestParam(defaultValue = "1990-01-01") String startTime,
                                                 @RequestParam(defaultValue = "9999-12-31") String endTime,
                                                 @RequestParam(name = "status", defaultValue = "0") Integer statusMin,
                                                 @RequestParam(name = "status", defaultValue = "1") Integer statusMax) {
        PageRequest request = PageRequest.of(current - 1, size, Sort.Direction.ASC, "sort");
        Page<Notice> page = noticeService.findWithPaging(title, startTime, endTime, statusMin, statusMax, request);
        PagedResult<Notice> pagedResult = new PagedResult<>(page);
        return R.success(pagedResult);
    }

    @PostMapping("/delete")
    @ApiOperation("Delete a notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "IDs of notices to be deleted")
    })
    @PreAuthorize("hasAuthority('notice_delete')")
    public R delete(@RequestBody List<Long> ids) {
        try {
            noticeService.deleteNotice(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }

        return R.success("Notice successfully deleted");
    }

    @PostMapping("/updateStatus")
    @ApiOperation("Update the status of a notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID of the notice whose status is to be modified"),
            @ApiImplicitParam(name = "status", value = "Target status")
    })
    @PreAuthorize("hasAuthority('notice_update')")
    public R updateStatus(Long id, Integer status) {
        try {
            noticeService.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Notice status updated successfully");
    }

    @PostMapping
    @ApiOperation("Create a notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notice", value = "JSON object representing notice")
    })
    @PreAuthorize("hasAuthority('notice_create')")
    public R create(@RequestBody @Validated Notice notice) {
        try {
            if (notice.getStatus() == null) {
                notice.setStatus(1);
            }
            noticeService.saveNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Notice successfully created");
    }

    @PatchMapping
    @ApiOperation("Update a notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notice", value = "JSON object representing notice")
    })
    @PreAuthorize("hasAuthority('notice_update')")
    public R update(@RequestBody @Validated Notice notice) {
        try {
            noticeService.saveNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(e.getMessage());
        }
        return R.success("Notice successfully updated");
    }
}
