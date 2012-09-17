/**
 * @author jeffreymeyerson, joshstewart@utexas.edu
 *
 * The SongDataManager abstract class.  SongDataManagers perform operations and calculate
 * price, volume, and other things.
 *
 */
 
 public abstract class SongDataManager {
 
 	public double getPrice( Function priceDefinition, SongData songData );
 	public double getVolume( SontData songData );
 
 }