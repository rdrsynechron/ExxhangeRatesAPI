package com.synechron.exchangeratesapi.serviceImpl;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.synechron.exchangeratesapi.dao.ExchangeRatesDAO;
import com.synechron.exchangeratesapi.dto.ExchangeRatesDataEntity;
import com.synechron.exchangeratesapi.exception.ExchangeRatesApiException;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRatesServiceTest {
	
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	private ExchangeRatesServiceImpl exchangeRatesService;
	
	@Mock
	private RestTemplate restTemplate;

	@Mock
	private ExchangeRatesDAO exchangeRatesDao;
	
	@Test
	public void testLoadDataFromRatesAPI() {
		ReflectionTestUtils.setField(exchangeRatesService, "ratesAPIUrl", "hhtp://test");
		ResponseEntity<Object> response = new ResponseEntity<>(new Object(), HttpStatus.OK);
		Mockito.when(restTemplate.exchange(ArgumentMatchers.any(String.class), ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(HttpEntity.class), ArgumentMatchers.<Class<Object>>any())).thenReturn(response);
		Mockito.when(exchangeRatesDao.storeRatesinDatabase(Mockito.any(), Mockito.any())).thenReturn(1);
		this.exchangeRatesService.loadDataFromRatesAPI();
	}
	
	@Test
	public void testLoadDataFromRatesAPIException() {
		ResponseEntity<Object> response = new ResponseEntity<>(new Object(), HttpStatus.OK);
		Mockito.when(restTemplate.exchange(ArgumentMatchers.any(String.class), ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(HttpEntity.class), ArgumentMatchers.<Class<Object>>any())).thenReturn(response);
		Mockito.when(exchangeRatesDao.storeRatesinDatabase(Mockito.any(), Mockito.any())).thenThrow(new ExchangeRatesApiException("Exception thrown for testing", new Exception()));
		Assertions.assertThrows(ExchangeRatesApiException.class, () -> this.exchangeRatesService.loadDataFromRatesAPI());
	}
	
	@Test
	public void testGetExchangeRatesBygivenDate() {
		Mockito.when(exchangeRatesDao.getRatesByGivenDate(Mockito.any())).thenReturn(getExchangeRatesTestData());
		Assertions.assertNotNull(this.exchangeRatesService.getExchangeRatesBygivenDate("2020-01-01"));
	}
	
	@Test
	public void testGetExchangeRatesBygivenDateNullResponse() {
		ExchangeRatesDataEntity exchangeRatesDataEntity = getExchangeRatesTestData();
		exchangeRatesDataEntity.setExchangeRates(null);
		Mockito.when(exchangeRatesDao.getRatesByGivenDate(Mockito.any())).thenReturn(exchangeRatesDataEntity);
		Assertions.assertThrows(ExchangeRatesApiException.class, () -> this.exchangeRatesService.getExchangeRatesBygivenDate("2020-01-01"));
	}
	
	@Test
	public void testGetExchangeRatesBygivenDateException() {
		Mockito.when(exchangeRatesDao.getRatesByGivenDate(Mockito.any())).thenReturn(null);
		Assertions.assertThrows(ExchangeRatesApiException.class, () -> this.exchangeRatesService.getExchangeRatesBygivenDate(null));
	}
	
	private ExchangeRatesDataEntity getExchangeRatesTestData() {
		ExchangeRatesDataEntity exchangeRatesDataEntity = new ExchangeRatesDataEntity();
		exchangeRatesDataEntity.setExchangeRates("USD=1.1052");
		exchangeRatesDataEntity.setGivenDate(java.sql.Date.valueOf(LocalDate.now()));
		exchangeRatesDataEntity.setRatingId(1);
		exchangeRatesDataEntity.getGivenDate();
		return exchangeRatesDataEntity;
	}

}
