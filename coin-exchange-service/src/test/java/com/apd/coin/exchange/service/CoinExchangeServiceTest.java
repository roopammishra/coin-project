package com.apd.coin.exchange.service;

import com.apd.coin.exchange.Entity.CoinExchangeRequest;
import com.apd.coin.exchange.Entity.CoinExchangeResponse;
import com.apd.coin.exchange.Util.CoinRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static com.apd.coin.exchange.Util.Constant.FAILURE;
import static com.apd.coin.exchange.Util.Constant.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CoinExchangeServiceTest {

    @InjectMocks
    CoinExchangeService coinExchangeService;

    @Mock
    CoinRepository coinRepository;

    @Before
    public void setUp() {
        coinExchangeService = new CoinExchangeService(coinRepository);
    }
    @Test
    public void testRequestCoinsWithSuccessCoins() {
        CoinExchangeRequest request = new CoinExchangeRequest();
        request.setBill(2.0);
        when(coinRepository.getCoinState()).thenReturn(mockCoinRepository());

        CoinExchangeResponse response = coinExchangeService.requestCoins(request);

        assertEquals(SUCCESS, response.getMessage());
    }

    @Test
    public void testRequestCoinsWithInsufficientCoins() {

        when(coinRepository.getCoinState()).thenReturn(mockCoinRepository());
        CoinExchangeRequest request = new CoinExchangeRequest();
        request.setBill(500);

        CoinExchangeResponse response = coinExchangeService.requestCoins(request);

        assertEquals(FAILURE, response.getMessage());
    }

    @Test
    public void testRequestCoinsWithMaxCoinTrue() {

        when(coinRepository.getCoinState()).thenReturn(mockCoinRepository());
        CoinExchangeRequest request = new CoinExchangeRequest();
        request.setBill(1);
        request.setMaxCoins(true);

        CoinExchangeResponse response = new CoinExchangeResponse();
        response.setBill(1);
        Map<Double,Integer> numberOfCoins = new HashMap<>();
        numberOfCoins.put(0.01,100);
        response.setNumberOfCoins(numberOfCoins);

        CoinExchangeResponse result = coinExchangeService.requestCoins(request);

        assertEquals(response.getNumberOfCoins(),result.getNumberOfCoins());
        assertEquals(SUCCESS, result.getMessage());
    }

    @Test
    public void testGetCoinDetails() {

        when(coinRepository.getCoinState()).thenReturn(mockCoinRepository());
        Map<Double, Integer> coinDetails = coinExchangeService.getCoinDetails();
        assertEquals(mockCoinRepository(), coinDetails);
    }

    private Map<Double, Integer> mockCoinRepository(){

        Map<Double, Integer> coinState = new HashMap<>();
        coinState.put(0.25, 100);
        coinState.put(0.10, 100);
        coinState.put(0.05, 100);
        coinState.put(0.02, 100);
        coinState.put(0.01, 100);

        return coinState;
    }
}
