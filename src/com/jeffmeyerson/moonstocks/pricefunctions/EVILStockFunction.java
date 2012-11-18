package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

/**
 * Evil moves as a function of BDST (which makes sense if you read the news).
 * 
 */
public class EVILStockFunction extends PriceFunction {
	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();

	@Override
	public int getValue(int time, List<Integer> values) {
		Random r = new Random();
		// wrap around if time goes past the amount of song data we have
		time = Math.abs(time) % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(time) * 10;
		} else {
			result = values.get(time) * values.get(time - 1) * 10;
			int difference = Math.abs(result - getPreviousValue());
			if (difference > r.nextInt(MAX_VOLATILITY))
				result = r.nextBoolean() ? getPreviousValue() - 10
						: getPreviousValue() + 10;
		}
		if (UPPER_BOUND < result)
			result = UPPER_BOUND;
		if (result < 0)
			result = getPreviousValue() + MAX_VOLATILITY;
		previousValues.add(result);
		return result % UPPER_BOUND;
	}

	@Override
	public int getPreviousValue() {
		if (previousValues.size() == 0)
			Log.d(this.toString(), "StockFunction undeveloped list error");
		return previousValues.get(previousValues.size() - 1);
	}

	@Override
	public String getName() {
		return "EVIL";
	}

}