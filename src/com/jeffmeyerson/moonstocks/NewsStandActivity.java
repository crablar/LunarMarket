package com.jeffmeyerson.moonstocks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class NewsStandActivity extends Activity {

	private Context context = this;

	private Button lunarMarketOpensButton;
	private Button carefulWithTheMoonButton;
	private Button buyStockNotGlobusButton;
	private Button restart_stock;
	private MediaPlayer mp;
	private long scrollTime = 2000;
	private long scrollTimeInterval = 20;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_stand);
		System.out.println("HELLO");

		lunarMarketOpensButton = (Button) findViewById(R.id.lunar_market_opens_button);
		carefulWithTheMoonButton = (Button) findViewById(R.id.careful_with_the_moon_button);
		buyStockNotGlobusButton = (Button) findViewById(R.id.buy_stock_not_globus_button);
		restart_stock = (Button) findViewById(R.id.restart_stock);

		mp = MediaPlayer.create(this, R.raw.fxxx);
		mp.setLooping(true);

		lunarMarketOpensButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				intent.putExtra("EXTRA_ARTICLE_NAME", "lunar_market_opens");
				startActivity(intent);
			}
		});
		carefulWithTheMoonButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				intent.putExtra("EXTRA_ARTICLE_NAME", "careful_with_the_moon");
				startActivity(intent);
			}
		});
		buyStockNotGlobusButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				intent.putExtra("EXTRA_ARTICLE_NAME", "buy_stock_not_globus");
				startActivity(intent);
			}
		});
		
		final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.stock_scroller);		
		
		scrollRight(hsv);
		
		restart_stock.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//should really use enum for holding all the different companies...
//				
//				InputStream inputStream = null;
//				
//				int time;
//				// Put the raw text file into an InputStream
//				inputStream = getResources().openRawResource(R.raw.evil_vals);
//				
//				inputStream = getResources().openRawResource(R.raw.bdst_vals);
//				
//				inputStream = getResources().openRawResource(R.raw.wmc_vals);
//				
				hsv.scrollTo(0, 0);
				scrollRight(hsv);
			}
			
		});
	}
	
	public void scrollRight(final HorizontalScrollView h){
		new CountDownTimer(scrollTime, scrollTimeInterval) { 
			
		    public void onTick(long millisUntilFinished) { 
		    	Log.d("width", "width: " + h.getWidth());
		    	int pos = (int) (1.0 * (scrollTime - millisUntilFinished) / scrollTime * h.getWidth());
		        h.scrollTo(pos, 0);
		        Log.d("Seconds", "At pixel: " + pos);
		    } 

		    public void onFinish() { 
		    	//Log.d("Finish", "Finish: " + h.getMaxScrollAmount());
		    	h.fullScroll(ScrollView.FOCUS_RIGHT);
		    } 
		 }.start(); 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mp.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.pause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.release();
	}
	
}
