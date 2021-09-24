package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.UserAuthAuditRecord;
import io.github.xpeteliu.repository.UserAuthAuditRecordRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserAuthAuditRecordService {

    @Autowired
    UserAuthAuditRecordRepository userAuthAuditRecordRepository;

    public UserAuthAuditRecord findById(Long id) {
        return userAuthAuditRecordRepository.findById(id).orElse(null);
    }

    public Page<UserAuthAuditRecord> findWithPaging(Long userId, int page, int size) {
        List<UserAuthAuditRecord> resultList = userAuthAuditRecordRepository.findWithPaging(userId, page * size, size);
        Long cnt = userAuthAuditRecordRepository.countQualified(userId);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUserAuthAuditRecord(UserAuthAuditRecord userAuthAuditRecord) {
        userAuthAuditRecordRepository.save(userAuthAuditRecord);
    }

    public void updateUserAuthAuditRecord(UserAuthAuditRecord userAuthAuditRecord) {
        UserAuthAuditRecord target = userAuthAuditRecordRepository.findById(userAuthAuditRecord.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(userAuthAuditRecord, target);
        userAuthAuditRecordRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
        userAuthAuditRecordRepository.updateStatusById(id, status);
    }

    public List<UserAuthAuditRecord> findAllById(Long userId) {
        return userAuthAuditRecordRepository.findRecordsByUserId(userId, Sort.by(Sort.Direction.DESC, "created"));
    }
}
