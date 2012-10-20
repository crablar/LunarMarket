package com.jeffmeyerson.moonstocks;

import java.util.List;

/**
 * @author jeffreymeyerson, joshstewart@utexas.edu
 *
 * The SongData class.
 *
 */
public class SongData {

    private List<SongEvent> events;

    enum SongEventType {
        MINOR_CHORD,
        MAJOR_CHORD,
        HIGH_FREQUENCY_NOTE,
        MID_FREQUENCY_NOTE,
        LOW_FREQUENCY_NOTE
    }
    
    public class SongEvent {
        long timestamp;
        SongEventType type;
        int value;


    }
}
