package com.jeffmeyerson.moonstocks.pojos;

/**
 * @author jeffreymeyerson
 * 
 *         The Stock class. This class maps a stock like a finite state machine
 *         with each state being represented by a time interval.
 * 
 */
public class Stock {

    private double[] prices;
    private PriceCalculator priceCalculator;

    public Stock(SongData songData) {
        // Create a PriceCalculator for calculating the price from variables
        // taken out of the SongData
        this.priceCalculator = new PriceCalculator(songData);
        prices = priceCalculator.getPriceArray();
    }

    public double getPrice(int currentTime) {
        /*
         * Time within StockActivity steadily increases, but there is only a
         * price for each defined time interval.
         */
        return prices[currentTime % prices.length];
    }
}
