package com.apd.coin.exchange.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoinExchangeRequest {

    @JsonProperty("Bill")
    private double bill;

    @JsonProperty("MaximumCoins")
    private boolean maxCoins;
}
