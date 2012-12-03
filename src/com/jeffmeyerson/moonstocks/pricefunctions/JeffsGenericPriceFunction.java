package com.jeffmeyerson.moonstocks.pricefunctions;

import java.util.List;
import java.util.Random;

import android.util.Log;

import com.jeffmeyerson.moonstocks.activities.MoonActivity;

/**
 * @author jeffreymeyerson
 * 
 *         The Function abstract class.
 */
public abstract class JeffsGenericPriceFunction implements PriceFunction {

	protected int volatilityMultiplier;
	protected boolean crashed;
	protected abstract String getName();
	protected abstract int getPreviousValue();
	protected abstract void addToPreviousValues(int result);
	public static boolean crashedMarket = false;
	Random r = new Random();

	public JeffsGenericPriceFunction() {
		crashed = false;
	}

	public static void toggleCrashedMarket(){
		crashedMarket = !crashedMarket;
	}
	
	public int randomVolatility(){
		int playerLevel = 1;
		if(MoonActivity.player != null)
			playerLevel = MoonActivity.player.getLevel();
		return r.nextInt(playerLevel * volatilityMultiplier) % maxVolatility();
	}

	public int getValue(int time, List<Integer> values) {
		// wrap around if time goes past the amount of song data we have
		time = time % values.size();
		int result = 0;
		if (time == 0) {
			result = values.get(0);
		} else {
			result = values.get(time);
			int difference = result - getPreviousValue();
			if (Math.abs(difference) > maxVolatility())
				result = difference < 0 ? getPreviousValue() - randomVolatility()
						: getPreviousValue() + randomVolatility();
		}
		if (result > upperBound())
			result = upperBound() - randomVolatility();
		if (result < lowerBound()){
			int vol = randomVolatility();
			result = r.nextBoolean() ? lowerBound() : lowerBound() + vol;
		}
		
		if(crashedMarket || crashed){
			Log.d(this.toString(),"Getting crashed value");
			result = 10;
			addToPreviousValues(result);
			return result;
		}
		addToPreviousValues(result);
		return result;
	}

	abstract int upperBound();
	abstract int lowerBound();
	abstract int maxVolatility();

	 public void crash() {
		crashed = !crashed;
	}

}
