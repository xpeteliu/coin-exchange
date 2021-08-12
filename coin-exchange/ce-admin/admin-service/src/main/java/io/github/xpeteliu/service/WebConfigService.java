package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.Notice;
import io.github.xpeteliu.entity.WebConfig;
import io.github.xpeteliu.entity.WebConfig;
import io.github.xpeteliu.repository.WebConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebConfigService {

    @Autowired
    WebConfigRepository webConfigRepository;

    public Page<WebConfig> findWithPaging(String name, String type, PageRequest request) {
        List<WebConfig> resultList = webConfigRepository.findByNameContainingAndTypeContaining(name, type, request);
        Long cnt = webConfigRepository.countByNameContainingAndTypeContaining(name, type);
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveWebConfig(WebConfig webConfig) {
        webConfigRepository.save(webConfig);
    }

    public void deleteWebConfig(List<Long> ids) {
        webConfigRepository.deleteAllById(ids);
    }

    public List<WebConfig> findBanners() {
        return webConfigRepository.findByTypeAndStatus("WEB_BANNER",1,Sort.by(Sort.Direction.ASC,"sort"));
    }
}
