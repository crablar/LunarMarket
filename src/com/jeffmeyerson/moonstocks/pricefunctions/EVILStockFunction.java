package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;

import android.util.Log;

/**
 * Evil moves as a function of BDST (which makes sense if you read the news).
 * 
 */
public class EVILStockFunction extends JeffsGenericPriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	// EVIL is the second least volatile
	public EVILStockFunction(){
		this.volatilityMultiplier = 1;
	}
	
	@Override
	public String getName() {
		return "EVIL";
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
		return 50;
	}



}