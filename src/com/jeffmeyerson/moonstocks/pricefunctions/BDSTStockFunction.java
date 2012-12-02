package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;

import android.util.Log;

/**
 * BDST is a high volatility company.
 * 
 */
public class BDSTStockFunction extends JeffsGenericPriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	// BDST is the most volatile
	public BDSTStockFunction(){
		this.volatilityMultiplier = 5;
	}
	
	@Override
	public String getName() {
		return "BDST";
	}
	
	@Override
	public int getPreviousValue() {
		if (previousValues.size() == 0)
			Log.d(this.toString(), "StockFunction undeveloped list error");
		return previousValues.get(previousValues.size() - 1);
	}

	@Override
	protected void addToPreviousValues(int result) {
		previousValues.add(result);
	}

	@Override
	int upperBound() {
		return 400;
	}

	@Override
	int maxVolatility() {
		return 10;
	}

}