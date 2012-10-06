package plain_java;

/**
 * @author jeffreymeyerson
 *
 * The Player class.  This class is responsible for keeping all the data of a user.
 * 
 */

public class Player {
	
	private Account account;
	private String name;
	
	public Player(int startingMoney, String name){
		account = new Account(startingMoney);
		this.name = name;
	}

	// Buy a share
	public void buy(double price) {
		account.buyShare("AAPL", price);
		
	}
	
	// Sell a share
	public void sell(double price) {
		account.sellShare("AAPL", price);
	}

	public double getBalance() {
		return account.getBalance();
	}
	
	public int getSharesOwned(String tickerSymbol){
		return account.getSharesOwned(tickerSymbol);
	}

}
