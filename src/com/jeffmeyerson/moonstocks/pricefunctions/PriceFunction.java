package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;
import java.util.Random;

/**
 * @author jeffreymeyerson
 * 
 *         The Function abstract class.
 */
public abstract class PriceFunction {

	protected abstract String getName();
	protected abstract int getPreviousValue();
	protected abstract void addToPreviousValues(int result);

	public int randomVolatility(){
		Random r = new Random();
		return r.nextInt(maxVolatility());
	}

	public int getValue(int time, List<Integer> values) {
		// wrap around if time goes past the amount of song data we have
		time = time % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(0) * values.get(0);
		} else {
			result = values.get(time) * values.get(time);
			int difference = result - getPreviousValue();
			if (Math.abs(difference) > maxVolatility())
				result = difference < 0 ? getPreviousValue() - randomVolatility()
						: getPreviousValue() + randomVolatility();
		}
		if (result > upperBound())
			result = upperBound() - randomVolatility();
		if (result < 0){
			int vol = randomVolatility();
			result = Math.max(result - vol, vol);
		}
		addToPreviousValues(result);
		return result;
	}

	abstract int upperBound();

	abstract int maxVolatility();

}
