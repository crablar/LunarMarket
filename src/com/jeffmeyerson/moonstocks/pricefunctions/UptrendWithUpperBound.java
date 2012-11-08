package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;


/**
*
* A simple uptrend with an upper bound. x has a stronger impact on the
* output than y.  This function could be applied by a SongDataManager
* that is trying to produce price that is positively correlated with high
* frequency (x) and negatively correlated with low frequency (y).
*
**/
public class UptrendWithUpperBound implements PriceFunction {

	private static final int UPPER_BOUND = 299;
	
    @Override
    public int getValue(int time, List<Integer> values) {
        if (time < 0 || time > values.size()) {
            return 0;
        }
        if (time == 0) {
            return values.get(time) * 10;
        } else {
            return Math.min(UPPER_BOUND, values.get(time) * values.get(time - 1) * 10);
        }
    }

}
