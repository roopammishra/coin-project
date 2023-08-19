package com.apd.coin.exchange.controller;

import com.apd.coin.exchange.Entity.CoinExchangeRequest;
import com.apd.coin.exchange.Entity.CoinExchangeResponse;
import com.apd.coin.exchange.Util.CoinRepository;
import com.apd.coin.exchange.service.CoinExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class CoinExchangeController {

    @Autowired
    private CoinExchangeService coinExchangeService;

    private final CoinRepository coinRepository;

    @Autowired
    public CoinExchangeController(CoinRepository coinRepository,CoinExchangeService coinExchangeService) {
        this.coinRepository = coinRepository;
        this.coinExchangeService = coinExchangeService;
    }

    //exhangeCoin is responsible for providing the change for a given bill.
    @PostMapping("/exchangedCoins")
    public ResponseEntity<CoinExchangeResponse> exchangeCoin(@RequestBody CoinExchangeRequest request){
        if(request.getBill() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try{
                CoinExchangeResponse coinExchangeResponse = coinExchangeService.requestCoins(request);
                return new ResponseEntity<>(coinExchangeResponse, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
    //getCoinDetails will provide the current state of coins.
    @GetMapping(value = "/getCoinDetails")
    public ResponseEntity<?> getCoinDetails(){
        try{
            Map<Double, Integer> coinResponse = coinExchangeService.getCoinDetails();
            return new ResponseEntity<>(coinResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
