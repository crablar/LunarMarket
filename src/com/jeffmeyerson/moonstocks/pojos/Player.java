package com.jeffmeyerson.moonstocks.pojos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jeffmeyerson.moonstocks.activities.MoonActivity;

/**
 * @author jeffreymeyerson
 * 
 *         The Player class. This class is responsible for keeping all the data
 *         of a user.
 * 
 */
public class Player implements Serializable {

	public enum AvatarType {NO_EYES, ONE_EYE, NORMAL, GOLD_TEETH, SHADES};
	public AvatarType avatarType;
	private static final long serialVersionUID = 5885032670865723288L;
	private String playerName;
	private double balance;
	private Map<String, Integer> stockShares;
	private Protocol protocol;
	private int level;
	private int levelGoal;

	public Player() {
		avatarType = AvatarType.NORMAL;
		balance = MoonActivity.STARTING_MONEY;
		stockShares = new HashMap<String, Integer>();
		protocol = new Protocol();
		level = 1;
		levelGoal = 6000;
	}

	/**
	 * Attempt to buy shares in a stock.
	 * 
	 * @param stockTicker
	 *            - The company to buy stock in.
	 * @param quantity
	 *            - The number of shares to buy.
	 * @param price
	 *            - The price of each share.
	 * @return True if the purchase was made, false if the player had
	 *         insufficient funds.
	 */
	public boolean buy(String stockTicker, int quantity, double price) {
		if (balance < (price * quantity)) {
			return false;
		}
		balance -= price * quantity;
		Integer sharesOwned = stockShares.get(stockTicker);
		if (sharesOwned == null) {
			sharesOwned = Integer.valueOf(0);
		}
		sharesOwned += quantity;
		stockShares.put(stockTicker, sharesOwned);
		return true;
	}

	/**
	 * Attempt to sell shares in a stock.
	 * 
	 * @param stockTicker
	 *            - The company to sell stock in.
	 * @param quantity
	 *            - The number of shares to sell.
	 * @param price
	 *            - The price of each share.
	 * @return True if the transaction was made, false if the player had
	 *         insufficient shares.
	 */
	public boolean sell(String stockTicker, int quantity, double price) {
		Integer sharesOwned = stockShares.get(stockTicker);
		if (sharesOwned == null) {
			sharesOwned = Integer.valueOf(0);
			stockShares.put(stockTicker, sharesOwned);
		}
		if (sharesOwned < quantity) {
			return false;
		}
		sharesOwned -= quantity;
		balance += price * quantity;
		stockShares.put(stockTicker, sharesOwned);
		return true;
	}

	// Getters and setters

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getSharesOwned(String ticker) {
		if (stockShares.get(ticker) == null) {
			stockShares.put(ticker, Integer.valueOf(0));
		}
		return stockShares.get(ticker);
	}

	public String getName() {
		return playerName;
	}

	public void setName(String name) {
		playerName = name;
	}

	public String toString() {
		return "Name: " + playerName + " balance: " + balance;
	}

	public Protocol getProtocol() {
		// TODO Auto-generated method stub
		return this.protocol;
	}

	public int getLevel() {
		return level;
	}

	public boolean updateLevel() {
		if (balance >= levelGoal) {
			level++;
			advanceAvatar();
			levelGoal += 1000;
			return true;
		}
		return false;

	}

	public int getLevelGoal() {
		return levelGoal;
	}
	
	public void advanceAvatar(){
		if(avatarType != AvatarType.SHADES)
			avatarType = AvatarType.values()[avatarType.ordinal() + 1];
	}
	
	public boolean demoteAvatar(){
		if(avatarType == AvatarType.NO_EYES)
			return false;
		avatarType = AvatarType.values()[avatarType.ordinal() - 1];
		return true;
	}
	
	public int pawn(){
		if(demoteAvatar()){
			return 500;
		}
		return 0;
	}
	
}
