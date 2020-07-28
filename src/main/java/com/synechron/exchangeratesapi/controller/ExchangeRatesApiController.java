package com.synechron.exchangeratesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.exchangeratesapi.service.ExchangeRatesService;

@RestController
public class ExchangeRatesApiController {
	
	@Autowired
	private ExchangeRatesService exchangeRatesService;

	@GetMapping("/loadExchangeRates")
	public String loadExchangeRatesData(){
		exchangeRatesService.loadDataFromRatesAPI();
		return "Data loaded successfully!";
	}
	
	@GetMapping("/getExchangeRates")
	public Object getExchangeRatesByGivenDate(@RequestParam String givenDate){
		return exchangeRatesService.getExchangeRatesBygivenDate(givenDate);
		
	}
	
	
}
