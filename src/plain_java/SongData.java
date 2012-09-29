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

    private ArrayList<TimeInterval> timeIntervals;
    private static SongDataProcessor songDataProcessor;

    // Generate a SongData object from a string with the given name
    public SongData(String songName) throws FileNotFoundException{
    	songDataProcessor = new SongDataProcessor(songName);
    	
    	// Get set of properties that have been mapped by the SongDataProcessor
    	Set<String> songProperties = songDataProcessor.getProperties();
    	
    	// Initialize the time intervals
    	int numTimeIntervals = songDataProcessor.getNumTimeIntervals();
    	timeIntervals = new ArrayList<TimeInterval>(numTimeIntervals);
    	
    	// For each property, iterate through the array of values and create time intervals
    	for(String property : songProperties){
    		
    		
    	}
    }

}
