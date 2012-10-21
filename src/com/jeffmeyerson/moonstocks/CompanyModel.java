package com.jeffmeyerson.moonstocks;

/**
 * The CompanyModel class. A POJO for representing some basic data about a
 * company needed to form the StockActivity.
 * 
 * @author jeffreymeyerson
 * 
 */

public class CompanyModel {

	private String tickerName;
	private String companyName;

	public CompanyModel(String tickerName, String companyName) {
		this.tickerName = tickerName;
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getTickerName(){
		return tickerName;
	}
	
}
