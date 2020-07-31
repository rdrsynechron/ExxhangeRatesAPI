package com.synechron.exchangeratesapi.dao;

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
import com.synechron.exchangeratesapi.daoImpl.ExchangeRatesDAOImpl;
import com.synechron.exchangeratesapi.dto.ExchangeRatesDataEntity;
import com.synechron.exchangeratesapi.repository.ExchangeRatesRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRateDAOTest {
	
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	private ExchangeRatesDAOImpl exchangeRatesDAO;
	
	@Mock
	private ExchangeRatesRepository exchangeRatesRepository;
	
	@Test
	public void testStoreRatesinDatabase() {
		Mockito.when(exchangeRatesRepository.save(Mockito.any())).thenReturn(getExchangeRatesTestData());
		Assertions.assertEquals(1, this.exchangeRatesDAO.storeRatesinDatabase(LocalDate.now(), "response"));
	}
	
	@Test
	public void testGetRatesByGivenDate() {
		Mockito.when(exchangeRatesRepository.getExchangeRatesDataEntityByGivenDate(Mockito.any())).thenReturn(getExchangeRatesTestData());
		Assertions.assertNotNull(this.exchangeRatesDAO.getRatesByGivenDate(java.sql.Date.valueOf(LocalDate.now())));
	}
	
	private ExchangeRatesDataEntity getExchangeRatesTestData() {
		ExchangeRatesDataEntity exchangeRatesDataEntity = new ExchangeRatesDataEntity();
		exchangeRatesDataEntity.setExchangeRates("USD=1.1052");
		exchangeRatesDataEntity.setGivenDate(java.sql.Date.valueOf(LocalDate.now()));
		exchangeRatesDataEntity.setRatingId(1);
		exchangeRatesDataEntity.toString();
		return exchangeRatesDataEntity;
	}
	
	

}
