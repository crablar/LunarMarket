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
	
	public MovingAverage(int numIntervals){
		this.numIntervals = numIntervals;
		prices = new ArrayList<Double>();
	}

	public void addPrice(double price, int time){
		prices.add(time % numIntervals, price);
	}
	
	public double getMovingAverage(){
		int total = 0;
		for(Double price : prices)
			total += price;
		return total / prices.size();
	}
	
}
