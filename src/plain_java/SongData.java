package plain_java;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author jeffreymeyerson, joshstewart@utexas.edu
 *
 * The SongData class. Each Stock has a SongData object which is processed by
 * SongDataManagers within that Stock to interpret the SongData in different ways.
 *
 */
public class SongData {

    private TimeInterval[] timeIntervals;
    private static SongDataProcessor songDataProcessor;

    /**
     * Creates a SongData object.
     * 
     * @param songName
     * @throws FileNotFoundException
     */
    public SongData(String songName) throws FileNotFoundException{
    	songDataProcessor = new SongDataProcessor(songName);
    	
    	// Get set of properties that have been mapped by the SongDataProcessor
    	Set<String> songProperties = songDataProcessor.getProperties();
    	
    	// Initialize the time intervals
    	int numTimeIntervals = songDataProcessor.getNumTimeIntervals();
    	timeIntervals = new TimeInterval[numTimeIntervals];
    	
    	// For each property, iterate through the time intervals and describe that property at that time interval
    	for(String property : songProperties){
    		Double[] propertyValues = songDataProcessor.getValuesFor(property);
    		for(int i = 0; i < numTimeIntervals; i++){
    			TimeInterval timeInterval = timeIntervals[i];
    			Double propertyValue = propertyValues[i];
    			timeInterval.addProperty(property, propertyValue);
    		}
    			
    	}
    }

	public int getNumIntervals() {
		return timeIntervals.length;
	}

}
