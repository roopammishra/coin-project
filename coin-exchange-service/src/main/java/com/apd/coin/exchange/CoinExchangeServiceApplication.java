package com.apd.coin.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CoinExchangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinExchangeServiceApplication.class, args);
	}

}
