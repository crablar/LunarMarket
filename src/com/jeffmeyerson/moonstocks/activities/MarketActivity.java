package com.jeffmeyerson.moonstocks.activities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Stock;
import com.jeffmeyerson.moonstocks.views.ChartView;
import com.jeffmeyerson.moonstocks.views.TickerView;

public class MarketActivity extends MoonActivity {

	private final Context context = this;

	// used for persistence?
	private int size;
	private SharedPreferences mPrefs;

	// A bargain at twice the price
	private final static int MARKET_CRASH_PRICE = 4000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);

		Log.d("class", "In MarketActivity");

        // Set up the scrolling stock ticker at the top.
        final TickerView tickerView = (TickerView) findViewById(R.id.stock_scroller);

        final Button crashTheMarketButton = (Button) findViewById(R.id.crash_the_market_button);

        //if (player.getLevel() > 1) {
        	crashTheMarketButton.setEnabled(true);
        	crashTheMarketButton.setText("Crash the market: $" + MARKET_CRASH_PRICE);
        //} else {
        //	crashTheMarketButton.setText("???");
        //}

		final ChartView chartView = (ChartView) findViewById(R.id.chart);
        chartView.setGridLines(5,10);
        tickerView.scroll();
        play(R.raw.main_menu);
        
        Log.d("level", "player level is " + player.getLevel());
        //otherwise, we don't lower the player's level

        Log.d("stocksOwned", "BANK: " + player.getSharesOwned("BANK"));

		// TODO: move this stuff out to MoonActivity
		mPrefs = getSharedPreferences("moonstocks_prefs", MODE_PRIVATE);
		size = mPrefs.getInt("fileSize", 0);

		// Iterate through the companies on the market and add a row to the
		// table for each one.
		final TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
		for (final Company c : companyMap.values()) {
			final TableRow row = new TableRow(this);
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			// Add a button to take the player to the StockActivity for that
			// company.
			final Button companyButton = new Button(this);
			companyButton.setText(c.getTicker());
			companyButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					final Intent intent = new Intent(context, StockActivity.class);
					intent.putExtra("EXTRA_TICKER_ID", c.getTicker());
					intent.putExtra("player", Utility.serialize(player));
					startActivityForResult(intent, 1);
				}
			});
			row.addView(companyButton);

			final TextView stockPrice = new TextView(this);
			stockPrice.setText("$" + c.getPrice());
			stockPrice.setGravity(Gravity.CENTER);
			stockPrice.setTextAppearance(this, android.R.style.TextAppearance_Large);
			row.addView(stockPrice);

			final TextView sharesOwned = new TextView(this);
			sharesOwned.setText(String.valueOf(player.getSharesOwned(c.getTicker())));
			sharesOwned.setGravity(Gravity.CENTER);
			sharesOwned.setTextAppearance(this, android.R.style.TextAppearance_Large);
			row.addView(sharesOwned);

			marketTable.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		final Runnable tableFlux = new Runnable() {
			public void run() {

		        //if (player.getLevel() > 3) {
		        	crashTheMarketButton.setClickable(true);
		        	crashTheMarketButton.setText("Crash the market: $" + MARKET_CRASH_PRICE);
		        //} else {
		        //	crashTheMarketButton.setText("???");
		        //}

				final TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
				for (int i = 1; i <= companyMap.size(); i++) {
					final TableRow row = (TableRow) marketTable.getChildAt(i); // gets the row
					final String tickerName = (String) ((Button) row.getChildAt(0)).getText();

					final Stock stock = companyMap.get(tickerName).getStock();
					((TextView) row.getChildAt(1)).setText("$" + String.valueOf(stock.getPrice(MoonActivity.getTime())));
					((TextView) row.getChildAt(2)).setText(String.valueOf(player.getSharesOwned(tickerName)));
				}

				// Put this function on the message queue
				mHandler.postDelayed(this, 1000);

			}
		};

		// Market Average chartview
		final Runnable chartFlux = new Runnable() {
			public void run() {
				double marketAvg = Utility.getMarketAverage();
				chartView.addPoint(Utility.roundCurrencyToFloat(marketAvg / 2));
				chartView.invalidate();
				mHandler.postDelayed(this, Stock.TIMESTEP);
			}
		};

		mHandler.postDelayed(tableFlux, 1000);
		mHandler.postDelayed(chartFlux, Stock.TIMESTEP);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putByteArray("player", Utility.serialize(player));
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		player = (Player) Utility.deserialize(savedInstanceState.getByteArray("player"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		update();
	}

	@Override
	protected void onStop() {
		super.onStop();
		update();
	}
	
	protected void onResume() {
	    super.onResume();
	    update();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				updateTable(data);
				update();
			} else if (resultCode == RESULT_CANCELED) {
				// Write your code on no result return
			}
		} // onActivityResult
	}

	// TODO: what exactly does this function do and when is it getting called?
	private void update() {
		Log.d("Running", "update()");

		final SharedPreferences.Editor ed = mPrefs.edit();

		try {
			Log.d("fileError", "writing file");
			final FileOutputStream fos = openFileOutput(PERSISTENCE_FILE, Context.MODE_PRIVATE);
			fos.write(Utility.serialize(player));
			size = Utility.serialize(player).length;
			Log.d("fileError", "buffer size in write: " + size);
			ed.putInt("fileSize", size);
			ed.commit();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d("fileError", "writing file not found", e);
		} catch (IOException e) {
			Log.d("fileError", "writing IO exception", e);
		}
	}

	private void updateTable(Intent data) {

		final TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);

		player = (Player) Utility.deserialize(data.getByteArrayExtra("player"));

		for (int i = 1; i <= companyMap.size(); i++) {
			final TableRow row = (TableRow) marketTable.getChildAt(i); // gets the row
			final String tickerName = (String) ((Button) row.getChildAt(0)).getText();

			final Stock stock = companyMap.get(tickerName).getStock();
			((TextView) row.getChildAt(1)).setText("$" + String.valueOf(stock.getPrice(MoonActivity.getTime())));
			((TextView) row.getChildAt(2)).setText(String.valueOf(player.getSharesOwned(tickerName)));
		}
	}

	public void clickCrashTheMarket(View view){
		if (player.getBalance() >= MARKET_CRASH_PRICE) {
			double balance = player.getBalance();

			player.setBalance(balance - MARKET_CRASH_PRICE);
			MoonActivity.crashTheMarket();
		} else {
			Toast.makeText(context, "Not enough money to crash the market!", Toast.LENGTH_SHORT).show();
		}
	}
}