package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.SysUserLog;
import io.github.xpeteliu.repository.SysUserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SysUserLogService {

    @Autowired
    SysUserLogRepository sysUserLogRepository;

    public Page<SysUserLog> findAllWithPaging(PageRequest request) {
        return sysUserLogRepository.findAll(request);
    }

    public void saveLog(SysUserLog log){
        sysUserLogRepository.save(log);
    }
}
