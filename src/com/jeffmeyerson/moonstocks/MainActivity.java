                                                                     
                                                                     
                                                                     
                                             
package com.jeffmeyerson.moonstocks;

import java.util.HashMap;
import java.util.LinkedList;

import plain_java.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	
	public final static String EXTRA_STOCK_ID = "com.jeffmeyerson.moonstocks.STOCK_ID";
	public final static String EXTRA_PLAYER = "com.jeffmeyerson.moonstocks.plain_java.Player";
	
	private static final String TAG = "Debugging";
	
	private Context context = this;
	
	// The amount of money the player has upon beginning a new game
	private final int STARTING_MONEY = 1000;
	
	// Plays launchpad music
	private MediaPlayer mp;
//	private SoundPool mSounds;
//	private HashMap<Integer, Integer> mSoundIDMap;
	
	// The object representing the person playing the game
	private Player player;
	
	// Buttons for companies
	private Button aaplButton;
	private Button googButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d(TAG, "in onCreate");
        
//        // Create CompanyModels
//        LinkedList<CompanyModel> companyModels = getCompanyModels();
//        MainActivityButtonManager mainActivityButtonManager = new MainActivityButtonManager(companyModels);
//        
        // Initialize player
        player = new Player(STARTING_MONEY, "Jeff");
        
        // Play launch pad music
//        MediaPlayer mp = MediaPlayer.create(this, R.raw.main_menu);
//                
//        mp.start();
        
        mp = MediaPlayer.create(this, R.raw.main_menu);
        mp.setLooping(true);
//        mp.create(this, R.raw.main_menu);
//        mp.start();
        
//        createSoundPool();
        
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
	
	@Override
	protected void onResume() {		
		super.onResume();
		Log.d(TAG, "in onResume");
		mp.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();		
		Log.d(TAG, "in onPause");
		mp.pause();

	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(TAG, "in onDestroy");
		mp.release();
	}

	/**
	 * Create a list of CompanyModels as defined by the companies.xml file.
	 * 
	 * @return
	 */
	private LinkedList<CompanyModel> getCompanyModels() {
		LinkedList<CompanyModel> result = new LinkedList<CompanyModel>();
		Resources res = getResources();
		String[] companyStrings = res.getStringArray(R.array.companies);
		for(String companyString : companyStrings){
			String[] companyArr = companyString.split(" ");
			String tickerName = companyArr[0];
			String companyName = companyArr[1];
			CompanyModel companyModel = new CompanyModel(tickerName, companyName);
			result.add(companyModel);
		}
		return result;
	}
}