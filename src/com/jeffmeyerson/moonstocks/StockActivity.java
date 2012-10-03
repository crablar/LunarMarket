package com.jeffmeyerson.moonstocks;

import java.io.FileNotFoundException;

import plain_java.Stock;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
	
	//The time interval the stock is currently on
	private int time = 0;

	private Runnable runnable = new Runnable() {
	    public void run() {
	        price = stock.getPrice(time);
	        stockPriceView.setText(price + "");
	        stockPriceView.invalidate();
	        mHandler.postDelayed(runnable, 2000);
	        time++;
	    }
	};
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockName =	getResources().getString(R.string.stock_name);
        try {
			stock = new Stock(stockName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
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