package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;

/**
 * @author jeffreymeyerson
 *
 * The Function interface.  Functions are used by PriceCalculators to produce prices
 * from generic parameter types that are consistent across time.
 */
public interface PriceFunction {
    int getValue(int time, List<Integer> values);
}
