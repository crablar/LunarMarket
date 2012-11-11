package com.jeffmeyerson.moonstocks.activities;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Stock;

public class NewsActivity extends Activity {

    private Context context = this;

    private MediaPlayer mp;
    private long scrollTime = 20000;
    private long scrollTimeInterval = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mp = MediaPlayer.create(this, R.raw.evil);
        mp.setLooping(true);

        // Set up the scrolling stock ticker at the top.
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.stock_scroller);    
        TextView tv = (TextView) findViewById(R.id.scroll_text);

        int time = 0;
        // Get the data from the Intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.containsKey("time")) {
                time = extras.getInt("time");
            }
        }

        tv.setText(makeTextView(time));
        scrollRight(hsv, tv);

        // Set up buttons for the articles.
        // TODO: this should totally be programmatic
        Button lunarMarketOpensButton = (Button) findViewById(R.id.lunar_market_opens_button);
        lunarMarketOpensButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "lunar_market_opens");
                startActivity(intent);
            }
        });

        Button carefulWithTheMoonButton = (Button) findViewById(R.id.careful_with_the_moon_button);
        carefulWithTheMoonButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "careful_with_the_moon");
                startActivity(intent);
            }
        });

        Button buyStockNotGlobusButton = (Button) findViewById(R.id.buy_stock_not_globus_button);
        buyStockNotGlobusButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "buy_stock_not_globus");
                startActivity(intent);
            }
        });

        Button freezeAndThawButton = (Button) findViewById(R.id.freeze_and_thaw_button);
        freezeAndThawButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "freeze_and_thaw");
                startActivity(intent);
            }
        });

        Button bankInitializationButton = (Button) findViewById(R.id.bank_initialization_button);
        bankInitializationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "bank_initialization");
                startActivity(intent);
            }
        });
    }

    private String makeTextView(int time) {
    	InputStream inputStream = null;
    	String marketScroll = "";
    	for (final Company company : getCompanies()) {
			
			// TODO: Make programmatic
	        // Put the raw text file into an InputStream
			if(company.getTicker().equals("EVIL")){
				inputStream = this.getResources().openRawResource(R.raw.evil_vals);
			}
			else if(company.getTicker().equals("BDST")){
				inputStream = this.getResources().openRawResource(R.raw.bdst_vals);
			}
			else if(company.getTicker().equals("WMC")){
				inputStream = this.getResources().openRawResource(R.raw.wmc_vals);
			}
			
			marketScroll += company.getTicker();
			marketScroll += " " + getUpdate(inputStream, time);
			marketScroll += "     ";
    	}
		return marketScroll + marketScroll + marketScroll;
	}

	private String getUpdate(InputStream inputStream, int time) {
		String update = "";
		Stock stock = new Stock(inputStream);

		if(time == 0)
			return "0.0";
		
        Double priceNew = stock.getPrice(time);
        Double priceOld = stock.getPrice(time - 1);
        Double change = priceNew - priceOld;
        
        if(change > 0){
        	update += "+";
        }
        update += change;
		return update;
	}

	public void scrollRight(final HorizontalScrollView h, final TextView tv){
        new CountDownTimer(scrollTime, scrollTimeInterval) { 

            public void onTick(long millisUntilFinished) {

                // Disable Scrolling by setting up an OnTouchListener to do nothing
                h.setOnTouchListener( new OnTouchListener(){ 
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true; 
                    }
                });

                int pos = (int) (1.0 * (scrollTime - millisUntilFinished) / scrollTime * (tv.getWidth() - h.getWidth()));
                h.scrollTo(pos, 0);
            } 

            public void onFinish() {
            	Handler handler = new Handler();
            	handler.postDelayed(new Runnable() {
	            	public void run() {
	            		 h.scrollTo(0, 0);
	            		 scrollRight(h, tv);
	            	}
            	}, 2000);
               
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

   private List<Company> getCompanies() {
        List<Company> result = new LinkedList<Company>();
        String[] companyStrings = getResources().getStringArray(R.array.companies);

        for (String companyString : companyStrings) {
            String[] companyArr = companyString.split(" ");
            String ticker = companyArr[0];
            String name = companyArr[1];
            Company company = new Company(ticker, name);
            result.add(company);
        }
        return result;
   } 
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.actionbar, menu);
       return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

       if (id == R.id.reset_game) {
           return true;
       } else if (id == R.id.menu_news) {
           Intent intent = new Intent(context, NewsActivity.class);
           startActivity(intent);
           return true;
       } else if (id == R.id.menu_system_details) {
           Intent intent = new Intent(context, SystemDetailsActivity.class);
           startActivity(intent);
           return true;
       } else if (id == R.id.menu_stock_market) {
           Intent intent = new Intent(this, MarketActivity.class);
           startActivity(intent);
           return true;
       }

       return false;
   }
}
