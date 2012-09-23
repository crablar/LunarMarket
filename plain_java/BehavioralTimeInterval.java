/**
 * @author jeffreymeyerson
 *
 * The BehavioralTimeInterval class.  A BehavioralTimeInterval object contains a map of behavioral
 * qualities that have occured up to and including this time interval.
 *
 */
public class BehavioralTimeInterval extends TimeInterval {
			
	/**
	 * A map of quality names to their values at time tn.  
	 * Example: "opening price higher than previous time interval"	-> False
	 * 			"decimal increase in volume from end of t( i - 1)"	-> .62
	 */
	private HashMap<String, Object> qualities;
	
	public SonicTimeInterval ( int intervalLength, HashMap<String, Object> qualities ){
		this.qualities = qualities;
		this.LENGTH_IN_MS = intervalLength
	}
    
}
