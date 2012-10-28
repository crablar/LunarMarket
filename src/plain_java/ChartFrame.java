package plain_java;

import java.util.ArrayList;

/**
 * The ChartFrame class. Describes a frame of data for the ChartView to
 * represent. Each ChartFrame contains a pointer to the next ChartFrame that
 * will be displayed. After numPointsDefined
 * becomes equal to NUM_POINTS_ALLOWED_IN_FRAME, a point will automatically be
 * deleted in the next frame.
 * 
 * @author jeffreymeyerson
 * 
 */
public class ChartFrame {

	private final int NUM_POINTS_ALLOWED_IN_FRAME = 14;
	private ArrayList<DataPoint> dataPoints;
	private ArrayList<Integer> roundedPrices;
	private ChartFrame nextFrame;
	private boolean dataPointWillBeDeletedFromViewWhenFrameAdvances;
	private int numPointsDefined;
	private boolean deprecatedStatus;


	public ChartFrame() {
		roundedPrices = new ArrayList<Integer>();
		numPointsDefined = 0;
		dataPointWillBeDeletedFromViewWhenFrameAdvances = false;
		dataPoints = new ArrayList<DataPoint>();
		deprecatedStatus = true;
	}

	public void appendDataPoint(DataPoint dataPoint) {
		dataPoints.add(dataPoint);
		roundedPrices.add(new Integer(dataPoint.getRoundedPrice()));
		numPointsDefined++;
		if(numPointsDefined == NUM_POINTS_ALLOWED_IN_FRAME)
			dataPointWillBeDeletedFromViewWhenFrameAdvances = true;
	}
	
	/**
	 * Gets the number of points that have been defined.
	 * @return
	 */
	public int getNumPointsDefined() {
		return dataPoints.size();
	}


	public ArrayList<DataPoint> getDataPoints() {
		return dataPoints;
	}
	
	public ArrayList<Integer> getRoundedPrices(){
		return roundedPrices;
	}

	public void setNextFrame(ChartFrame next) {
		this.nextFrame = next;
	}
	
	public ChartFrame getNextFrame(){
		return nextFrame;
	}
	
	public String toString(){
		return "Prices represented: " + dataPoints.toString();
	}

	public boolean getDeprecatedStatus() {
		return deprecatedStatus;
	}
	
	public void setDeprecatedStatus(boolean deprecated){
		this.deprecatedStatus = deprecated;
	}

}
