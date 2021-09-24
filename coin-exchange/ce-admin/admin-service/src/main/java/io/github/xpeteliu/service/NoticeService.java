package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.Notice;
import io.github.xpeteliu.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    public void deleteNotice(List<Long> ids) {
        noticeRepository.deleteAllById(ids);
    }

    public Page<Notice> findWithPaging(String title, String startTimeStr, String endTimeStr, Integer statusMin, Integer statusMax, PageRequest request) {
        Timestamp startTime = Timestamp.valueOf(startTimeStr + " 00:00:00");
        Timestamp endTime = Timestamp.valueOf(endTimeStr + " 23:59:59");
        List<Notice> resultList = noticeRepository.findByTitleContainingAndCreatedBetweenAndStatusBetween(title, startTime, endTime, statusMin, statusMax, request);
        Long cnt = noticeRepository.countByTitleContainingAndCreatedBetweenAndStatusBetween(title, startTime, endTime, statusMin, statusMax);
        return new PageImpl<>(resultList, request, cnt);
    }

    public void updateStatus(Long id, Integer status) {
        noticeRepository.updateStatusById(id, status);
    }

    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }

}
