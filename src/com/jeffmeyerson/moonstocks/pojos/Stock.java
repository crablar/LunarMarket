package com.jeffmeyerson.moonstocks.pojos;

import com.jeffmeyerson.moonstocks.pricefunctions.PriceFunction;
import com.jeffmeyerson.moonstocks.pricefunctions.UptrendWithUpperBound;

/**
 * @author jeffreymeyerson
 * 
 *         The Stock class. This class maps a stock like a finite state machine
 *         with each state being represented by a time interval.
 */
public class Stock {

    private double[] prices;

    public Stock(SongData songData) {
        // Use a PriceFunction to calculate the prices.
        PriceFunction fn = new UptrendWithUpperBound();

        prices = new double[songData.getNumIntervals()];

        for (int i = 0; i < prices.length; i++) {
            TimeInterval interval = songData.getTimeInterval(i);

            double x = interval.getValueOf("high_freq_values");
            double y = interval.getValueOf("low_freq_values");

            prices[i] = fn.getPrice(x, y, 299);
        }
    }

    public double getPrice(int currentTime) {
        /*
         * Time within StockActivity steadily increases, but there is only a
         * price for each defined time interval.
         */
        return prices[currentTime % prices.length];
    }
}
