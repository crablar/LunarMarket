package com.jeffmeyerson.moonstocks.pricefunctions;

public class PriceFunctionFactory {

	public static PriceFunction getPriceFunctionForStock(String stockName){
		if(stockName.equals("WMC"))
			return new WMCStockFunction();
		if(stockName.equals("EVIL"))
			return new EVILStockFunction();
		if(stockName.equals("BDST"))
			return new BDSTStockFunction();
		if(stockName.equals("PAR"))
			return new PARStockFunction();
		if(stockName.equals("BANK"))
			return new BANKStockFunction();
		// Error
		return null;
	}
	
}
