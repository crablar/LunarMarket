package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

/**
 * A simple uptrend with an upper bound.
 * 
 */
public class WMCStockFunction extends PriceFunction {

	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	@Override
	public String getName() {
		return "WMC";
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
		return 300;
	}

	@Override
	int maxVolatility() {
		return 10;
	}

}