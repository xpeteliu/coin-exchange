package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.CoinType;
import io.github.xpeteliu.repository.CoinTypeRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class CoinTypeService {

    @Autowired
    CoinTypeRepository coinTypeRepository;

    public Page<CoinType> findWithPaging(String code, Pageable pageable) {
        CoinType coinType = new CoinType();
        coinType.setCode(code);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("code", contains());
        return coinTypeRepository.findAll(Example.of(coinType, matcher), pageable);
    }

    public void save(CoinType coinType) {
        coinTypeRepository.save(coinType);
    }

    public void update(CoinType coinType) {
        CoinType target = coinTypeRepository.findById(coinType.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(coinType,target);
        coinTypeRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
        coinTypeRepository.updateStatusById(id, status);
    }

    public List<CoinType> findAll(int status) {
        return coinTypeRepository.findAllByStatus(status);
    }
}
