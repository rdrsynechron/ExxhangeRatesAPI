package com.synechron.exchangeratesapi.dao;

import java.sql.Date;
import java.time.LocalDate;

import com.synechron.exchangeratesapi.dto.ExchangeRatesDataEntity;

public interface ExchangeRatesDAO {

	public int storeRatesinDatabase(LocalDate givenDate, String response);
	public ExchangeRatesDataEntity getRatesByGivenDate(Date givenDate);
}
