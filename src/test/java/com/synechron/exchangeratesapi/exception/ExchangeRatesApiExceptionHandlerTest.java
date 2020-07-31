package com.synechron.exchangeratesapi.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRatesApiExceptionHandlerTest {
	
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	private ExchangeRatesApiExceptionHandler exceptionHandler;
	
	@Test
	public void testhandleExchangeRatesApiException() {
		Assertions.assertNotNull(this.exceptionHandler.handleExchangeRatesApiException(new ExchangeRatesApiException("testing", new Exception())));
	}

	@Test
	public void testHandleAnyOtherExceptions() {
		testData();
		Assertions.assertNotNull(this.exceptionHandler.handleAnyOtherExceptions(new Exception("testing")));
		
	}
	
	private void testData() {
		ExchangeRatesApiException exchangeRatesApiException = new ExchangeRatesApiException("testing", new Exception());
		exchangeRatesApiException.getCustomMessage();
		exchangeRatesApiException.getException();
		ExchangeRatesNotFoundException exchangeRatesNotFoundException = new ExchangeRatesNotFoundException("testing", new Exception());
		exchangeRatesNotFoundException.getCustomMessage();
		exchangeRatesNotFoundException.getException();
	}

}
