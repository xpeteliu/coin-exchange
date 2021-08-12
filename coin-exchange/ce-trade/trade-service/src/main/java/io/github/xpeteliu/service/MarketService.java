package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.CoinDto;
import io.github.xpeteliu.dto.MarketDto;
import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.feign.CoinServiceFeignClient;
import io.github.xpeteliu.feign.OrderBookFeignClient;
import io.github.xpeteliu.mapper.MarketDtoMapper;
import io.github.xpeteliu.model.DepthItemResult;
import io.github.xpeteliu.model.DepthResult;
import io.github.xpeteliu.repository.MarketRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    CoinServiceFeignClient coinServiceFeignClient;

    @Autowired
    OrderBookFeignClient orderBookFeignClient;

    public Page<Market> findWithPaging(Long tradeAreaId, Integer status, Pageable pageable) {
        Market market = new Market();
        market.setTradeAreaId(tradeAreaId);
        market.setStatus(status);
        return marketRepository.findAll(Example.of(market), pageable);
    }

    public List<Market> findByTradeAreaId(Long tradeAreaId) {
        return marketRepository.findByTradeAreaIdAndStatus(tradeAreaId, 1);
    }

    public void saveSubmittedMarket(Market market) {
        Map<Long, CoinDto> coins = coinServiceFeignClient.findCoins(List.of(market.getSellCoinId(), market.getBuyCoinId()));
        if (coins == null || !coins.containsKey(market.getSellCoinId()) || !coins.containsKey(market.getBuyCoinId())) {
            throw new IllegalArgumentException("Invalid buyCoinId or sellCoinId");
        }
        CoinDto sellCoin = coins.get(market.getSellCoinId());
        CoinDto buyCoin = coins.get(market.getBuyCoinId());
        market.setName(buyCoin.getName() + "/" + sellCoin.getName());
        market.setTitle(buyCoin.getTitle() + "/" + sellCoin.getTitle());
        market.setSymbol(buyCoin.getName() + sellCoin.getName());
        market.setImg(sellCoin.getImg());
        marketRepository.save(market);
    }

    public void update(Market market) {
        Market target = findById(market.getId());
        if (target == null) {
            throw new IllegalArgumentException("Non-existent target market");
        }
        SupplementaryUtils.copyBeanIgnoringNullProps(market, target);
        marketRepository.save(target);
    }

    public Market findById(Long id) {
        return marketRepository.findById(id).orElse(null);
    }

    public List<Market> findByStatus(Integer status) {
        Market market = new Market();
        market.setStatus(status);
        return marketRepository.findAll(Example.of(market));
    }

    public List<Market> findAllById(List<Long> marketIds) {
        return marketRepository.findAllById(marketIds);
    }

    public List<Market> findByExample(Example<Market> example) {
        return marketRepository.findAll(example);
    }

    public DepthResult findDepthInfo(String symbol, String depth) {
        Market market = marketRepository.findBySymbol(symbol);
        if (market == null) {
            throw new IllegalArgumentException("Invalid market symbol");
        }

        Map<String, List<DepthItemResult>> marketDepth = orderBookFeignClient.findMarketDepth(symbol, null);
        DepthResult result = new DepthResult();
        result.setCnyPrice(market.getOpenPrice());
        result.setPrice(market.getOpenPrice());
        result.setAsks(marketDepth.get("asks"));
        result.setBids(marketDepth.get("bids"));
        return result;
    }

    public MarketDto findBySymbol(String symbol) {
        return MarketDtoMapper.INSTANCE.entity2Dto(marketRepository.findBySymbol(symbol));
    }

    public List<MarketDto> findAllMarkets() {
        return MarketDtoMapper.INSTANCE.entity2Dto(marketRepository.findAll());
    }
}
