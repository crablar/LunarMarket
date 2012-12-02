package com.jeffmeyerson.moonstocks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.views.TickerView;

public class NewsActivity extends MoonActivity {

    final private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Log.d("class", "In NewsActivity");

        // play a lovely little tune
        play(R.raw.evil);

        // Set up the scrolling stock ticker at the top.
        TickerView tickerView = (TickerView) findViewById(R.id.stock_scroller);

        tickerView.scroll();


        // Get the data from the Intent
     	Bundle extras = getIntent().getExtras();

     	if (extras == null) {
     	   Toast.makeText(context, "ERROR: Didn't get an extras bundle!", Toast.LENGTH_SHORT).show();
     	} else  {
     	    if (extras.containsKey("player")) {
     	        player = (Player) Utility.deserialize(extras.getByteArray("player"));
     	    } else {
     	        Toast.makeText(context, "ERROR: Intent bundle did not contain a player!", Toast.LENGTH_SHORT).show();
     	        if (player == null) {
     	            player = new Player();
     	        }
     	    }
     	}

        final int level = player.getLevel();

        Log.d("level", "player level in newsActivity " + level);

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
        
        Button buyStockNotGlobusButton = (Button) findViewById(R.id.buy_stock_not_globus_button);
        if(level < 5){	   
        	buyStockNotGlobusButton.setText("???");
        	buyStockNotGlobusButton.setGravity(Gravity.CENTER);
        	buyStockNotGlobusButton.setEnabled(false);
        }
        else{
	        buyStockNotGlobusButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent intent = new Intent(context, ArticleActivity.class);
	                intent.putExtra("EXTRA_ARTICLE_NAME", "buy_stock_not_globus");
	                startActivity(intent);
	            }
	        });
        }

        Button carefulWithTheMoonButton = (Button) findViewById(R.id.careful_with_the_moon_button);
        if(level < 3){	        
	        carefulWithTheMoonButton.setText("???");
	        carefulWithTheMoonButton.setGravity(Gravity.CENTER);
	        carefulWithTheMoonButton.setEnabled(false);
        }
        else{
        	carefulWithTheMoonButton.setEnabled(true);
	        carefulWithTheMoonButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent intent = new Intent(context, ArticleActivity.class);
	                intent.putExtra("EXTRA_ARTICLE_NAME", "careful_with_the_moon");
	                startActivity(intent);
	            }
	        });
        }
        
        Button freezeAndThawButton = (Button) findViewById(R.id.freeze_and_thaw_button);
        if(level < 4){
        	freezeAndThawButton.setText("???");
        	freezeAndThawButton.setGravity(Gravity.CENTER);
        	freezeAndThawButton.setEnabled(false);
        }
        else{
	        freezeAndThawButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent intent = new Intent(context, ArticleActivity.class);
	                intent.putExtra("EXTRA_ARTICLE_NAME", "freeze_and_thaw");
	                startActivity(intent);
	            }
	        });
        }
        
        Button bankInitializationButton = (Button) findViewById(R.id.bank_initialization_button);
        if(level < 2){
        	bankInitializationButton.setText("???");
        	bankInitializationButton.setGravity(Gravity.CENTER);
        	bankInitializationButton.setEnabled(false);
        }
        else{
	        bankInitializationButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                Intent intent = new Intent(context, ArticleActivity.class);
	                intent.putExtra("EXTRA_ARTICLE_NAME", "bank_initialization");
	                startActivity(intent);
	            }
	        });
        }
    }
}
