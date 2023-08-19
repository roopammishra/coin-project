package com.apd.coin.exchange.service;

import com.apd.coin.exchange.Entity.CoinExchangeRequest;
import com.apd.coin.exchange.Entity.CoinExchangeResponse;
import com.apd.coin.exchange.Util.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.apd.coin.exchange.Util.Constant.*;

@Service
public class CoinExchangeService {
    private CoinRepository coinRepository;
    public CoinExchangeService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    //This service will calculate the change.
    public CoinExchangeResponse requestCoins(CoinExchangeRequest request) {

        double bill = request.getBill();
        boolean maxCoin = request.isMaxCoins();

        Map<Double,Integer> change =  new HashMap<>();
        int balance = (int) (bill * NUM);
        List<Double> coinList;

        //To check if most coins is requested.
        if(!maxCoin) {
            coinList = new ArrayList<>(coinRepository.getCoinState().keySet()).stream().sorted(Comparator.reverseOrder()).toList();
        }else {
            coinList = new ArrayList<>(coinRepository.getCoinState().keySet()).stream().sorted().toList();
        }

        //build the change list.
        for (double coin : coinList) {
            int count = 0;
            while (balance >= (int)(coin * NUM) &&
                    coinRepository.getCoinState().get(coin) > 0) {
                change.put(coin,++count);
                balance -= coin * NUM;
                coinRepository.getCoinState().put(coin, coinRepository.getCoinState().get(coin) - 1);
            }
        }

        //Set response to send.
        CoinExchangeResponse coinExchangeResponse = new CoinExchangeResponse();
        coinExchangeResponse.setBill(bill);
        if (balance == 0) {
            coinExchangeResponse.setNumberOfCoins(change);
            coinExchangeResponse.setMessage(SUCCESS);
        } else {
            coinExchangeResponse.setMessage(FAILURE);
        }

        return coinExchangeResponse;
    }

    //This service is requesting the current balance of coins.
    public Map<Double, Integer> getCoinDetails() {
        Map<Double, Integer> coinDetails = coinRepository.getCoinState();
        return coinDetails;
    }
}
