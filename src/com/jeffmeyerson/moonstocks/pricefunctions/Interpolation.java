package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jeffreymeyerson
 * 
 */

public class Interpolation implements PriceFunction {
	private static final int UPPER_BOUND = 299;

	private final static int REAL_POINT_FREQUENCY = 5;
	private PriceFunction functionToInterpolateOn;

	public Interpolation(PriceFunction function) {
		functionToInterpolateOn = function;
	}

	public int getValue(int time, List<Integer> values) {
		if (time % REAL_POINT_FREQUENCY == 0)
			return functionToInterpolateOn.getValue(time, values);
		int timeSincePreviousRealPoint = time % REAL_POINT_FREQUENCY;
		int timeAtPreviousRealPoint = time - timeSincePreviousRealPoint;
		int timeAtNextRealPoint = timeAtPreviousRealPoint
				+ REAL_POINT_FREQUENCY;
		int timeElapsedSinceLastPoint = time - timeAtPreviousRealPoint;
		int previousRealPoint = functionToInterpolateOn.getValue(
				timeAtPreviousRealPoint, values);
		int nextRealPoint = functionToInterpolateOn.getValue(
				timeAtNextRealPoint, values);
		int priceDifferenceBetweenRealPoints = nextRealPoint
				- previousRealPoint;
		int result = previousRealPoint + timeElapsedSinceLastPoint
				* priceDifferenceBetweenRealPoints;
		if (result < 1)
			return 1;
		return Math.min(result, UPPER_BOUND);
	}

}
