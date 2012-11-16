package com.jeffmeyerson.moonstocks.pojos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jeffmeyerson.moonstocks.pojos.SongElement.SongElementType;
import com.jeffmeyerson.moonstocks.pricefunctions.PriceFunction;
import com.jeffmeyerson.moonstocks.pricefunctions.PriceFunctionFactory;

/**
 * @author jeffreymeyerson
 * 
 *         The Stock class. This class maps a stock like a finite state machine
 *         with each state being represented by a time interval.
 */
public class Stock {

	/** Time interval between actions measured in ms. */
	public static final int TIMESTEP = 100;

	private List<SongElement> song;

	public String stockName;

	private double MAX_PRICE = -1;
	private double MIN_PRICE = -1;

	public Stock(InputStream songData) {

		song = new ArrayList<SongElement>();

		// Read the song data from the InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(songData));
		String line = "";
		stockName = "";

		try {
			stockName = br.readLine();
		} catch (IOException e) {
			// error :(
			e.printStackTrace();
		}

		while (true) {
			try {
				line = br.readLine();
			} catch (IOException e) {
				// error :(
				assert (false);
				e.printStackTrace();
			}

			if (line == null) {
				break;
			}

			// line example: "low_freq_values 5 6 6 7 7 8 8 9 9 10 10 11 11 12"
			// The type in the above example is "low_freq_values"
			String[] lineArr = line.split(" ");

			// Parse the type of this line.
			String key = lineArr[0];
			SongElementType type = null;
			if (key.equals("low_freq_values")) {
				type = SongElementType.LOW_FREQUENCY_NOTES;
			} else if (key.equals("mid_freq_values")) {
				type = SongElementType.MID_FREQUENCY_NOTES;
			} else if (key.equals("high_freq_values")) {
				type = SongElementType.HIGH_FREQUENCY_NOTES;
			}

			// Parse the values for this line.
			List<Integer> values = new ArrayList<Integer>();
			for (int i = 1; i < lineArr.length; i++) {
				values.add(Integer.valueOf(lineArr[i]));
			}

			PriceFunction fn = PriceFunctionFactory.getPriceFunctionForStock(stockName);
			SongElement element = new SongElement(type, values, fn);

			assert (element != null);

			song.add(element);

			MAX_PRICE = getMaxPrice();
			MIN_PRICE = getMinPrice();
		}
	}

	/**
	 * Gets the price of the stock at the given point in time.
	 * 
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

	public double getMaxPrice() {
		if(MAX_PRICE != -1)
			return MAX_PRICE;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < song.size(); i++)
			max = Math.max(max, getPrice(i));
		return max;
	}

	public double getMinPrice() {
		if(MIN_PRICE != -1)
			return MIN_PRICE;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < song.size(); i++)
			min = Math.min(min, getPrice(i));
		return min;
	}

}
