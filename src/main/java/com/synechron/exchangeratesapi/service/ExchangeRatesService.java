package com.synechron.exchangeratesapi.service;

public interface ExchangeRatesService {
	
	public void loadDataFromRatesAPI();
	
	public String getExchangeRatesBygivenDate(String givenDate);
	
}
