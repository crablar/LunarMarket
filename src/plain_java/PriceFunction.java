package plain_java;

/**
 * @author jeffreymeyerson
 *
 * The Function interface.  Functions are used by PriceCalculators to produce prices
 * from generic parameter types that are consistent across time.
 *
 */
public interface PriceFunction {
		
	double getPrice(double x, double y, int upperBound);
    
}
