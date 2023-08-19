package com.apd.coin.exchange.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class CoinExchangeResponse {

    @JsonProperty("Bill")
    private double bill;

    @JsonProperty("NumberOfCoins")
    private Map<Double,Integer> numberOfCoins;

    @JsonProperty("Message")
    private String message;

}
