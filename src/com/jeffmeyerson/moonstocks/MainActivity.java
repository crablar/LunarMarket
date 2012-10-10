package com.jeffmeyerson.moonstocks;

import plain_java.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	
	public final static String EXTRA_STOCK_ID = "com.jeffmeyerson.moonstocks.STOCK_ID";
	public final static String EXTRA_PLAYER = "com.jeffmeyerson.moonstocks.plain_java.Player";

	
	private Context context = this;
	
	// The amount of money the player has upon beginning a new game
	private final int STARTING_MONEY = 1000;
	
	// Plays launchpad music
	private MediaPlayer mp;
	
	// The object representing the person playing the game
	private Player player;
	
	// Buttons for companies
	private Button aaplButton;
	private Button googButton;
	
	    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Play launch pad music
        MediaPlayer mp = MediaPlayer.create(this, R.raw.main_menu);
        mp.start();
        
        // Initialize player
        player = new Player(STARTING_MONEY, "Jeff");
        
     	// Initialize buttons
        aaplButton = (Button) findViewById(R.id.aaplButton);
        googButton = (Button) findViewById(R.id.googButton);
        
        // Add onclick listeners to existing buttons
        aaplButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(context, StockActivity.class);
        		intent.putExtra(EXTRA_STOCK_ID, "AAPL");
        		intent.putExtra(EXTRA_PLAYER, "AAPL");
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
    }
    
    

}