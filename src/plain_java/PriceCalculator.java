package plain_java;

import PriceFunctions.UptrendWithUpperBound;

/**
 * @author jeffreymeyerson
 *
 * The PriceCalculator class.  This class is responsible for
 * calculating the prices over time for a SongData object.
 * 
 */

public class PriceCalculator {

	private Double[] prices;
	private PriceFunction priceFunction;
	
	public PriceCalculator(SongData songData) {
				
		// The number of time intervals in the SongData
		int numTimeIntervals = songData.getNumIntervals();
		
		this.prices = new Double[numTimeIntervals];
		
		// Different stocks might have different functions in the future
		this.priceFunction = new UptrendWithUpperBound();
		
		for(int i = 0; i < numTimeIntervals; i++){
			TimeInterval timeInterval = songData.getTimeInterval(i);
			
			double x = timeInterval.getValueOf("high_freq_values");
			System.out.println("PC initializing: 1");

			double y = timeInterval.getValueOf("low_freq_values");
			System.out.println("PC initializing: 2");
			
			prices[i] = priceFunction.getPrice(x, y, 10);
			System.out.println("PC initializing: 3");

		}
		
		System.out.println("Price calculator initialized.");

	}

	public Double[] getPriceArray() {
		return prices;
	}

}
