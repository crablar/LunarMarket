package plain_java;


/**
 * @author jeffreymeyerson
 *
 * The Stock class.  This class maps a stock as a finite state machine with each state being represented by a time interval.
 *
 */

public class Stock {

    private SongData songData;
    private Double[] prices;
    private PriceCalculator priceCalculator;
    
    public Stock(SongData songData){
    	this.songData = songData;
    	this.priceCalculator = new PriceCalculator(songData);
    	prices = priceCalculator.getPriceArray();
    }


	public Double getPrice(int currentTime) {
		
		/* Time within StockActivity steadily increases, but there
		** is only a price for each defined time interval.
		*/
		return prices[currentTime % prices.length];
	}

}
