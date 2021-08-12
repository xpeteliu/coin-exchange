package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.Market;
import io.github.xpeteliu.entity.UserFavoriteMarket;
import io.github.xpeteliu.repository.UserFavoriteMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFavoriteMarketService {

    @Autowired
    UserFavoriteMarketRepository userFavoriteMarketRepository;

    @Autowired
    MarketService marketService;

    public List<Long> findMarketIdByUserId(Long userId) {
        List<Long> marketIds = userFavoriteMarketRepository
                .findByUserId(userId)
                .stream()
                .map(UserFavoriteMarket::getMarketId)
                .collect(Collectors.toList());
        return marketIds;
    }

    public void add(Market exampleMarket) {
        List<Market> markets = marketService.findByExample(Example.of(exampleMarket));
        if (markets == null || markets.size() != 1) {
            throw new IllegalArgumentException("Unable to locate the specific market in database");
        }
        Market market = markets.get(0);

        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserFavoriteMarket userFavoriteMarket = new UserFavoriteMarket();
        userFavoriteMarket.setUserId(userId);
        userFavoriteMarket.setMarketId(market.getId());
        userFavoriteMarket.setType(market.getType());

        userFavoriteMarketRepository.save(userFavoriteMarket);
    }

    public void delete(String symbol) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Market exampleMarket = new Market();
        exampleMarket.setSymbol(symbol);
        List<Market> markets = marketService.findByExample(Example.of(exampleMarket));
        if (markets == null || markets.size() != 1) {
            throw new IllegalArgumentException("Unable to locate the specific market in database");
        }
        userFavoriteMarketRepository.deleteByUserIdAndMarketId(userId,markets.get(0).getId());
    }
}
