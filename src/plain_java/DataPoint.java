package plain_java;
/**
 * The DataPoint class.  Used to represent points within a ChartFrame.
 * 
 * @author jeffreymeyerson
 *
 */
public class DataPoint {
	
	private double price;
	private int roundedPrice;
	
	// Rounds the price
	public DataPoint(double price){
		this.price = price;
		this.roundedPrice = (int)price;
	}
	
	public int getRoundedPrice(){
		return roundedPrice;
	}
	
	public double getPrice(){
		return price;
	}

	
	public String toString(){
		return "" + price;
	}

}
