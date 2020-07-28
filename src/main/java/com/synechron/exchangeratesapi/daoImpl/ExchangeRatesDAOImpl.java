package com.synechron.exchangeratesapi.daoImpl;

import java.sql.Date;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synechron.exchangeratesapi.dao.ExchangeRatesDAO;
import com.synechron.exchangeratesapi.dto.ExchangeRatesDataEntity;
import com.synechron.exchangeratesapi.repository.ExchangeRatesRepository;

@Component
public class ExchangeRatesDAOImpl implements ExchangeRatesDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesDAO.class);

	@Autowired
	private ExchangeRatesRepository exchangeRatesRepository;

	@Override
	public int storeRatesinDatabase(LocalDate givenDate, String response) {
		LOGGER.info("Storing rates API response for the date: {} in Database", givenDate);
		ExchangeRatesDataEntity exchangeRatesDataEntity = new ExchangeRatesDataEntity();
		exchangeRatesDataEntity.setExchangeRates(response);
		exchangeRatesDataEntity.setGivenDate(java.sql.Date.valueOf(givenDate));
		exchangeRatesRepository.save(exchangeRatesDataEntity);
		return exchangeRatesDataEntity.getRatingId();
	}

	@Override
	public ExchangeRatesDataEntity getRatesByGivenDate(Date givenDate) {
		LOGGER.info("Getting Exchange rates for the date: {}", givenDate);
		return exchangeRatesRepository.getExchangeRatesDataEntityByGivenDate(givenDate);
	}

}
