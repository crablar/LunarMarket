package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;

/**
 * A simple uptrend with an upper bound.
 *
 */
public class BDSTStockFunction implements PriceFunction {

    private static final int UPPER_BOUND = 599;

    @Override
    public int getValue(int time, List<Integer> values) {
        // for now, wrap around if time goes past the amount of song data we have
        time = Math.abs(time) % values.size();
        int result = 0;
        if (time == 0) {
            result = values.get(time) * 10;
        } else {
            result = values.get(time) * values.get(time - 1) * 10;
        }
        return Math.min(UPPER_BOUND, result);
    }

}