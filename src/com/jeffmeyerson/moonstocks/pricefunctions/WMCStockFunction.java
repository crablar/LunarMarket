package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;
import java.util.Random;

/**
 * A simple uptrend with an upper bound.
 * 
 */
public class WMCStockFunction implements PriceFunction {

	private static final int UPPER_BOUND = 599;
	private static int prevResult;
	Random rand = new Random();

	@Override
	public int getValue(int time, List<Integer> values) {
		Random rand = new Random();
		// for now, wrap around if time goes past the amount of song data we
		// have
		time = Math.abs(time) % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(time) * 10;
			prevResult = result;
		} else {
			result = values.get(time) * values.get(time - 1) * 10;
			result += rand.nextInt(UPPER_BOUND / 10);
			if (Math.abs(result - prevResult) > UPPER_BOUND / 10)
				result += UPPER_BOUND / 10;
			prevResult = result;
		}
		if (UPPER_BOUND < result)
			prevResult = UPPER_BOUND / 2;
		return result % UPPER_BOUND;
	}
}