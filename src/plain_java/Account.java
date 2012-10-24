package plain_java;

import java.util.HashMap;

import android.util.Log;

/**
 * @author jeffreymeyerson
 *
 * The Account class.  This object represents the collection of assets of its owner.
 * 
 */

public class Account {
	
	private double numDollars;
	
	// A map of stocks to the shares
	private HashMap<String, Integer> stockShares;
	
	public Account(double startingDollars){
		numDollars = startingDollars;
		stockShares = new HashMap<String, Integer>();
	}
	
	// Perform the operations within an account to buy a share
	public void buyShare(String stockTicker, double price){
		numDollars -= price;
		Integer currentShares = stockShares.get(stockTicker);
		if(currentShares == null)
			stockShares.put(stockTicker, 1);
		else{
			currentShares++;
			stockShares.put(stockTicker, currentShares);
		}
	}

	// Perform the operations within an account to sell a share
	public void sellShare(String stockTicker, double price) {
		Integer sharesOwned = stockShares.get(stockTicker);
		Log.d(this.toString(), "SELLING A SHARE OF " + stockTicker );
		Log.d(this.toString(), "Account before sale: " + this.toString() );

		if(sharesOwned != null && sharesOwned > 0){
			sharesOwned--;
			numDollars += price;
			stockShares.put(stockTicker, sharesOwned);
			Log.d(this.toString(), "Account after sale: " + this.toString() );

			
		}
	}
	
	// Get the account balance
	public double getBalance(){
		return numDollars;
	}

	// Get the number of shares for a particular stock
	public int getSharesOwned(String stockTicker) {
		Integer sharesOwned = stockShares.get(stockTicker);

		if(sharesOwned == null){
			sharesOwned = 0;
			stockShares.put(stockTicker, sharesOwned);
		}
		return sharesOwned;
	}
	
	@Override
	public String toString(){
		return "$" + numDollars + " Shares: " + stockShares.toString();
	}

}
