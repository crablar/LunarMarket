package com.jeffmeyerson.moonstocks.pojos;

import java.util.List;

import com.jeffmeyerson.moonstocks.pricefunctions.PriceFunction;

/**
 * A SongElement contains information about a single component of a song.
 * The component is defined in the 'type' field, and the information
 * is stored in the 'values' field. 
 * @author josh
 *
 */
public class SongElement {

    public final SongElementType type;

    private final List<Integer> values;
    private final PriceFunction fn;

    public enum SongElementType {
        HIGH_FREQUENCY_NOTES,
        MID_FREQUENCY_NOTES,
        LOW_FREQUENCY_NOTES
    };

    SongElement(SongElementType type, List<Integer> values, PriceFunction fn) {
        this.type = type;
        this.values = values;
        this.fn = fn;
    }

    int getValue(int time) {
        // Right now the steps between time intervals is a constant in the
        // Stock class, but in the future, every SongElement could have its
        // own 'timestep' field as needed.
        return fn.getValue(time / Stock.TIMESTEP, values);
    }
}
