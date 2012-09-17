/**
 * @author jeffreymeyerson
 *
 * The SonicTimeInterval class.  A SonicTimeInterval object contains a map of sonic qualities
 * which compose the map's key set that are exhibited by the song at time tn. 
 *
 */
public class SonicTimeInterval extends TimeInterval {
		
	/**
	 *
	 * A map of quality names to their values at time tn.  
	 * Example: "minor chord presence"	-> False
	 * 			"lowFreqDensity"		-> .34
	 *
	 */
	private HashMap<String, Object> qualities;
	
	public SonicTimeInterval (int intervalLength, HashMap<String, Object> qualities){
		this.qualities = qualities;
		this.LENGTH_IN_MS = intervalLength
	}
    
}
