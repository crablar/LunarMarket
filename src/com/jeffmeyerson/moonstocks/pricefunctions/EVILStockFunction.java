package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Evil moves as a function of BDST (which makes sense if you read the news).
 * 
 */
public class EVILStockFunction extends PriceFunction {
	private static ArrayList<Integer> previousEVILPrices = new ArrayList<Integer>();
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
			previousEVILPrices.add(result);
		} else {
			result = values.get(time) * values.get(time - 1) * 10;
			result += rand.nextInt(UPPER_BOUND / 10);
			if (Math.abs(result - getPreviousValue()) > UPPER_BOUND / 10)
				result += UPPER_BOUND / 10;
			previousEVILPrices.add(result);
		}
		if (UPPER_BOUND < result)
			previousEVILPrices.add(UPPER_BOUND / 2);
		return result % UPPER_BOUND;
	}

	@Override
	int getPreviousValue() {
		if(previousEVILPrices.size() == 0)
			// Error
			return previousEVILPrices.get(0);
		return previousEVILPrices.get(previousEVILPrices.size() - 1);
	}

}