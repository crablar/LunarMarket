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
	protected static int MAX_VOLATILITY = 10;
	
	protected abstract String getName();
	protected abstract int getPreviousValue();
	protected abstract void addToPreviousValues(int result);
	
	public static int randomVolatility(){
		Random r = new Random();
		return r.nextInt(MAX_VOLATILITY);
	}
	
	public int getValue(int time, List<Integer> values) {
		// wrap around if time goes past the amount of song data we have
		time = time % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(0) * 5;
		} else {
			result = values.get(time) * 5;
			int difference = result - getPreviousValue();
			if (Math.abs(difference) > MAX_VOLATILITY)
				result = difference < 0 ? getPreviousValue() - MAX_VOLATILITY
						: getPreviousValue() + MAX_VOLATILITY;
		}
		if (result > UPPER_BOUND)
			result = UPPER_BOUND - randomVolatility();
		if (result < 0){
			int vol = randomVolatility();
			result = Math.max(result - vol, vol);
		}
		addToPreviousValues(result);
		return result;
	}

}
