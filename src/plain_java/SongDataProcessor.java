package plain_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * @author jeffreymeyerson
 *
 * The SongDataProcessor class.  This class is responsible for importing the appropriate files
 * and generating the data for the SongData class to access.
 */

public class SongDataProcessor {
	
	private int numTimeIntervals;
	
	// A map of properties of songs, which are represented as arrays indexed by their respective time interval values.
	private HashMap<String, Double[]> valueMap  = new HashMap<String, Double[]>();
	
	public SongDataProcessor(BufferedReader br){
		
		String line = "";
		
		while(true){
			
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(line == null)
				break;
			
			// line example: "low_freq_values 5 6 6 7 7 8 8 9 9 10 10 11 11 12"
			
			// Build value array for a map entry
			String[] lineArr = line.split(" ");
			String key = lineArr[0];
			Double[] valueList = new Double[lineArr.length - 1];
			for(int i = 1; i < lineArr.length; i++)
				valueList[i - 1] = new Double(lineArr[i]);
			
			// Put values into map
			valueMap.put(key, valueList);
			
			// Number of time intervals can be established at this point
			numTimeIntervals = valueList.length;
		}

	}

	/**
	 * Get the properties enumerated by the text file the song has generated
	 * 
	 * @return the set of property strings
	 */
	public Set<String> getProperties() {
		return valueMap.keySet();
	}

	public int getNumTimeIntervals() {
		return numTimeIntervals;
	}
	
	public Double[] getValuesFor(String key){
		return valueMap.get(key);
	}

}
