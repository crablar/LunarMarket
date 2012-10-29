package com.jeffmeyerson.moonstocks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import plain_java.ChartFrame;
import plain_java.Player;
import plain_java.SongData;
import plain_java.SongDataProcessor;
import plain_java.Stock;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author jeffreymeyerson
 * 
 *         The StockActivity class. The Stock object is accessed at each time
 *         interval in order to get a price to display.
 * 
 */
public class StockActivity extends Activity {

	// Based on screen size?
	private final int MAX_NUMBER_OF_DATA_POINTS_FRAMED = 14;

	private Handler mHandler = new Handler();
	private TextView stockTickerView;
	private TextView stockPriceView;
	private TextView balanceView;
	private TextView sharesOwnedView;
	private ChartView chartView;
	private Stock stock;
	private double price;

	// Plays appropriate music for the level
	private MediaPlayer mp;

	private int time;
	private Player player;
	private Button buyButton;
	private Button sellButton;
	private double balance;
	private int sharesOwned;
	private String stockTicker;
	private DecimalFormat twoDForm = new DecimalFormat("#.00");
	private ChartFrame currentFrame;
	private int currentFrameNumber;
	
	private Runnable priceFlux;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);

		// Get the data from the Intent
		Bundle extras = getIntent().getExtras();
		
		// Get the resources
		final Resources res = getResources();
		//final int timeBetweenPriceChangeMs = res.getInteger(R.string.time_between_price_change_ms);
		final int timeBetweenPriceChangeMs = 320;

		stockTickerView = (TextView) findViewById(R.id.stock_ticker_text);
		stockPriceView = (TextView) findViewById(R.id.stock_price_view);
		sharesOwnedView = (TextView) findViewById(R.id.shares_owned_view);
		balanceView = (TextView) findViewById(R.id.balance_view);
		
		chartView = (ChartView) findViewById(R.id.chart);

		buyButton = (Button) findViewById(R.id.buy_button);
		sellButton = (Button) findViewById(R.id.sell_button);

		// TODO: Get the Player object from our activity
		// this.getIntent().getComponent().getClass();
		player = new Player(1000, "Jeff");

		// TODO: Get the ticker symbol
		if (extras != null) {
			stockTicker = extras.getString("EXTRA_TICKER_ID");
		}

		stockTickerView.setText(stockTicker);

		InputStream inputStream = null;
		
		// TODO: Make programmatic
		{
			// Put the raw text file into an InputStream
			if (stockTicker.equals("EVIL")) {
				inputStream = this.getResources().openRawResource(
						R.raw.evil_vals);
				mp = MediaPlayer.create(this, R.raw.evil);
				mp.setLooping(true);
			}
			if (stockTicker.equals("BDST")) {
				inputStream = this.getResources().openRawResource(
						R.raw.bdst_vals);
				mp = MediaPlayer.create(this, R.raw.bdst);
				mp.setLooping(true);
			}
			if (stockTicker.equals("WMC")) {
				inputStream = this.getResources().openRawResource(
						R.raw.wmc_vals);
				mp = MediaPlayer.create(this, R.raw.wmc);
				mp.setLooping(true);
			}

		}

		// Start at t = 0
		time = 0;

		// Create a BufferedReader for the InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		// Create the SongDataProcessor, which parses the raw text file
		SongDataProcessor songDataProcessor = new SongDataProcessor(br);

		// Create the SongData using the SongDataProcessor
		SongData songData = new SongData(songDataProcessor);

		// Create the Stock object out of the SongData
		stock = new Stock(songData);
		
		price = stock.getPrice(time);
		
		stockPriceView.setText("$" + price);

		// Create ArrayList of ChartFrames
		ArrayList<ChartFrame> chartFrames = stock
				.createChartFrames(MAX_NUMBER_OF_DATA_POINTS_FRAMED);

		currentFrame = chartFrames.get(0);
		
		currentFrame.setDeprecatedStatus(false);
		
		chartView.setCurrentFrame(currentFrame);
		
		// The function that repeatedly updates the stock price and the
		// ChartView
		priceFlux = new Runnable() {
			public void run() {

				// Get the stock price for the current time and set the TextView
				double rawPrice = stock.getPrice(time);
				price = roundToTwoPlaces(rawPrice);
				stockPriceView.setText("$" + price);
								
				// Invalidate the StockPriceView so that it can be reset
				stockPriceView.invalidate();

				// Invalidate the ChartView so that it can be reset
				chartView.invalidate();
				
				currentFrame.setDeprecatedStatus(true);
				
				currentFrame = currentFrame.getNextFrame();
				
				chartView.setCurrentFrame(currentFrame);
				
				// Put this function on the message queue
				mHandler.postDelayed(priceFlux, timeBetweenPriceChangeMs);

				// Move to the next time interval
				time++;
			}
		};

		// Begin running the function
		mHandler.postDelayed(priceFlux, timeBetweenPriceChangeMs);

		// Initialize buttons
		buyButton = (Button) findViewById(R.id.buy_button);
		sellButton = (Button) findViewById(R.id.sell_button);

		// Add onclick listeners to existing buttons
		buyButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Buy!!!!

				player.buy(stockTicker, price);

				// Get and set the player's updated balance
				balance = roundToTwoPlaces(player.getBalance());
				balanceView.setText(balance + "");

				// Get and set the player's updated sharesOwned
				sharesOwned = player.getSharesOwned(stockTicker);
				sharesOwnedView.setText(sharesOwned + "");
			}
		});
		sellButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Sell!!!!
				player.sell(stockTicker, price);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_stock, menu);
		return true;
	}

	public void quitToMarket(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public double roundToTwoPlaces(double rawPrice) {
		Double result = new Double(twoDForm.format(rawPrice));
		return result.doubleValue();
	}
	//
	// @Override
	// public void onPause(){
	// super.onPause();
	// }
	//
	// @Override
	// public void onResume(){
	// super.onResume();
	// setContentView(R.layout.activity_stock);
	// }
}