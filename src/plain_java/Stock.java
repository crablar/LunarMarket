package plain_java;

import java.io.FileNotFoundException;

/**
 * @author jeffreymeyerson
 *
 * The Stock class.  This class eagerly maps a stock as a finite state machine with each state being represented by a time interval.
 *
 */

public class Stock {

    private SongData songData;
    private int[] prices;
    
    public Stock(String stockName) throws FileNotFoundException{
    	songData = new SongData(stockName);
    	prices = new int[songData.getNumIntervals()];
    }


	public int getPrice(int currentTime) {
		return prices[currentTime];
	}

}
