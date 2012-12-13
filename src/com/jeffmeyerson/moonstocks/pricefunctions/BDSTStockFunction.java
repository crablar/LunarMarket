package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * BDST is a high volatility company.
 * 
 */
public class BDSTStockFunction extends JeffsGenericPriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	public BDSTStockFunction(){
		this.volatilityMultiplier = 3;
	}
	
	@Override
	public String getName() {
		return "BDST";
	}
	
	@Override
	public int getValue(int time, List<Integer> values){
		return super.getValue(time, values) / 20;
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
		return 25;
	}

}