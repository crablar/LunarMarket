package com.jeffmeyerson.moonstocks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import plain_java.ChartFrame;
import plain_java.DataPoint;
import plain_java.Player;
import plain_java.SongData;
import plain_java.SongDataProcessor;
import plain_java.Stock;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
 *         The StockActivity class. The Stock object is accessed at each time
 *         interval in order to get a price to display.
 * 
 */
public class StockActivity extends Activity {

	// Based on screen size?
	private final int MAX_NUMBER_OF_DATA_POINTS_FRAMED = 14;
	
	private Handler mHandler = new Handler();
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

	private Runnable priceFlux;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);

        mp = MediaPlayer.create(this, R.raw.everline_ilk);
        mp.setLooping(true);
		
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
		// stockTicker = this.getIntent().getStringExtra("EXTRA_STOCK_ID");
		stockTicker = "AAPL";
		
		// Start at t = 0
		time = 0;

		// Put the raw text file into an InputStream
		InputStream is = this.getResources().openRawResource(
				R.raw.main_menu_vals);

		// Create a BufferedReader for the InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// Create the SongDataProcessor, which parses the raw text file
		SongDataProcessor songDataProcessor = new SongDataProcessor(br);

		// Create the SongData using the SongDataProcessor
		SongData songData = new SongData(songDataProcessor);

		// Create the Stock object out of the SongData
		stock = new Stock(songData);

		// Create ArrayList of ChartFrames
		ArrayList<ChartFrame> chartFrames = stock.createChartFrames(MAX_NUMBER_OF_DATA_POINTS_FRAMED);

		
		chartView.setCurrentFrame(chartFrames.get(0));

		
		// Set the initial price as a function of time
		price = stock.getPrice(time);

		// Initialize the ChartFrame and give it a data point
		currentFrame = chartFrames.get(0);


		
		// The function that repeatedly updates the stock price and the
		// ChartView
		priceFlux = new Runnable() {
			public void run() {

				// Get the stock price for the current time and set the TextView
				double rawPrice = stock.getPrice(time);
				price = roundToTwoPlaces(rawPrice);
				stockPriceView.setText("$" + price + "");

				// Invalidate the StockPriceView so that it can be reset
				stockPriceView.invalidate();

		        Log.d(this.toString(), "Starting stock activity");

				// Invalidate the ChartView so that it can be reset
				chartView.invalidate();
				
				// Put this function on the message queue
				mHandler.postDelayed(priceFlux, 2000);

				// Move to the next time interval
				time++;
			}
		};


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

				Log.d(this.toString(), player.toString());

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

				Log.d(this.toString(), player.toString());

				// Get and set the player's updated balance
				balance = roundToTwoPlaces(player.getBalance());
				balanceView.setText(balance + "");

				// Get and set the player's updated sharesOwned
				sharesOwned = player.getSharesOwned(stockTicker);
				System.out.println(sharesOwned);

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
	protected void onDestroy(){
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