package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;

import android.util.Log;

/**
 * 
 */
public class PARStockFunction extends JeffsGenericPriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	// PAR is the third least volatile
	public PARStockFunction(){
		this.volatilityMultiplier = 3;
	}
	
	@Override
	public String getName() {
		return "PAR";
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
		return 1000;
	}

	@Override
	int lowerBound() {
		return 10;
	}
	
	@Override
	int maxVolatility() {
		return 20;
	}


}