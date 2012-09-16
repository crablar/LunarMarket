/**
 * @author jeffreymeyerson, joshstewart@utexas.edu
 *
 * The SongData class.  SongData is a singleton object that interface
 *
 */
public class SongData {

    private List<SongEvent> events;
    
    private SongData {
        
    }

    public class SongEvent {
        long timestamp;
        SongEventType type;
        int value;

        enum SongEventType {
            MINOR_CHORD,
            MAJOR_CHORD,
            HIGH_FREQUENCY_NOTE,
            MID_FREQUENCY_NOTE,
            LOW_FREQUENCY_NOTE
        }
    }
}
