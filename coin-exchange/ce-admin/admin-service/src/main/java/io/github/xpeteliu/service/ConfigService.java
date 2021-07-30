package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.Config;
import io.github.xpeteliu.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    @Autowired
    ConfigRepository configRepository;

    public Page<Config> findWithPaging(String name, String type,String code, PageRequest request) {
        List<Config> resultList = configRepository.findByNameContainingAndTypeContainingAndCodeContaining(name, type,code, request);
        Long cnt = configRepository.countByNameContainingAndTypeContainingAndCodeContaining(name,code, type);
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveConfig(Config config) {
        configRepository.save(config);
    }

    public void deleteConfig(List<Long> ids) {
        configRepository.deleteAllById(ids);
    }
}
