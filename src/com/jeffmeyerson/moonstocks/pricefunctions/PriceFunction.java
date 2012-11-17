package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;

/**
 * @author jeffreymeyerson
 * 
 *         The Function abstract class.
 */
public abstract class PriceFunction {

	protected static final int UPPER_BOUND = 599;

	public abstract int getValue(int time, List<Integer> values);

	abstract int getPreviousValue();
	
}
