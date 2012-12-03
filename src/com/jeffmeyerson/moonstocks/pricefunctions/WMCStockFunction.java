package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * A simple uptrend with an upper bound.
 * 
 */
public class WMCStockFunction extends JeffsGenericPriceFunction {

	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();
	
	private static double growCRASH = .5;
	
	// WMC is the second most volatile
	public WMCStockFunction(){
		this.volatilityMultiplier = 3;
	}
	
	@Override
	public String getName() {
		return "WMC";
	}
	
	@Override
	public int getValue(int time, List<Integer> values) {
		int x = super.getValue(time, values);
		growCRASH %= 100;
		return (int)(x * x * growCRASH++);
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
		return 10;
	}

}