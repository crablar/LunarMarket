package com.jeffmeyerson.moonstocks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import plain_java.Player;
import plain_java.SongData;
import plain_java.SongDataProcessor;
import plain_java.Stock;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * @author jeffreymeyerson
 *
 * The StockActivity class.  The Stock object is accessed at each time interval in order to get a price to display.
 *
 */
public class StockActivity extends Activity {

	private Handler mHandler = new Handler();
	private TextView stockPriceView;
	private TextView balanceView;
	private TextView sharesOwnedView;
	private Stock stock;
	private double price;

	private int time;
	private Player player;
	private Button buyButton;
	private Button sellButton;
	private double balance;
	private int sharesOwned;
	private String stockTicker;
	private DecimalFormat twoDForm = new DecimalFormat("#.00");
    
	private Runnable priceFlux;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        stockPriceView = (TextView) findViewById(R.id.stock_price_view);
        sharesOwnedView = (TextView) findViewById(R.id.shares_owned_view);
        balanceView = (TextView) findViewById(R.id.balance_view);
        buyButton = (Button) findViewById(R.id.buy_button);
        sellButton = (Button) findViewById(R.id.sell_button);

        
        // TODO: Get the Player object from our activity
        //this.getIntent().getComponent().getClass();
        player = new Player(1000, "Jeff");
        
        // TODO: Get the ticker symbol
        stockTicker = "AAPL";
        
        // The function that repeatedly updates the stock price
    	priceFlux = new Runnable() {
    	    public void run() {
    	    	
    	    	// Get the stock price for the current time and set the TextView
    	        double rawPrice = stock.getPrice(time);
    	        price = roundToTwoPlaces(rawPrice);
    	        stockPriceView.setText("$" + price + "");
    	        
    	        // Invalidate the view so that it can be reset
    	        stockPriceView.invalidate();
    	        
    	        // Put this function on the message queue
    	        mHandler.postDelayed(priceFlux, 2000);
    	        
    	        // Move to the next time interval
    	        time++;
    	    }
    	};

    	
    	// Start at t = 0
    	time = 0;
        
        // Put the raw text file into an InputStream
        InputStream is = this.getResources().openRawResource(R.raw.main_menu_vals);
        
        // Create a BufferedReader for the InputStream
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // Create the SongDataProcessor, which parses the raw text file
        SongDataProcessor songDataProcessor = new SongDataProcessor(br);
        
        // Create the SongData using the SongDataProcessor
        SongData songData = new SongData(songDataProcessor);
                
        // Create the Stock object out of the SongData
		stock = new Stock(songData);
		
		// Set the initial price as a function of time
        price = stock.getPrice(time);
        
        // Begin running the function
        mHandler.postDelayed(priceFlux, 2000);
        
    	// Initialize buttons
        buyButton = (Button) findViewById(R.id.buy_button);
        sellButton = (Button) findViewById(R.id.sell_button);
        
        // Add onclick listeners to existing buttons
        buyButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// Buy!!!!
        		player.buy(price);
        		
                // Get and set the player's updated balance
                balance = roundToTwoPlaces(player.getBalance());
                Log.d(this.toString(), "Balance: " + balance);
                balanceView.setText(balance + "");
                
                // Get and set the player's updated sharesOwned
                sharesOwned = player.getSharesOwned(stockTicker);
                sharesOwnedView.setText(sharesOwned + "");
        	}
        	});
        sellButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// Sell!!!!
        		player.sell(price);
        		
                // Get and set the player's updated balance
                balance = roundToTwoPlaces(player.getBalance());
                balanceView.setText(balance + "");
                
                // Get and set the player's updated sharesOwned
                sharesOwned = player.getSharesOwned(stockTicker);
                sharesOwnedView.setText(sharesOwned + "");
        	}
        	});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stock, menu);
        return true;
    }
    
    
    public void quitToMarket(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
    }
    
	public double roundToTwoPlaces(double rawPrice){
		Double result = new Double(twoDForm.format(rawPrice));
		return result.doubleValue();
	}
//	
//	@Override
//	public void onPause(){
//		super.onPause();
//	}
//	
//	@Override
//    public void onResume(){
//    	super.onResume();
//    	setContentView(R.layout.activity_stock);
//    }
}