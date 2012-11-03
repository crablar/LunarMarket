package com.jeffmeyerson.moonstocks.pojos;

import java.util.ArrayList;


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

	/**
	 * Returns an ArrayList of ChartFrames that is also appropriately described
	 * as a primitive singly linked list with a circle at the end. The circle at
	 * the end is reached when the song is played for the second consecutive
	 * time.
	 * 
	 * @param maxNumberDataPoints
	 * @return
	 */
	public ArrayList<ChartFrame> createChartFrames(int maxNumberDataPoints) {

		// Initialize the result, a list of null ChartFrames, with one entry for
		// each of the first (maxNumberDataPoints - 1) frames, each of which
		// don't
		// have the maxNumberDataPoints, and one entry for each frame containing
		// maxNumberDataPoints data points
		ArrayList<ChartFrame> result = new ArrayList<ChartFrame>();

		for (int i = 0; i < prices.length; i++)
			System.out.println(prices[i]);

		for (int i = 0; i < prices.length + maxNumberDataPoints - 1; i++)
			result.add(new ChartFrame());

		// Initialize all the frames in the list
		int numDataPointsToDiscloseToChartFrame = 1;
		int timeToBeginAt = 0;
		for (int i = 0; i < result.size(); i++) {
			ChartFrame chartFrame = result.get(i);

			// Calculate whether this ChartFrame will wrap around and by how
			// many data points
			int numDataPointsAfterWrapAround = (timeToBeginAt + numDataPointsToDiscloseToChartFrame)
					% prices.length;

			// Calculate number of data points before wrap around
			int numDataPointsBeforeWrapAround = numDataPointsToDiscloseToChartFrame
					- numDataPointsAfterWrapAround;

			for (int j = timeToBeginAt; j < timeToBeginAt
					+ numDataPointsBeforeWrapAround; j++)
				chartFrame.appendDataPoint(new DataPoint(prices[j]));
			for (int j = 0; j < numDataPointsAfterWrapAround; j++)
				chartFrame.appendDataPoint(new DataPoint(prices[j]));

			result.set(i, chartFrame);

			if (numDataPointsToDiscloseToChartFrame != maxNumberDataPoints)
				numDataPointsToDiscloseToChartFrame++;
			else
				timeToBeginAt++;
		}

		// Iterate through the list and give each ChartFrame a
		// pointer to the next frame
		for (int i = 0; i < result.size(); i++) {
			ChartFrame temp = result.get(i);
			int indexToPointTo;
			if (i == result.size() - 1)
				indexToPointTo = result.size() - maxNumberDataPoints;
			else
				indexToPointTo = i + 1;
			ChartFrame next = result.get(indexToPointTo);
			temp.setNextFrame(next);
			result.set(i, temp);
		}

		return result;
	}

}
