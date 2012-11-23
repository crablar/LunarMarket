package com.jeffmeyerson.moonstocks.volumefunctions;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.jeffmeyerson.moonstocks.pojos.Stock;

public class TemplateVolumeFunction extends VolumeFunction {

	private static ArrayList<Integer> previousValues = new ArrayList<Integer>();
	
	@Override
	public int getValue(int time, List<Integer> values) {
		return values.get(time % values.size());
	}

	@Override
	public int getPreviousValue() {
		if (previousValues.size() == 0)
			Log.d(this.toString(), "StockFunction undeveloped list error");
		return previousValues.get(previousValues.size() - 1);
	}

	@Override
	public String getName() {
		return "TEMPLATE FUNCTION";
	}

	
	
}
