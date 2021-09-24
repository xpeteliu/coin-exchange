package io.github.xpeteliu.service;

import io.github.xpeteliu.mapper.CoinDtoMapper;
import io.github.xpeteliu.dto.CoinDto;
import io.github.xpeteliu.entity.Coin;
import io.github.xpeteliu.repository.CoinRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoinService {

    @Autowired
    CoinRepository coinRepository;

    public Page<Coin> findWithPaging(Coin coin, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return coinRepository.findAll(Example.of(coin, matcher), pageable);
    }

    public void save(Coin coin) {
        coinRepository.save(coin);
    }

    public void update(Coin coin) {
        Coin target = coinRepository.findById(coin.getId()).get();
        SupplementaryUtils.copyBeanIgnoringNullProps(coin, target);
        coinRepository.save(target);
    }

    public void updateStatus(Long id, Integer status) {
        coinRepository.updateStatusById(id, status);
    }

    public List<Coin> findAll(int status) {
        return coinRepository.findAllByStatus(status);
    }

    public Coin findByName(String coinName) {
        return coinRepository.findByName(coinName);
    }

    public Coin findById(Long coinId) {
        return coinRepository.findById(coinId).orElse(null);
    }

    public Map<Long, CoinDto> findAllById(List<Long> ids) {
        List<CoinDto> coins = CoinDtoMapper.INSTANCE.entity2Dto(coinRepository.findAllById(ids));
        return coins.stream().collect(Collectors.toMap(CoinDto::getId, coin -> coin));
    }
}
