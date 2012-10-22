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
	public void buyShare(String stockName, double price){
		numDollars -= price;
		Integer currentShares = stockShares.get(stockName);
		if(currentShares == null)
			stockShares.put(stockName, 1);
		else{
			currentShares++;
			stockShares.put(stockName, currentShares);
		}
	}

	// Perform the operations within an account to sell a share
	public void sellShare(String stockName, double price) {
		Integer sharesOwned = stockShares.get(stockName);
		Log.d(this.toString(), "SELLING A SHARE OF " + stockName );
		Log.d(this.toString(), "Account before sale: " + this.toString() );

		if(sharesOwned != null && sharesOwned > 0){
			sharesOwned--;
			numDollars += price;
			stockShares.put(stockName, sharesOwned);
			Log.d(this.toString(), "Account after sale: " + this.toString() );

			
		}
	}
	
	// Get the account balance
	public double getBalance(){
		return numDollars;
	}

	// Get the number of shares for a particular stock
	public int getSharesOwned(String tickerSymbol) {
		Integer sharesOwned = stockShares.get(tickerSymbol);

		if(sharesOwned == null){
			sharesOwned = 0;
			stockShares.put(tickerSymbol, sharesOwned);
		}
		return sharesOwned;
	}
	
	@Override
	public String toString(){
		return "$" + numDollars + " Shares: " + stockShares.toString();
	}

}
