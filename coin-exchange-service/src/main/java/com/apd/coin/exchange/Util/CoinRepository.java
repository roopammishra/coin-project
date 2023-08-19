package com.apd.coin.exchange.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CoinRepository {

    @Value("${coin.quantity}")
    private int initialCoinCount;

    //Initial set of coins
    @Cacheable("coinStateCache")
    public Map<Double, Integer> getCoinState() {

        Map<Double, Integer> coinState = new HashMap<>();
        coinState.put(0.25, initialCoinCount);
        coinState.put(0.10, initialCoinCount);
        coinState.put(0.05, initialCoinCount);
        coinState.put(0.02, initialCoinCount);
        coinState.put(0.01, initialCoinCount);
        return coinState;
    }
}
