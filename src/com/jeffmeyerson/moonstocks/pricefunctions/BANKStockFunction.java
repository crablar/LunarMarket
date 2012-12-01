package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

/**
 * BDST is a high volatility company.
 * 
 */
public class BANKStockFunction extends PriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	// Bank is least volatile
	public BANKStockFunction(){
		this.volatilityMultiplier = 1;
	}
	
	@Override
	public String getName() {
		return "BANK";
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