package com.jeffmeyerson.moonstocks.pricefunctions;


/**
*
* A simple uptrend with an upper bound. x has a stronger impact on the
* output than y.  This function could be applied by a SongDataManager
* that is trying to produce price that is positively correlated with high
* frequency (x) and negatively correlated with low frequency (y).
*
**/
public class UptrendWithUpperBound implements PriceFunction {
	
	public UptrendWithUpperBound(){}
	
	public double getPrice(double x, double y, int upperBound){
		double result = x * y * 10;
		if(result < upperBound)
			return result;
		else
			return upperBound;
	}

}
