package com.mkyong.web.model;

import java.util.Date;

public class SearchCriteria {

	String customer;
	String ccyPair;
	String type;
	String direction;
	Date tradeDate;
	// for US style
	Date excerciseStartDate, expiryDate, premium;
	Long amount1;
	Long amount2;
	Double rate;
	Date valueDate;
	String legalEntity;
	String trader;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCcyPair() {
		return ccyPair;
	}

	public void setCcyPair(String ccyPair) {
		this.ccyPair = ccyPair;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Date getExcerciseStartDate() {
		return excerciseStartDate;
	}

	public void setExcerciseStartDate(Date excerciseStartDate) {
		this.excerciseStartDate = excerciseStartDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getPremium() {
		return premium;
	}

	public void setPremium(Date premium) {
		this.premium = premium;
	}

	public Long getAmount1() {
		return amount1;
	}

	public void setAmount1(Long amount1) {
		this.amount1 = amount1;
	}

	public Long getAmount2() {
		return amount2;
	}

	public void setAmount2(Long amount2) {
		this.amount2 = amount2;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	@Override
	public String toString() {
		return "SearchCriteria [customer=" + customer + ", ccyPair=" + ccyPair + "]";
	}

}
