package com.synechron.exchangeratesapi.serviceImpl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synechron.exchangeratesapi.dao.ExchangeRatesDAO;
import com.synechron.exchangeratesapi.exception.ExchangeRatesApiException;
import com.synechron.exchangeratesapi.exception.ExchangeRatesNotFoundException;
import com.synechron.exchangeratesapi.service.ExchangeRatesService;
import com.synechron.exchangeratesapi.util.ExchangeRatesApiConstants;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${rates.api.url}")
	private String ratesAPIUrl;

	@Autowired
	private ExchangeRatesDAO exchangeRatesDao;

	@CircuitBreaker(name = "exchangeRates")
	@Retry(name="exchangeRates", fallbackMethod = "loadDataFallback")
	@Override
	public void loadDataFromRatesAPI() {

		LOGGER.info("Loading Data from Rates API -- START");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.add(ExchangeRatesApiConstants.USER_AGENT_HEADER, ExchangeRatesApiConstants.USER_AGENT_VALUE);
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			IntStream.range(0, 12).forEach(number -> {
				LocalDate givenDate = YearMonth.now().minusMonths(number).atDay(1);
				LOGGER.info("Querying rates API for the date: {}", givenDate);
				ResponseEntity<Object> response = restTemplate.exchange(
						ratesAPIUrl + givenDate + ExchangeRatesApiConstants.SYMBOLS, HttpMethod.GET, entity,
						Object.class);
				exchangeRatesDao.storeRatesinDatabase(givenDate, response.getBody().toString());
			});

			LOGGER.info("Loading Data from Rates API -- END");

		} catch (RuntimeException exception) {
			LOGGER.error("Exception occured while loading data from rates API: {}", exception.getMessage());
			throw new ExchangeRatesApiException("Exception occured while loading data from rates API", exception);
		}

	}
	
	public void loadDataFallback(CallNotPermittedException exception) {
		LOGGER.info("LoadData fallback method: Due to unavailability of RatesApi");
		throw new ExchangeRatesApiException("Rates API Service is Not Available right now! Please try after sometime!!", exception);
	}

	@CircuitBreaker(name="exchangeRates")
	@Retry(name="exchangeRates", fallbackMethod="getExchangeRatesBygivenDateFallback")
	@Override
	public String getExchangeRatesBygivenDate(String givenDate) {
		LocalDate date;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String exchangeRates = "";
		try {
			date = LocalDate.parse(givenDate);
			exchangeRates = exchangeRatesDao
					.getRatesByGivenDate(java.sql.Date.valueOf(date.withDayOfMonth(1))).getExchangeRates();
			if (null == exchangeRates || exchangeRates.isEmpty()) {
				LOGGER.error("No Exchange Rates found for the given date: {}", givenDate);
				throw new ExchangeRatesNotFoundException("No Exchange Rates found for the given date: " + givenDate,
						null);
			}
		} catch (RuntimeException exception) {
			LOGGER.error("Exception occured while getting data from rates API for he givenDate: {} - {}", givenDate,
					exception.getMessage());
			throw new ExchangeRatesApiException("Exception occured while loading data from rates API", exception);
		}
		return exchangeRates;
	}
	
	public String getExchangeRatesBygivenDateFallback(String givenDate, CallNotPermittedException exception) {
		LOGGER.info("Get ExchangeRates By GivenDate fallback method: Due to unavailability of RatesApi");
		throw new ExchangeRatesApiException("Rates API Service is Not Available right now! Please try after sometime!!", exception);
	}

}
