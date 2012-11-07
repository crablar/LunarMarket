package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.Random;

/**
 * 
 * @author jeffreymeyerson
 * 
 */

public class WorkaroundFunction {

	/**
	 * @param previousPrice
	 * 			  the previous price
	 * @param previousAverageFrequency
	 *            the previous average frequency
	 * @param averageFrequency
	 * 			  the average frequency at t(i)
	 * @param volatility
	 * 			  the maximum percentage change between prices
	 * @param upperBound
	 * 			  the maximum price that can be returned
	 * @return
	 */
	public double getPrice(double previousPrice, double previousAverageFrequency,
			double averageFrequency, int volatility, int upperBound) {
		Random rand = new Random();
		
		// Find the direction and magnitude of the change
		double magnitudeOfChange = averageFrequency - previousAverageFrequency;
		
		// Find the multiplier that will be applied to this price
		double volatilityMultiplier = rand.nextInt(volatility) / 100.0;
		
		double price = volatilityMultiplier * magnitudeOfChange * previousPrice;

		return Math.min(upperBound, price);
	}
}
