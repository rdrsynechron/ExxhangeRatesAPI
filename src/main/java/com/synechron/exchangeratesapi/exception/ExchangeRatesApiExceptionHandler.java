package com.synechron.exchangeratesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExchangeRatesApiExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(value = ExchangeRatesApiException.class)
	public ResponseEntity handleExchangeRatesApiException(ExchangeRatesApiException exchangeRatesApiException) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exchangeRatesApiException.getCustomMessage());	
	}
	
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	private ResponseEntity handleAnyOtherExceptions(Exception exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

}
