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
	
	// Object value type is probably a placeholder
	private final HashMap<String, Object>
		
	/**
	 *
	 * A map of quality names to their values at time tn.  
	 * Example: "minor chord presence"	-> False
	 * 			"lowFreqDensity"		-> .34
	 *
	 */
	private HashMap<String, Object> qualities;
	
	public TimeInterval ( int intervalLength, HashMap<String, Object> qualities ){
		this.qualities = qualities;
		this.LENGTH_IN_MS = intervalLength
	}
    
}
