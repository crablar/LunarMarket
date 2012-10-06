package plain_java;

import java.util.HashMap;

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
	private final int INTERVAL_LENGTH_IN_MS;

	// Map from properties to their values in this Time Interval		
	private HashMap<String, Double> properties;

	/**
	 *
	 * A map of quality names to their values at time tn.  
	 * Example: "minor chord presence"	-> False
	 * 			"lowFreqDensity"		-> .34
	 *
	 */
	
	public TimeInterval (int intervalNumber, int intervalLength){
		this.INTERVAL_NUMBER = intervalNumber;
		this.INTERVAL_LENGTH_IN_MS = intervalLength;
		properties = new HashMap<String, Double>();
	}

	public void addProperty(String property, Double propertyValue) {
		properties.put(property, propertyValue);
	}
	
	public Double getValueOf(String key){
		return properties.get(key);
	}
	
	public HashMap<String, Double> getProperties(){
		return properties;
	}
   	
}
