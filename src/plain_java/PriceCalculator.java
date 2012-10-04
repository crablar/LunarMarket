package plain_java;

import PriceFunctions.UptrendWithUpperBound;

/**
 * @author jeffreymeyerson
 *
 * The PriceCalculator class.  This class is responsible for
 * calculating the prices over time for a Stock using a SongData.
 * 
 */

public class PriceCalculator {

	private Double[] prices;
	private PriceFunction priceFunction;
	
	public PriceCalculator(SongData songData) {
				
		// Get the number of time intervals in the SongData
		int numTimeIntervals = songData.getNumIntervals();
		
		// Create a price for each TimeInterval
		this.prices = new Double[numTimeIntervals];
		
		// Different stocks might have different functions in the future
		this.priceFunction = new UptrendWithUpperBound();
		
		// Iterate through the TimeIntervals, calculating price using their respective property values
		for(int i = 0; i < numTimeIntervals; i++){
			
			TimeInterval timeInterval = songData.getTimeInterval(i);
			
			// Get variables to plug into the price function
			double x = timeInterval.getValueOf("high_freq_values");
			double y = timeInterval.getValueOf("low_freq_values");
			
			// Assign the price to the function
			prices[i] = priceFunction.getPrice(x, y, 10);
		}

	}

	public Double[] getPriceArray() {
		return prices;
	}

}
