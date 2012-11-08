package com.jeffmeyerson.moonstocks.activities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

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

import com.jeffmeyerson.moonstocks.R;
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
public class StockActivity extends Activity {
    private Handler mHandler = new Handler();
    private TextView stockPriceView;
    private TextView balanceView;
    private TextView sharesOwnedView;
    private ChartView chartView;
    private Stock stock;
    private double price;
    private String stockTicker;

    // Plays appropriate music for the level
    private MediaPlayer mp;

    private int time;
    private Player player;
    private final DecimalFormat twoDForm = new DecimalFormat("#.00");

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

        //Get the Player object from our activity
        player = (Player) deserializeObject(extras.getByteArray("player"));
        ((TextView) findViewById(R.id.balance_view)).setText("" + player.getBalance());

        //Get the ticker symbol
        stockTicker = extras.getString("EXTRA_TICKER_ID");

        // Set the ticker name in the TextView
        TextView stockTickerView = (TextView) findViewById(R.id.stock_ticker_text);
        stockTickerView.setText(stockTicker);

        InputStream inputStream = null;

        // TODO: Make programmatic
        // Put the raw text file into an InputStream
        if (stockTicker.equals("EVIL")) {
            inputStream = this.getResources().openRawResource(R.raw.evil_vals);
            mp = MediaPlayer.create(this, R.raw.evil);
            mp.setLooping(true);
        } else if (stockTicker.equals("BDST")) {
            inputStream = this.getResources().openRawResource(R.raw.bdst_vals);
            mp = MediaPlayer.create(this, R.raw.bdst);
            mp.setLooping(true);
        } else if (stockTicker.equals("WMC")) {
            inputStream = this.getResources().openRawResource(R.raw.wmc_vals);
            mp = MediaPlayer.create(this, R.raw.wmc);
            mp.setLooping(true);
        }

        // Start at t = 0
        time = 0;

        // Create the Stock object out of the SongData
        stock = new Stock(inputStream);

        price = stock.getPrice(time);

        stockPriceView.setText("$" + price);

        //chartView.setCurrentFrame(currentFrame);

        // The function that repeatedly updates the stock price and the ChartView
        Runnable priceFlux = new Runnable() {
            public void run() {
                // Get the stock price for the current time and set the TextView
                double rawPrice = stock.getPrice(time);
                price = roundToTwoPlaces(rawPrice);
                stockPriceView.setText("$" + price);

                chartView.addPoint(Float.valueOf(twoDForm.format(rawPrice)));

                // Invalidate the StockPriceView so that it can be reset
                stockPriceView.invalidate();

                // Invalidate the ChartView so that it can be reset
                chartView.invalidate();

                // Put this function on the message queue
                mHandler.postDelayed(this, Stock.TIMESTEP);

                // Move to the next time interval
                time += Stock.TIMESTEP;
            }
        };

        // Begin running the function
        mHandler.postDelayed(priceFlux, Stock.TIMESTEP);

        //initialize the stock shares owned
        int sharesOwned = player.getSharesOwned(stockTicker);
        sharesOwnedView.setText(sharesOwned + "");

        // Initialize buttons
        Button buyButton = (Button) findViewById(R.id.buy_button);
        Button sellButton = (Button) findViewById(R.id.sell_button);

        // Add onclick listeners to existing buttons
        buyButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Buy!!!!

                player.buy(stockTicker, 1, price);

                // Get and set the player's updated balance
                double balance = roundToTwoPlaces(player.getBalance());
                balanceView.setText(balance + "");

                // Get and set the player's updated sharesOwned
                int sharesOwned = player.getSharesOwned(stockTicker);
                sharesOwnedView.setText(sharesOwned + "");
            }
        });
        sellButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                // Sell!!!!
                player.sell(stockTicker, 1, price);

                // Get and set the player's updated balance
                double balance = roundToTwoPlaces(player.getBalance());
                balanceView.setText(balance + "");

                // Get and set the player's updated sharesOwned
                int sharesOwned = player.getSharesOwned(stockTicker);
                sharesOwnedView.setText(sharesOwned + "");
            }
        });

    }
    
    public static byte[] serializeObject(Object o) { 
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
     
        try { 
          ObjectOutput out = new ObjectOutputStream(bos); 
          out.writeObject(o); 
          out.close(); 
     
          // Get the bytes of the serialized object 
          byte[] buf = bos.toByteArray(); 
     
          return buf; 
        } catch(IOException ioe) { 
          Log.e("serializeObject", "error", ioe); 
     
          return null; 
        } 
      } 
    
    public static Object deserializeObject(byte[] b) { 
        try { 
          ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
          Object object = in.readObject(); 
          in.close(); 
     
          return object; 
        } catch(ClassNotFoundException cnfe) { 
          Log.e("deserializeObject", "class not found error", cnfe); 
     
          return null; 
        } catch(IOException ioe) { 
          Log.e("deserializeObject", "io error", ioe); 
     
          return null; 
        } 
      } 

  @Override
  protected void onSaveInstanceState(Bundle outState) {
  	super.onSaveInstanceState(outState);

  	outState.putByteArray("player", serializeObject(player));
  	outState.putString("stock", stockTicker);

  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);

      player = (Player) deserializeObject(savedInstanceState.getByteArray("player"));
      stockTicker = savedInstanceState.getString("stock");

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
        Log.d("Time", "time: " + time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
        Log.d("Time", "time: " + time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stock, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
    	Intent returnIntent = new Intent();
    	returnIntent.putExtra("player", serializeObject(player));
    	returnIntent.putExtra("time", getTime());
    	setResult(RESULT_OK,returnIntent);     
    	finish();
    }

    public void quitToMarket(View view) {
    	onBackPressed();
    }

    public double roundToTwoPlaces(double rawPrice) {
        Double result = Double.valueOf(twoDForm.format(rawPrice));
        return result.doubleValue();
    }

    public int getTime(){
        return time;
    }

}