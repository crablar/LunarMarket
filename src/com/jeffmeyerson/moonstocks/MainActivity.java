package com.jeffmeyerson.moonstocks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static String EXTRA_STOCK_ID = "com.jeffmeyerson.moonstocks.STOCK_ID";
	
	private Context context = this;
	
	String stockNames[] = {"AAPL", "GOOG", "IBM"};
	int stockPrices[] = {10000, 20000, 30203};
	int sharesOwned[] = {50, 20, 39};
    private MediaPlayer mp;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mp = MediaPlayer.create(this, R.raw.main_menu);
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
        
        
        // Programmatically add stocks to view
        
        // Get the table layout
        TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
        
        for (int i = 0; i < stockNames.length; i++) {
        	Log.d("main", "adding stock" + stockNames[i]);
        
        	// Set up the row
        	TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        	// Add the button and its frame
        	FrameLayout frame = new FrameLayout(this);
        	// convert DPs to pixels
        	int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics());
        	int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        	frame.setLayoutParams(new LayoutParams(width, height));
        	Button button = new Button(this);
        	button.setGravity(Gravity.CENTER_HORIZONTAL);
        	button.setText(stockNames[i]);
        	final int j = i;
        	button.setOnClickListener(new OnClickListener() {
        		public void onClick(View v) {
        			Intent intent = new Intent(context, StockActivity.class);
        			intent.putExtra(EXTRA_STOCK_ID, stockNames[j]);
        			startActivity(intent);
        		}
        	});
        	frame.addView(button);
        	row.addView(frame);
        
        	// Add share price
        	TextView sharePrice = new TextView(this);
        	sharePrice.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        	sharePrice.setGravity(Gravity.CENTER);
        	sharePrice.setText("$" + stockPrices[i]);
        	// TODO: set android:textAppearance="?android:attr/textAppearanceLarge" on share price
        	row.addView(sharePrice);
        
        	// Add shares owned
        	TextView shares = new TextView(this);
        	shares.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        	shares.setGravity(Gravity.CENTER);
        	shares.setText(String.valueOf(sharesOwned[i]));
        	// TODO: set android:textAppearance="?android:attr/textAppearanceLarge" on shares owned
        	row.addView(shares);
        	marketTable.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	mp.stop();
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	mp.pause();
    }
    
}
