package com.synechron.exchangeratesapi.repository;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synechron.exchangeratesapi.dto.ExchangeRatesDataEntity;

@Transactional
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRatesDataEntity, Integer>{

	public ExchangeRatesDataEntity getExchangeRatesDataEntityByGivenDate(Date givenDate);
}
