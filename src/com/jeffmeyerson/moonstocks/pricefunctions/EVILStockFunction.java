package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;
import java.util.Random;

/**
 * A simple uptrend with an upper bound.
 * 
 */
public class EVILStockFunction implements PriceFunction {

	private static final int UPPER_BOUND = 299;
	private static Random rand = new Random();
	private static int fibThreshold = rand.nextInt(1000);
	private static double fluctuativeMultiplier = 1;
	private static double amplifier = .1;
	private static int fib1 = 0;
	private static int fib2 = 0;
	

	public static int getFib() {
		if (fib1 == 0)
			return fib1++;
		if (fib2 == 0)
			return fib2++;
		int temp = fib1;
		fib1 = fib2;
		fib2 += temp;
		if (fib2 > fibThreshold) {
			fib1 = 0;
			fib2 = 0;
		}
		return fib2;
	}

	@Override
	public int getValue(int time, List<Integer> values) {
				
		// for now, wrap around if time goes past the amount of song data we
		// have
		time = Math.abs(time) % values.size();
		
		// Variables to calculate the percentage change between musical points
		int t1;
		int t2;
		
		int result = 0;
		if (time == 0) {
			result = values.get(time) * 10;
		} else {
			result = values.get(time) * values.get(time - 1) * 10;
		}
		if (fluctuativeMultiplier % 3 == 0)
			amplifier *= -1;
		else
			fluctuativeMultiplier += amplifier;

		result += getFib() * fluctuativeMultiplier;
		if (result < UPPER_BOUND && result > 0)
			return result;
		else
			return Math.abs(result % UPPER_BOUND);
	}

}