package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;
import java.util.Random;

/**
 * @author jeffreymeyerson
 * 
 *         The Function abstract class.
 */
public abstract class PriceFunction {

	protected static final int UPPER_BOUND = 599;
	protected int MAX_VOLATILITY = 50;
	
	protected abstract String getName();
	protected abstract int getPreviousValue();
	protected abstract void addToPreviousValues(int result);
	
	public int randomVolatility(){
		Random r = new Random();
		return r.nextInt(MAX_VOLATILITY);
	}
	
	public int getValue(int time, List<Integer> values) {
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
		addToPreviousValues(result);
		return result;
	}

}
