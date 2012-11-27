package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

/**
 * 
 */
public class PARStockFunction extends PriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	@Override
	public int getValue(int time, List<Integer> values) {
		Random r = new Random();
		// wrap around if time goes past the amount of song data we have
		time = time % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(0);
		} else {
			result = values.get(time);
			int difference = result - getPreviousValue();
			if (Math.abs(difference) > MAX_VOLATILITY)
				result = difference < 0 ? getPreviousValue() - randomVolatility()
						: getPreviousValue() + randomVolatility();
		}
		if (UPPER_BOUND < result)
			result = UPPER_BOUND - randomVolatility();
		if (result < 0)
			result = getPreviousValue() + randomVolatility();
		previousValues.add(result);
		return result;
	}

	@Override
	public int getPreviousValue() {
		if (previousValues.size() == 0)
			Log.d(this.toString(), "StockFunction undeveloped list error");
		return previousValues.get(previousValues.size() - 1);
	}

	@Override
	public String getName() {
		return "PAR";
	}

}