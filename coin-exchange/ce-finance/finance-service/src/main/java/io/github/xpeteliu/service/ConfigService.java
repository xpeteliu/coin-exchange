package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.Config;
import io.github.xpeteliu.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Autowired
    ConfigRepository configRepository;

    public Config findByCode(String code) {
        return configRepository.findByCode(code);
    }
}
