/**
 * @author jeffreymeyerson
 *
 * The TimeInterval description.  A time interval is a high-level encapsulation 
 * of the events that occur within the program over a specified number of milliseconds.
 *
 */
public class TimeInterval {
		
	// This is the same entity as the i in t(i) in my notation
	private final int INTERVAL_NUMBER;
	private static final int INTERVAL_LENGTH_IN_MS = 2000;
	
	// Map from properties to their values in this Time Interval
	private final HashMap<String, Double>
		
	/**
	 *
	 * A map of quality names to their values at time tn.  
	 * Example: "minor chord presence"	-> False
	 * 			"lowFreqDensity"		-> .34
	 *
	 */
	private HashMap<String, Double> properties;
	
	public TimeInterval ( int intervalLength, HashMap<String, Object> qualities ){
		this.qualities = qualities;
		this.LENGTH_IN_MS = intervalLength
	}

	public void addProperty(String property, Double propertyValue) {
		
	}
    
}
