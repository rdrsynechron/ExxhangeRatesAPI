package com.synechron.exchangeratesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.exchangeratesapi.service.ExchangeRatesService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ExchangeRatesApiController {
	
	@Autowired
	private ExchangeRatesService exchangeRatesService;

	@GetMapping("/loadExchangeRates")
	@ApiOperation(value =  "This endpoint will load the Exchange Rates from Rates API for the last One year and store it in the Database and only GBP/USD/HKD currencies are included")
	public String loadExchangeRatesData(){
		exchangeRatesService.loadDataFromRatesAPI();
		return "Data loaded successfully!";
	}
	
	@GetMapping("/getExchangeRates")
	@ApiOperation(value =  "This endpoint will give the Exchange Rates from the Database for the given date.")
	public Object getExchangeRatesByGivenDate(@RequestParam String givenDate){
		return exchangeRatesService.getExchangeRatesBygivenDate(givenDate);
		
	}
	
	
}
