/**
 * @author jeffreymeyerson, joshstewart@utexas.edu
 *
 * The SongDataManager abstract class. SongDataManagers perform operations and calculate
 * price, volume, and other things that get reflected in the output to the user.
 *
 */
 
 public abstract class SongDataManager {
 
 	private static Function priceDefinition;
 
 	public double getPrice( SongData songData );
 	public double getVolume( SongData songData );
 
 }