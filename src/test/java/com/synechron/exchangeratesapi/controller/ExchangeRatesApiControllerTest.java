package com.synechron.exchangeratesapi.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.gson.Gson;
import com.synechron.exchangeratesapi.controller.ExchangeRatesApiController;
import com.synechron.exchangeratesapi.dto.ExchangeRatesDataEntity;
import com.synechron.exchangeratesapi.service.ExchangeRatesService;

/**
 * @author Dhanaraju
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRatesApiControllerTest {
	
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	private ExchangeRatesApiController exchangeRatesApiController;
	
	@Mock
	private ExchangeRatesService exchangeRatesService;
	
	@Test
	public void testLoadExchangeRatesData() {
		Mockito.doNothing().when(exchangeRatesService).loadDataFromRatesAPI();
		String response = this.exchangeRatesApiController.loadExchangeRatesData();
		Assertions.assertEquals("Data loaded successfully!", response);
	}
	
	@Test
	public void testGetExchangeRatesByGivenDate() {
		Mockito.when(exchangeRatesService.getExchangeRatesBygivenDate(Mockito.any())).thenReturn(getExchangeRatesTestData());
		Object response = this.exchangeRatesApiController.getExchangeRatesByGivenDate("2020-02-01");
		Assertions.assertNotNull(response);
	}
	
	private String getExchangeRatesTestData() {
		ExchangeRatesDataEntity exchangeRatesDataEntity = new ExchangeRatesDataEntity();
		exchangeRatesDataEntity.setExchangeRates("USD=1.1052");
		exchangeRatesDataEntity.setGivenDate(java.sql.Date.valueOf(LocalDate.now()));
		exchangeRatesDataEntity.setRatingId(1);
		return new Gson().toJson(exchangeRatesDataEntity);
	}
}
