package com.jeffmeyerson.moonstocks.volumefunctions;

import java.util.List;

public abstract class VolumeFunction {
	protected static int MAX_LIQUIDITY;
	protected int MAX_VOLATILITY = 50;
		
	public abstract int getValue(int time, List<Integer> values);
	public abstract int getPreviousValue();
	public abstract String getName();
}
