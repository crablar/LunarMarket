package com.jeffmeyerson.moonstocks.pojos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

	private List<Float> dataPoints;
	private ChartFrame nextFrame;

	public ChartFrame() {
		dataPoints = new ArrayList<Float>();
	}

	public void appendDataPoint(double price) {
	    DecimalFormat df = new DecimalFormat("#.00");
	    dataPoints.add(Float.valueOf(df.format(price)));
	}

	public List<Float> getDataPoints() {
		return dataPoints;
	}

	public void setNextFrame(ChartFrame next) {
		this.nextFrame = next;
	}
	
	public ChartFrame getNextFrame(){
		return nextFrame;
	}
}
