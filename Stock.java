/**
 * @author jeffreymeyerson
 *
 * The Stock class.  This class maps a stock as a finite state machine or directed acyclic
 * linked list with each state being represented by a time interval.
 *
 */
public class Stock {
    
    // The previous and next nodes in the linked list
    private TimeInterval prev;
    private TimeInterval next;
    
    // The particular behavioral pattern that this stock exhibits over time
    private BehavioralFunction behavioralFunction;
    
    // The data produced by the song after it is processed by the SongTransformer API
    private SongData songData;
    
}
