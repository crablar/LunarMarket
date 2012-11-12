package com.jeffmeyerson.moonstocks.activities;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Stock;
import com.jeffmeyerson.moonstocks.views.ChartView;

/**
 * @author jeffreymeyerson
 * 
 *         The StockActivity class. The Stock object is accessed at each time
 *         interval in order to get a price to display.
 * 
 */
public class StockActivity extends MoonActivity {
    Context context = this;

	private Handler mHandler = new Handler();
	private TextView stockPriceView;
	private TextView balanceView;
	private TextView sharesOwnedView;
	private ChartView chartView;
	private Stock stock;
	private double price;
	private String stockTicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);

		// Get the data from the Intent
		Bundle extras = getIntent().getExtras();

		// Get the resources
		stockPriceView = (TextView) findViewById(R.id.stock_price_view);
		sharesOwnedView = (TextView) findViewById(R.id.shares_owned_view);
		balanceView = (TextView) findViewById(R.id.balance_view);

		chartView = (ChartView) findViewById(R.id.chart);


		// Get the Player object from our activity
		player = (Player) Utility.deserialize(extras.getByteArray("player"));
		((TextView) findViewById(R.id.balance_view)).setText(""
				+ player.getBalance());

		// Get the ticker symbol
		stockTicker = extras.getString("EXTRA_TICKER_ID");

		// Set the ticker name in the TextView
		TextView stockTickerView = (TextView) findViewById(R.id.stock_ticker_text);
		stockTickerView.setText(stockTicker);

		InputStream inputStream = null;

		// TODO: Make programmatic
		// Put the raw text file into an InputStream
		if (stockTicker.equals("EVIL")) {
			inputStream = this.getResources().openRawResource(R.raw.evil_vals);
			play(R.raw.evil);
		} else if (stockTicker.equals("BDST")) {
			inputStream = this.getResources().openRawResource(R.raw.bdst_vals);
			play(R.raw.evil);
		} else if (stockTicker.equals("WMC")) {
			inputStream = this.getResources().openRawResource(R.raw.wmc_vals);
			play(R.raw.evil);
		}

		// Start at t = 0
		time = 0;

		// Create the Stock object out of the SongData
		stock = new Stock(inputStream);

		price = stock.getPrice(time);

		stockPriceView.setText("$" + price);

		// chartView.setCurrentFrame(currentFrame);

		// The function that repeatedly updates the stock price and the
		// ChartView
		Runnable priceFlux = new Runnable() {
			public void run() {
				// Get the stock price for the current time and set the TextView
				double rawPrice = stock.getPrice(time);
				price = Utility.roundCurrency(rawPrice);
				stockPriceView.setText("$" + price);

				chartView.addPoint(Utility.roundCurrencyToFloat(rawPrice));

				/**
				 * My understanding of how this section of our code works is
				 * that the view is automatically refreshed by the OS on some
				 * basis, and if the view is still valid then it will be redrawn
				 * but if it has been invalidated since the page was last
				 * loaded, it is redrawn (in our case, with new information).
				 */

				// Invalidate the StockPriceView so that it can be reset
				stockPriceView.invalidate();

				// Invalidate the ChartView so that it can be reset
				chartView.invalidate();


				// Put this function on the message queue
				mHandler.postDelayed(this, Stock.TIMESTEP);

				// Move to the next time interval
				time += Stock.TIMESTEP;
				globalTime += Stock.TIMESTEP;
			}
		};

		// Begin running the function
		mHandler.postDelayed(priceFlux, Stock.TIMESTEP);

		// initialize the stock shares owned
		int sharesOwned = player.getSharesOwned(stockTicker);
		sharesOwnedView.setText(sharesOwned + "");

		// Initialize buttons
		View buyButton = (View) findViewById(R.id.buy_button);
		Button sellButton = (Button) findViewById(R.id.sell_button);

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
			}
		});
		
		sellButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Sell!!!!
                if (!player.sell(stockTicker, 1, price)) {
                    Toast.makeText(context, "You don't own any stock to sell!", Toast.LENGTH_SHORT).show();
                }

				// Get and set the player's updated balance
				double balance = Utility.roundCurrency(player.getBalance());
				balanceView.setText(balance + "");

				// Get and set the player's updated sharesOwned
				int sharesOwned = player.getSharesOwned(stockTicker);
				sharesOwnedView.setText(sharesOwned + "");
			}
		});

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

		player = (Player) Utility.deserialize(savedInstanceState.getByteArray("player"));
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
		returnIntent.putExtra("time", getTime());
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public int getTime() {
		return time;
	}
	
	public void quitToMarket(View view) {
	    onBackPressed();
	}

}