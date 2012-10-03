package com.jeffmeyerson.moonstocks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	
	public final static String EXTRA_STOCK_ID = "com.jeffmeyerson.moonstocks.STOCK_ID";
	
	private Context context = this;
	
	private String stockNames[] = {"AAPL", "GOOG", "IBM"};
	private int stockPrices[] = {10000, 20000, 30203};
	private int sharesOwned[] = {50, 20, 39};
	
	private MediaPlayer mp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Play launch pad music
        MediaPlayer mp = MediaPlayer.create(this, R.raw.main_menu);
        
        mp.start();
        
        // Add onclick listeners to existing buttons
        Button aaplButton = (Button) findViewById(R.id.button1);
        Button googButton = (Button) findViewById(R.id.button2);
        aaplButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(context, StockActivity.class);
        		intent.putExtra(EXTRA_STOCK_ID, "AAPL");
        		startActivity(intent);
        	}
        });
        googButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(context, StockActivity.class);
        		intent.putExtra(EXTRA_STOCK_ID, "GOOG");
        		startActivity(intent);
        	}
        });
    }
    
    

}