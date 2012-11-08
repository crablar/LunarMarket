package com.jeffmeyerson.moonstocks.pojos;

import java.util.ArrayList;
import java.util.List;

import com.jeffmeyerson.moonstocks.pricefunctions.PriceFunction;
import com.jeffmeyerson.moonstocks.pricefunctions.UptrendWithUpperBound;

public class SongElement {

    public final SongElementType type;

    private List<Integer> values;
    private PriceFunction fn;

    public enum SongElementType {
        HIGH_FREQUENCY_NOTES,
        MID_FREQUENCY_NOTES,
        LOW_FREQUENCY_NOTES
    };

    SongElement(SongElementType type) {
        values = new ArrayList<Integer>();
        // TODO: make this configurable
        fn = new UptrendWithUpperBound();
        this.type = type;
    }

    void add(Integer value) {
        values.add(value);
    }

    int getValue(int time) {
        return fn.getValue(time % values.size(), values);
    }
}
