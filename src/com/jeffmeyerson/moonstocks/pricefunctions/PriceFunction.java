package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;

public interface PriceFunction {
    public int getValue(int time, List<Integer> values);

    // TODO: this should be moved to Stock or possibly even higher up
    public void crash();
}
