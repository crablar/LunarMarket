package com.jeffmeyerson.moonstocks.pojos;

import com.jeffmeyerson.moonstocks.pricefunctions.WorkaroundFunction;

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
        WorkaroundFunction fn = new WorkaroundFunction();

        prices = new double[songData.getNumIntervals()];
        
        // TODO: Calculate price at t(0)
        TimeInterval interval = songData.getTimeInterval(0);
        double x = interval.getValueOf("high_freq_values");
        double y = interval.getValueOf("low_freq_values");
        double averageFrequency = (x + y) / 2;
        double previousAverageFrequency = 0.0;
        
        prices[0] = 150.0;
        
        for (int i = 1; i < prices.length; i++) {
            interval = songData.getTimeInterval(i);

            x = interval.getValueOf("high_freq_values");
            y = interval.getValueOf("low_freq_values");
            
            previousAverageFrequency = averageFrequency;
            averageFrequency = (x + y) / 2;

            prices[i] = fn.getPrice(prices[i - 1], previousAverageFrequency,
        			averageFrequency, 150, 299);
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
