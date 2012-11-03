package com.jeffmeyerson.moonstocks.pojos;
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
    private int numProperties;

    /**
     * Creates a SongData object.
     * 
     * @param songName
     */
    public SongData(SongDataProcessor songDataProcessor){

        // Get set of properties that have been mapped by the SongDataProcessor
        Set<String> songProperties = songDataProcessor.getProperties();

        // Get the number of properties mapped within this data set
        numProperties = songProperties.size();

        // Create an array of TimeIntervals
        int numTimeIntervals = songDataProcessor.getNumTimeIntervals();
        timeIntervals = new TimeInterval[numTimeIntervals];
        for(int i = 0; i < numTimeIntervals; i++){
            timeIntervals[i] = new TimeInterval();
        }

        // For each property, iterate through the time intervals and describe that property at that time interval
        for(String property : songProperties){

            // Get the property values for the current property that is being defined
            Double[] propertyValues = songDataProcessor.getValuesFor(property);

            // Iterate through each TimeInterval and set the property value of that TimeInterval
            for(int i = 0; i < numTimeIntervals; i++){
                TimeInterval timeInterval = timeIntervals[i];
                Double propertyValue = propertyValues[i];
                timeInterval.addProperty(property, propertyValue);
                timeIntervals[i] = timeInterval;
            }
        }
    }

    public int getNumIntervals() {
        return timeIntervals.length;
    }

    public int getNumProperties(){
        return numProperties;
    }

    public TimeInterval getTimeInterval(int time){
        return timeIntervals[time];
    }

}
