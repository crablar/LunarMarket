package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;

/**
 * @author jeffreymeyerson
 * 
 *         The Function abstract class.
 */
public abstract class PriceFunction {

	protected static final int UPPER_BOUND = 599;
	
	// Because I hate those ridiculous price jumps on the chart
	protected int MAX_VOLATILITY = 50;
	
	public abstract int getValue(int time, List<Integer> values);
	public abstract int getPreviousValue();
	public abstract String getName();

}
