package com.jeffmeyerson.moonstocks.pojos;

import java.io.Serializable;

/**
 * The Company class. A POJO for representing some basic data about a
 * company needed to form the StockActivity.
 * 
 * @author jeffreymeyerson
 * 
 */
public class Company implements Serializable {

    private static final long serialVersionUID = -878415599490397998L;
    private final String ticker;
    private final String name;
    private final Stock stock;
    private double price; 

    
    public Company(String ticker, String name, Stock stock) {
        this.ticker = ticker;
        this.name = name;
        this.stock = stock;
    }

    // Getters and setters
    
    public String getName() {
        return name;
    }

    public String getTicker(){
        return ticker;
    }
    
    public Stock getStock(){
    	return stock;
    }

    public double getPrice() {
        return price;
    }
    

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
