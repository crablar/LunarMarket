package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple uptrend with an upper bound.
 * 
 */
public class WMCStockFunction extends PriceFunction {

	private static final int UPPER_BOUND = 599;
	private static ArrayList<Integer> previousWMCPrices = new ArrayList<Integer>();

	@Override
	public int getValue(int time, List<Integer> values) {
		Random rand = new Random();
		// for now, wrap around if time goes past the amount of song data we
		// have
		time = Math.abs(time) % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(time) * 10;
			previousWMCPrices.add(result);
		} else {
			result = values.get(time) * values.get(time - 1) * 10;
			result += rand.nextInt(UPPER_BOUND / 10);
			if (Math.abs(result - getPreviousValue()) > UPPER_BOUND / 10)
				result += UPPER_BOUND / 10;
			previousWMCPrices.add(result);
		}
		if (UPPER_BOUND < result)
			previousWMCPrices.add(UPPER_BOUND / 2);
		return result % UPPER_BOUND;
	}

	@Override
	int getPreviousValue() {
		if(previousWMCPrices.size() == 0)
			// Error
			return previousWMCPrices.get(0);
		return previousWMCPrices.get(previousWMCPrices.size() - 1);
	}
}