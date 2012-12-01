package com.jeffmeyerson.moonstocks.activities;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.MovingAverage;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Stock;
import com.jeffmeyerson.moonstocks.views.BuyButton;
import com.jeffmeyerson.moonstocks.views.ChartView;
import com.jeffmeyerson.moonstocks.views.SellButton;

/**
 * @author jeffreymeyerson
 * 
 *         The StockActivity class. The Stock object is accessed at each time
 *         interval in order to get a price to display.
 * 
 */
public class StockActivity extends MoonActivity {
	Context context = this;

	private final int STOCK_CRASH_PRICE = 300;
	private Handler mHandler = new Handler();

	private Button crashThisStockButton;

	private Stock stock;
	private double price;
	private String stockTicker;
	private MovingAverage movingAverage;
	int currentTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);
		InputStream inputStream = null;

		Log.d("class", "In StockActivity");

		// Get the data from the Intent
		Bundle extras = getIntent().getExtras();

		// Get the resources
		final TextView stockPriceView = (TextView) findViewById(R.id.stock_price_view);
		final TextView sharesOwnedView = (TextView) findViewById(R.id.shares_owned_view);
		final TextView balanceView = (TextView) findViewById(R.id.balance_view);
		final Button interpolationButton = (Button) findViewById(R.id.interpolation_button);
		final ChartView chartView = (ChartView) findViewById(R.id.chart);

		movingAverage = new MovingAverage(10);

		// Get the Player object from our activity
		player = (Player) Utility.deserialize(extras.getByteArray("player"));
		((TextView) findViewById(R.id.balance_view)).setText("" + player.getBalance());

		if(player.getLevel() == 1)
			interpolationButton.setText("???");
		else
			interpolationButton.setText("Toggle Interpolation");

		// Get the ticker symbol
		stockTicker = extras.getString("EXTRA_TICKER_ID");

		// Set the ticker name in the TextView
		TextView stockTickerView = (TextView) findViewById(R.id.stock_ticker_text);
		stockTickerView.setText(stockTicker);

		// TODO: Make programmatic
		// Put the raw text file into an InputStream, play music
		if (stockTicker.equals("EVIL")) {
			inputStream = this.getResources().openRawResource(R.raw.evil_vals);
			play(R.raw.evil);
		} else if (stockTicker.equals("BDST")) {
			inputStream = this.getResources().openRawResource(R.raw.bdst_vals);
			play(R.raw.main_menu);
		} else if (stockTicker.equals("WMC")) {
			inputStream = this.getResources().openRawResource(R.raw.wmc_vals);
			play(R.raw.wmc);
		}
		else if (stockTicker.equals("PAR")) {
			inputStream = this.getResources().openRawResource(R.raw.par_vals);
			play(R.raw.par);
		}
		else if (stockTicker.equals("BANK")) {
			inputStream = this.getResources().openRawResource(R.raw.bank_vals);
			play(R.raw.evil);
		}

		// Create the Stock object out of the SongData
		stock = new Stock(inputStream);

        chartView.showAverage(true);

		// Make a temp variable to freeze time
		currentTime = MoonActivity.getTime();

		price = stock.getPrice(currentTime);

		movingAverage.addPrice(price);

		stockPriceView.setText("$" + price);

		// chartView.setCurrentFrame(currentFrame);

		// The function that repeatedly updates the stock price and the
		// ChartView
		Runnable priceFlux = new Runnable() {
			public void run() {
		        if(player.getLevel() > 2){
		        	crashThisStockButton.setClickable(true);
		        	crashThisStockButton.setText("Crash this stock: $" + STOCK_CRASH_PRICE);
		        }
		        else
		        	crashThisStockButton.setText("???");
				// Get the stock price for the current time and set the TextView
				double rawPrice;
				rawPrice = stock.getPrice(currentTime);
				price = Utility.roundCurrency(rawPrice);
				movingAverage.addPrice(price);
				stockPriceView.setText("$" + price);

				chartView.addPoint(Utility.roundCurrencyToFloat(rawPrice));
                chartView.setAverage((float) (movingAverage.getMovingAverage()));

				/**
				 * My understanding of how this section of our code works is
				 * that the view is automatically refreshed by the OS on some
				 * basis, and if the view is still valid then it will be redrawn
				 * but if it has been invalidated since the page was last
				 * loaded, it is redrawn (in our case, with new information).
				 */

				stockPriceView.invalidate();
				chartView.invalidate();
				mHandler.postDelayed(this, Stock.TIMESTEP);

				// Move to the next time interval
				currentTime += Stock.TIMESTEP;
				globalTime += Stock.TIMESTEP;
			}
		};

		// Begin running the function
		mHandler.postDelayed(priceFlux, Stock.TIMESTEP);

		// initialize the stock shares owned
		int sharesOwned = player.getSharesOwned(stockTicker);
		sharesOwnedView.setText(sharesOwned + "");

		// Initialize buttons
		final BuyButton buyButton = (BuyButton) findViewById(R.id.buy_button);
		final SellButton sellButton = (SellButton) findViewById(R.id.sell_button);

		// Add onclick listeners to existing buttons
		buyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Buy!!!!

				if (!player.buy(stockTicker, 1, price)) {
					Toast.makeText(context, "Not enough money to buy stock!", Toast.LENGTH_SHORT).show();
				}

				// Get and set the player's updated balance
				double balance = Utility.roundCurrency(player.getBalance());
				balanceView.setText(balance + "");

				// Get and set the player's updated sharesOwned
				int sharesOwned = player.getSharesOwned(stockTicker);
				sharesOwnedView.setText(sharesOwned + "");
				buyButton.clickedState = true;
				mHandler.postDelayed(new Runnable() { public void run() {
				    buyButton.clickedState = false;
                }}, 100);
			}
		});

		sellButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Sell!!!!
				if (!player.sell(stockTicker, 1, price)) {
					Toast.makeText(context, "You don't own any stock to sell!",
							Toast.LENGTH_SHORT).show();
				}

				// Get and set the player's updated balance
				double balance = Utility.roundCurrency(player.getBalance());
				if (player.updateLevel()) {
                    chartView.doLevelUpAnimation();
                    interpolationButton.setText("Toggle Interpolation");
                    interpolationButton.setClickable(true);
            		Toast.makeText(context, "You reached level " + player.getLevel() + "!", Toast.LENGTH_SHORT).show();
				}
				balanceView.setText(balance + "");

				// Get and set the player's updated sharesOwned
				int sharesOwned = player.getSharesOwned(stockTicker);
				sharesOwnedView.setText(sharesOwned + "");
				sellButton.clickedState = true;
				mHandler.postDelayed(new Runnable() {public void run() {
				    sellButton.clickedState = false;
				}}, 100);
			}
		});

	}

	public void toggleInterpolation(View view) {
	    final ChartView chartView = (ChartView) findViewById(R.id.chart);
	    chartView.toggleInterpolation();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putByteArray("player", Utility.serialize(player));
		outState.putString("stock", stockTicker);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		player = (Player) Utility.deserialize(savedInstanceState
				.getByteArray("player"));
		stockTicker = savedInstanceState.getString("stock");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_stock, menu);
		return true;
	}

	// don't use MoonActivity's options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("player", Utility.serialize(player));
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	
	public void clickCrashTheStock(View view){
		if(player.getBalance() >= STOCK_CRASH_PRICE){
			double balance = player.getBalance();
			player.setBalance(balance - STOCK_CRASH_PRICE);
			Runnable runnable = new Runnable(){
				public void run(){
					stock.fn.toggleCrashed();
					mHandler.postDelayed(this, 5000);
				}
			};
			mHandler.post(runnable);
		}
		else
			Toast.makeText(context, "Not enough money to crash the market!", Toast.LENGTH_SHORT).show();
	}
	

}