package com.jeffmeyerson.moonstocks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	private Stock stock;
	private double price;
	private Runnable runnable;
	private int time;

    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        stockPriceView = (TextView) findViewById(R.id.stock_price_view);
        
        
        // The function that repeatedly updates the stock price
    	runnable = new Runnable() {
    	    public void run() {
    	    	
    	    	// Get the stock price for the current time
    	        price = stock.getPrice(time);
    	        
    	        // Set the price TextView
    	        stockPriceView.setText(price + "");
    	        
    	        // Invalidate the view so that it can be reset
    	        stockPriceView.invalidate();
    	        
    	        // Put this function on the message queue
    	        mHandler.postDelayed(runnable, 2000);
    	        
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
        mHandler.postDelayed(runnable, 2000);
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
}