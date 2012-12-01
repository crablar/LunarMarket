package com.jeffmeyerson.moonstocks.pojos;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of recent prices that can be asked for a moving average over that period.
 * 
 * @author jeffreymeyerson
 *
 */

public class MovingAverage {
	
	private int numIntervals;
	private List<Double> prices;
	int index;
	
	public MovingAverage(int numIntervals){
		index = 0;
		this.numIntervals = numIntervals;
		prices = new ArrayList<Double>();
	}

	public void addPrice(double price){
		prices.add(index, price);
		index %= numIntervals;
	}
	
	public double getMovingAverage(){
		int total = 0;
		for(Double price : prices)
			total += price;
		return total / prices.size();
	}
	
}
