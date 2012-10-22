package plain_java;
/**
 * The DataPoint class.  Used to represent points within a ChartFrame.
 * 
 * @author jeffreymeyerson
 *
 */
public class DataPoint {
	
	private double price;
	
	public DataPoint(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String toString(){
		return "" + price;
	}

}
