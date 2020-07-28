package com.synechron.exchangeratesapi.exception;

public class ExchangeRatesApiException extends RuntimeException {

	private static final long serialVersionUID = -6969560460319633846L;
	
	private final String customMessage;

	public ExchangeRatesApiException(String customMessage, final Exception exception) {
		super(exception);
		this.customMessage = customMessage;
		
	}
	
	public Exception getException(){
		return (Exception) super.getCause();
	}

	public String getCustomMessage() {
		return customMessage;
	}	

}
