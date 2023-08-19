package com.apd.coin.exchange.controller;

import com.apd.coin.exchange.Entity.CoinExchangeRequest;
import com.apd.coin.exchange.Entity.CoinExchangeResponse;
import com.apd.coin.exchange.Util.CoinRepository;
import com.apd.coin.exchange.service.CoinExchangeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CoinExchangeControllerTest {

    @InjectMocks
    CoinExchangeController coinExchangeController;

    @Mock
    CoinExchangeService coinExchangeService;

    @Mock
    CoinRepository coinRepository;

    @Before
    public void setup() {

        coinExchangeController = new CoinExchangeController(coinRepository,coinExchangeService);
    }

    @Test
    public void getCoinExchangeTestSuccessMessage(){
        CoinExchangeRequest request = new CoinExchangeRequest();
        request.setBill(5);

        CoinExchangeResponse response = new CoinExchangeResponse();
        response.setBill(5.0);
        Map<Double,Integer> numberOfCoins = new HashMap<>();
        numberOfCoins.put(0.25,20);
        response.setNumberOfCoins(numberOfCoins);
        response.setMessage("SUCCESS");

        Mockito.when(coinExchangeService.requestCoins(request)).thenReturn(response);

        ResponseEntity<CoinExchangeResponse> result = coinExchangeController.exchangeCoin(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void getCoinExchangeTestFailureMessage(){
        CoinExchangeRequest request = new CoinExchangeRequest();
        request.setBill(5);

        CoinExchangeResponse response = new CoinExchangeResponse();
        response.setBill(5.0);
        Map<Double,Integer> numberOfCoins = new HashMap<>();

        response.setNumberOfCoins(numberOfCoins);
        response.setMessage("Cannot provide exact change!");

        when(coinExchangeService.requestCoins(request)).thenReturn(response);

        ResponseEntity<CoinExchangeResponse> result = coinExchangeController.exchangeCoin(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void getCoinExchangeTestNegative(){
        CoinExchangeRequest request = new CoinExchangeRequest();
        request.setBill(-5);

        ResponseEntity<CoinExchangeResponse> result = coinExchangeController.exchangeCoin(request);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }


    @Test
    public void getCoinDetailsTest(){
        when(coinExchangeService.getCoinDetails()).thenReturn(mockCoinRepository());
        ResponseEntity<?> details = coinExchangeController.getCoinDetails();
        assertEquals(HttpStatus.OK, details.getStatusCode());
        assertEquals(mockCoinRepository(), details.getBody());
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
