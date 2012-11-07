package com.jeffmeyerson.moonstocks.pojos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jeffmeyerson.moonstocks.pojos.SongElement.SongElementType;

/**
 * @author jeffreymeyerson
 * 
 *         The Stock class. This class maps a stock like a finite state machine
 *         with each state being represented by a time interval.
 */
public class Stock {

    private List<SongElement> song;

    public Stock(InputStream songData) {

        song = new ArrayList<SongElement>();

        // Read the song data from the InputStream
        BufferedReader br = new BufferedReader(new InputStreamReader(songData));
        String line = "";
        while (true) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                // error :(
                assert(false);
                e.printStackTrace();
            }

            if (line == null) {
                break;
            }

            // line example: "low_freq_values 5 6 6 7 7 8 8 9 9 10 10 11 11 12"
            String[] lineArr = line.split(" ");

            // The key in the above example is "low_freq_values"
            String key = lineArr[0];
            SongElement element;
            if (key.equals("low_freq_values")) {
                element = new SongElement(SongElementType.LOW_FREQUENCY_NOTES);
            } else if (key.equals("mid_freq_values")) {
                element = new SongElement(SongElementType.MID_FREQUENCY_NOTES);
            } else if (key.equals("high_freq_values")) {
                element = new SongElement(SongElementType.HIGH_FREQUENCY_NOTES);
            } else {
                // error :(
                element = null;
            }

            assert(element != null);

            for (int i = 1; i < lineArr.length; i++) {
                element.add(Integer.valueOf(lineArr[i]));
            }

            song.add(element);
        }
    }

    /**
     * Gets the price of the stock at the given point in time.
     * @param time
     * @return
     */
    public double getPrice(int time) {
        // Take the average of the returned values for each SongElement.
        int total = 0;
        for (SongElement element : song) {
            total += element.getValue(time);
        }
        return total / song.size();
    }
}
