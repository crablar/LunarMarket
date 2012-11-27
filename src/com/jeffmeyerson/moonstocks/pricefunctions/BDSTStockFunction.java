package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

/**
 * A function representing a company whose ambitions and greed rival those of
 * EVIL.
 * 
 */
public class BDSTStockFunction extends PriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

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

}