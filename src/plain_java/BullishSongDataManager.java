package plain_java;

/**
 * @author jeffreymeyerson
 *
 * A SongDataManager implementation for producing data for a stock price that is bullish
 * for the duration of T; prices will tend to increase over time.
 *
 */
 
 public class BullishSongDataManager extends SongDataManager{
 
  	// A "bullish" price pattern is one that generally increases over time
 	Function priceDefinition = uptrendWithUpperBound();

	private Function uptrendWithUpperBound() {
		// TODO Auto-generated method stub
		return null;
	}
 
 }