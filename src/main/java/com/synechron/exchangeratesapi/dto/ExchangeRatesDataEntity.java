package com.synechron.exchangeratesapi.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Exchange_Rates")
public class ExchangeRatesDataEntity {
	
	@Id
    @GeneratedValue
    @Column(name="rating_id")
    private Integer ratingId;

	@Lob
	@Column(name="exchange_rates")
	private String exchangeRates;
	
	
	@Column(name="given_date")
	private Date givenDate;


	public Integer getRatingId() {
		return ratingId;
	}


	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}


	public String getExchangeRates() {
		return exchangeRates;
	}


	public void setExchangeRates(String exchangeRates) {
		this.exchangeRates = exchangeRates;
	}


	public Date getGivenDate() {
		return givenDate;
	}


	public void setGivenDate(Date givenDate) {
		this.givenDate = givenDate;
	}


	@Override
	public String toString() {
		return "ExchangeRatesDataEntity [ratingId=" + ratingId + ", exchangeRates=" + exchangeRates + ", givenDate="
				+ givenDate + "]";
	}
	
	
}
