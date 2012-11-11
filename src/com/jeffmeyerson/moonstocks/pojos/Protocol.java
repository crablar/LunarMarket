package com.jeffmeyerson.moonstocks.pojos;

import java.io.Serializable;

public class Protocol implements Serializable {

    private static final long serialVersionUID = 2986579522968148222L;

    public Protocol() {
	}

	public static String getProtocolVerbose() {
		return "                                                                                                                                 "
				+ "This autonomy implements StockBot version xF4D2. The primary objective"
				+ " of StockBot is to minimize variance while maximizing rate of return.  A certain amount of profit must be"
				+ " earned while maintaining at most a certain level of variance for one hour.  Neither threshold is "
				+ "disclosed to this autonomy, lest incentive for optimization suffer.  Failure will be met with "
				+ "autonomic deprivation and retirement to the task which this hardware was previously implemented to perform.";
	}
}
