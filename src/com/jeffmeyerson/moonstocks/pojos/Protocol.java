package com.jeffmeyerson.moonstocks.pojos;

import java.io.Serializable;

public class Protocol implements Serializable {

	private static final long serialVersionUID = 2986579522968148222L;

	public Protocol() {
	}

	public static String getProtocolVerbose() {
		return "This autonomy implements StockBot version xF4D2. The primary objective"
				+ " of StockBot is to maximize rate of return.  Failure will be met with "
				+ "autonomic deprivation.";
	}
}
