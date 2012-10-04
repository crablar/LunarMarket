package plain_java;

/**
 * @author jeffreymeyerson
 *
 * The Function interface.  Functions are used by SongDataManagers to produce prices
 * from generic parameter types that are consistent across time.  Each
 * PriceFunction must be able to handle up to n parameters where n is the
 * greatest number of properties we define for a song.
 *
 */
public interface PriceFunction {
		
	double getPrice(double x, double y, int upperBound);
    
}
