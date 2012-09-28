package com.jeffmeyerson.moonstocks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class StockActivity extends Activity {

	private Handler mHandler = new Handler();
	private  TextView stockPrice;

	private Runnable runnable = new Runnable() {
	    public void run() {
	        double price = (int) (Math.random() * 10000) / 100.0;
	        stockPrice.setText(price + "");
	        stockPrice.invalidate();
	        mHandler.postDelayed(runnable, 2000);
	    }
	};
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        stockPrice = (TextView) findViewById(R.id.stock_price);
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
