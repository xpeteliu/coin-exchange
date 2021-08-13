package io.github.xpeteliu.service;

import io.github.xpeteliu.dto.CoinDto;
import io.github.xpeteliu.dto.TradeAreaDto;
import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.entity.TradeArea;
import io.github.xpeteliu.feign.CoinServiceFeignClient;
import io.github.xpeteliu.mapper.TradeAreaDtoMapper;
import io.github.xpeteliu.model.MergeDepthResult;
import io.github.xpeteliu.model.TradeAreaMarketResult;
import io.github.xpeteliu.model.TradeMarketResult;
import io.github.xpeteliu.repository.TradeAreaRepository;
import io.github.xpeteliu.utils.SupplementaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeAreaService {

    @Autowired
    TradeAreaRepository tradeAreaRepository;

    @Autowired
    MarketService marketService;

    @Autowired
    CoinServiceFeignClient coinServiceFeignClient;

    @Autowired
    UserFavoriteMarketService userFavoriteMarketService;

    public Page<TradeArea> findWithPaging(String name, Integer status, Pageable pageable) {
        TradeArea tradeArea = new TradeArea();
        tradeArea.setName(name);
        tradeArea.setStatus(status);
        return tradeAreaRepository.findAll(Example.of(tradeArea), pageable);
    }

    public void save(TradeArea tradeArea) {
        tradeAreaRepository.save(tradeArea);
    }

    public void update(TradeArea tradeArea) {
        TradeArea target = findById(tradeArea.getId());
        if (target == null) {
            throw new IllegalArgumentException("Non-existent target tradeArea");
        }
        SupplementaryUtils.copyBeanIgnoringNullProps(tradeArea, target);
        tradeAreaRepository.save(target);
    }

    private TradeArea findById(Long id) {
        return tradeAreaRepository.findById(id).orElse(null);
    }

    public List<TradeArea> findByStatus(Integer status) {
        return tradeAreaRepository.findByStatus(status);
    }

    public List<TradeAreaMarketResult> findAllTradeAreaWithMarket() {
        List<TradeAreaMarketResult> resultList = new ArrayList<>();
        List<TradeArea> tradeAreas = tradeAreaRepository.findAll();
        for (TradeArea tradeArea : tradeAreas) {
            List<Market> markets = marketService.findByTradeAreaId(tradeArea.getId());
            TradeAreaMarketResult tradeAreaMarketResult = new TradeAreaMarketResult();
            tradeAreaMarketResult.setAreaName(tradeArea.getName());
            tradeAreaMarketResult.setMarkets(convertToMarketResult(markets));
            resultList.add(tradeAreaMarketResult);
        }
        return resultList;
    }

    private List<TradeMarketResult> convertToMarketResult(List<Market> markets) {
        return markets.stream().map(market -> {
            TradeMarketResult tradeMarketResult = new TradeMarketResult();
            tradeMarketResult.setImage(market.getImg());
            tradeMarketResult.setName(market.getName());
            tradeMarketResult.setSymbol(market.getSymbol());


            // TODO: set prices
            tradeMarketResult.setHigh(market.getOpenPrice());
            tradeMarketResult.setLow(market.getOpenPrice());
            tradeMarketResult.setPrice(market.getOpenPrice());
            tradeMarketResult.setCnyPrice(market.getOpenPrice());
            tradeMarketResult.setCnyPrice(market.getOpenPrice());
            tradeMarketResult.setPriceScale(market.getPriceScale());


            @NotNull Long buyCoinId = market.getBuyCoinId();
            Map<Long, CoinDto> coins = coinServiceFeignClient.findCoins(Arrays.asList(buyCoinId));

            if (CollectionUtils.isEmpty(coins) || coins.size() > 1) {
                throw new IllegalArgumentException("Invalid coin ID");
            }
            CoinDto coinDto = coins.values().iterator().next();
            tradeMarketResult.setPriceUnit(coinDto.getName());


            tradeMarketResult.setTradeMin(market.getTradeMin());
            tradeMarketResult.setTradeMax(market.getTradeMax());

            tradeMarketResult.setNumMin(market.getNumMin());
            tradeMarketResult.setNumMax(market.getNumMax());

            tradeMarketResult.setSellFeeRate(market.getFeeSell());
            tradeMarketResult.setBuyFeeRate(market.getFeeBuy());

            tradeMarketResult.setNumScale(market.getNumScale());


            tradeMarketResult.setSort(market.getSort());

            tradeMarketResult.setVolume(BigDecimal.ZERO);
            tradeMarketResult.setAmount(BigDecimal.ZERO);


            tradeMarketResult.setChange(0.00);

            tradeMarketResult.setMergeDepth(convertMergeDepths(market.getMergeDepth()));

            return tradeMarketResult;
        }).collect(Collectors.toList());
    }

    private List<MergeDepthResult> convertMergeDepths(String mergeDepth) {

        String[] split = mergeDepth.split(",");
        if (split.length != 3) {
            throw new IllegalArgumentException("Invalid merge depth");
        }

        MergeDepthResult minMergeDepthResult = new MergeDepthResult();
        minMergeDepthResult.setMergeType("MIN");
        minMergeDepthResult.setValue(convertDepthValue(Integer.valueOf(split[0])));


        MergeDepthResult defaultMergeDepthResult = new MergeDepthResult();
        defaultMergeDepthResult.setMergeType("DEFAULT");
        defaultMergeDepthResult.setValue(convertDepthValue(Integer.valueOf(split[1])));

        MergeDepthResult maxMergeDepthResult = new MergeDepthResult();
        maxMergeDepthResult.setMergeType("MAX");
        maxMergeDepthResult.setValue(convertDepthValue(Integer.valueOf(split[2])));

        List<MergeDepthResult> mergeDepthResults = new ArrayList<>();
        mergeDepthResults.add(minMergeDepthResult);
        mergeDepthResults.add(defaultMergeDepthResult);
        mergeDepthResults.add(maxMergeDepthResult);

        return mergeDepthResults;
    }

    private BigDecimal convertDepthValue(Integer scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(Math.pow(10, scale));
        return BigDecimal.ONE.divide(bigDecimal).setScale(scale, RoundingMode.HALF_UP);
    }

    public List<TradeAreaMarketResult> findUserFavoriteMarket() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<Long> marketIds = userFavoriteMarketService.findMarketIdByUserId(userId);
        List<Market> favoriteMarkets = marketService.findAllById(marketIds);
        TradeAreaMarketResult result = new TradeAreaMarketResult();
        result.setAreaName("My Favorite");
        result.setMarkets(convertToMarketResult(favoriteMarkets));
        return Collections.singletonList(result);
    }

    public List<TradeAreaDto> findAllTradeAreasWithMarkets() {
        List<TradeArea> tradeAreas = findByStatus(1);
        List<TradeAreaDto> tradeAreaDtos = TradeAreaDtoMapper.INSTANCE.entity2Dto(tradeAreas);
        tradeAreaDtos.forEach(tradeAreaDto -> {
            List<Market> markets = marketService.findByTradeAreaId(tradeAreaDto.getId());
            String marketIds = markets.stream().map(market -> market.getId().toString()).collect(Collectors.joining(","));
            tradeAreaDto.setMarketIds(marketIds);
        });
        return tradeAreaDtos;
    }
}
