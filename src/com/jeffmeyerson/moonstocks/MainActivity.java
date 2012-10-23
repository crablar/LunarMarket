                                                                     
                                                                     
                                                                     
                                             
package com.jeffmeyerson.moonstocks;

import java.util.LinkedList;
import java.util.List;

import plain_java.MainActivityButtonManager;
import plain_java.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
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
	private Button evilButton;
	private Button bdstButton;
	private Button wmcButton;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        // Create CompanyModels
        List<CompanyModel> companyModels = getCompanyModels();
        Log.d(TAG, "we are alive");

        // Create the MainActivityButtonManager (currently unused)
        //MainActivityButtonManager mainActivityButtonManager = new MainActivityButtonManager(companyModels);
        
        // Initialize player
        player = new Player(STARTING_MONEY, "Jeff");
        Log.d(TAG, "we are alive");

        mp = MediaPlayer.create(this, R.raw.main_menu);
        mp.setLooping(true);
        
        Log.d(TAG, "we are alive");
        
     	// Initialize buttons
        // TODO: make this programmatic
        evilButton = (Button) findViewById(R.id.evilButton);
        bdstButton = (Button) findViewById(R.id.bdstButton);
        wmcButton = (Button) findViewById(R.id.wmcButton);

        
        // Add onclick listeners to existing buttons
        evilButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(context, StockActivity.class);
        		intent.putExtra(EXTRA_STOCK_ID, "AAPL");
        		intent.putExtra(EXTRA_PLAYER, "AAPL");
        		startActivity(intent);
        	}
        });
        bdstButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(context, StockActivity.class);
        		intent.putExtra(EXTRA_STOCK_ID, "GOOG");
        		startActivity(intent);
        	}
        });
        wmcButton.setOnClickListener(new OnClickListener() {
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

	/**
	 * Create a list of CompanyModels as defined by the companies.xml file.
	 * 
	 * @return
	 */
	private List<CompanyModel> getCompanyModels() {
		List<CompanyModel> result = new LinkedList<CompanyModel>();
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