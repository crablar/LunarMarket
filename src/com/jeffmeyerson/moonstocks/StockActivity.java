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
	private String stockName;
	private Stock stock;
	private double price;
	private Runnable runnable;
	private int time;

    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	runnable = new Runnable() {
    	    public void run() {
    	        price = stock.getPrice(time);
    	        stockPriceView.setText(price + "");
    	        stockPriceView.invalidate();
    	        mHandler.postDelayed(runnable, 2000);
    	        time++;
    	        System.out.println(time);
    	    }
    	};
    	time = 0;
        stockName =	"AAPL";
        InputStream is = this.getResources().openRawResource(R.raw.main_menu_vals);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // Create the SongDataProcessor
        SongDataProcessor songDataProcessor = new SongDataProcessor(br);
        
        // Create the SongData using the SongDataProcessor
        SongData songData = new SongData(songDataProcessor);
                
        // Create the Stock object
		stock = new Stock(songData);
		
		// Get initial price
        price = stock.getPrice(time);
        setContentView(R.layout.activity_stock);
        stockPriceView = (TextView) findViewById(R.id.stock_price_view);
        
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