package com.jeffmeyerson.moonstocks.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Stock;

/**
 * A base class that other MoonStocks activities can extend from. It fills in
 * the ActionBar with the standard options if you want it, and handles putting
 * the correct Intent extras in place for transitioning to the various other
 * Activities.
 * 
 * It also includes some game related constants.
 * 
 * It also handles a MediaPlayer for you. In your OnCreate method, you can call
 * play(id) and MoonActivity will start playing a song with that id, and handle
 * pauses and suspends appropriately.
 * 
 * Finally, it provides some useful utility functions like getCompanies.
 * 
 * @author josh
 * 
 */
public abstract class MoonActivity extends Activity {

	static Handler mHandler = new Handler();
	static boolean isRunning = false;

	// Map of ticker names to their companies
	public static HashMap<String, Company> companyMap = new HashMap<String, Company>();

	// Companies sorted in a consistent fashion
	public static ArrayList<Company> companyList = new ArrayList<Company>();

	// Constants
	public static final int STARTING_MONEY = 5000;
	protected static final String PERSISTENCE_FILE = "moonstocks";

	// The amount of time (ms) elapsed since the player started the game.
	// TODO: persist this
	static int globalTime = 1000;

	// There is only one player
	static Player player;

	// Media player data. Hidden from children.
	private MediaPlayer mp;
	private int music_id = 0;
	
	static boolean start = true;

	// Persistence related things **************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("class", "In MoonActivity");

		if (companyMap.size() == 0)
			loadCompanies();

		if (!isRunning) {
			Runnable timeFlux = new Runnable() {
				public void run() {

					// Put this function on the message queue
					mHandler.postDelayed(this, 1000);

					// Move to the next time interval
					globalTime += 1000;
				}
			};
			// Begin running the function
			mHandler.postDelayed(timeFlux, 1000);
		}

		isRunning = true;

		SharedPreferences mPrefs = getSharedPreferences("moonstocks_prefs",
				MODE_PRIVATE);
		int size = mPrefs.getInt("fileSize", 0);
		Log.d("size", "size of file: " + size);
		// Read the player from persistence if necessary
		if (player != null && size > 0) {
		    Log.d("exist" , "Exisiting Player");
			FileInputStream fin;
			byte[] buffer = new byte[size];
			try {
				fin = openFileInput(PERSISTENCE_FILE);
				fin.read(buffer);
			} catch (FileNotFoundException e) {
				Log.d("errors", "player file not found");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("errors", "player file io exception");
				e.printStackTrace();
			}
			
		    player = (Player) Utility.deserialize(buffer);
			if (player == null) {
				Log.e("MoonActivity.onCreate",
						"Player could not be deserialized!");
			}
		} else {
		    Log.d("new","newPlayer!");
			player = new Player();
			player.setBalance(STARTING_MONEY);
			player.setName("Jeff");
			
			SharedPreferences.Editor ed = mPrefs.edit();
			
			try {
	            Log.d("fileError", "writing file");
	            FileOutputStream fos = openFileOutput(PERSISTENCE_FILE,
	                    Context.MODE_PRIVATE);
	            fos.write(Utility.serialize(player));
	            size = Utility.serialize(player).length;
	            Log.d("fileError", "buffer size in write: " + size);
	            ed.putInt("fileSize", size);
	            ed.commit();
	            fos.close();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            Log.d("fileError", "writing file not found");
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            Log.d("fileError", "writing IO exception");
	            e.printStackTrace();
	        }

		}

	}

	// ActionBar related things ****************************************

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
			player = new Player();
			player.setBalance(STARTING_MONEY);
			player.setName("Jeff");
			getSharedPreferences("moonstocks_prefs",
	                MODE_PRIVATE).edit().clear().commit();
			deleteFile(PERSISTENCE_FILE);
			Intent intent = new Intent(this, SystemDetailsActivity.class);
            intent.putExtra("player", Utility.serialize(player));
            startActivity(intent);
			return true;
		} else if (id == R.id.menu_news) {
			Intent intent = new Intent(this, NewsActivity.class);
			intent.putExtra("player", Utility.serialize(player));
			startActivity(intent);
			// This is kind of a hack to make the ActionBar seem like it doesn't
			// do anything when you try and go into the activity you're already
			// in.
			// The correct solution would be to disable the button on the
			// actionbar.
			if (this instanceof NewsActivity) {
				overridePendingTransition(0, 0);
			} else {
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
			return true;
		} else if (id == R.id.menu_system_details) {
			Intent intent = new Intent(this, SystemDetailsActivity.class);
			intent.putExtra("player", Utility.serialize(player));
			startActivity(intent);
			if (this instanceof SystemDetailsActivity) {
				overridePendingTransition(0, 0);
			} else {
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
			return true;
		} else if (id == R.id.menu_stock_market) {
		    
			Intent intent = new Intent(this, MarketActivity.class);
			intent.putExtra("player", Utility.serialize(player));
			startActivity(intent);
			//Log.d("stocksOwned", "BANK in MoonActivity: " + player.getSharesOwned("BANK"));
			if (this instanceof MarketActivity) {
				overridePendingTransition(0, 0);
			} else {
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
			return true;
		}

		return false;
	}

	// MediaPlayer related things *************************************

	protected void play(int music_id) {
		this.music_id = music_id;
		if (mp != null) {
			// TODO: handle killing the old MP
		}
		mp = MediaPlayer.create(this, music_id);
		mp.setLooping(true);
		mp.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if (music_id == 0) {
			return;
		}

		// Start playing music
		if (mp == null) {
			mp = MediaPlayer.create(this, music_id);
			mp.setLooping(true);
		}

		mp.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mp != null) {
			mp.pause();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mp != null) {
			mp.release();
			mp = null;
		}
	}

	/**
	 * Read in the list of companies from companies.xml.
	 */
	protected void loadCompanies() {
		String[] companyStrings = getResources().getStringArray(
				R.array.companies);

		for (String companyString : companyStrings) {
			String[] companyArr = companyString.split(" ");
			String tickerName = companyArr[0];
			String name = companyArr[1];
			for (int i = 2; i < companyArr.length; i++)
				name += " " + companyArr[i];
			InputStream inputStream = null;
			if (tickerName.equals("EVIL")) {
				inputStream = this.getResources().openRawResource(
						R.raw.evil_vals);
			} else if (tickerName.equals("BDST")) {
				inputStream = this.getResources().openRawResource(
						R.raw.bdst_vals);
			} else if (tickerName.equals("WMC")) {
				inputStream = this.getResources().openRawResource(
						R.raw.wmc_vals);
			} else if (tickerName.equals("PAR")) {
				inputStream = this.getResources().openRawResource(
						R.raw.par_vals);
			}
			else if (tickerName.equals("BANK")) {
				inputStream = this.getResources().openRawResource(
						R.raw.bank_vals);
			}

			Stock stock = new Stock(inputStream);
			Company company = new Company(tickerName, name, stock);
			companyList.add(company);
			companyMap.put(tickerName, company);
		}
	}

	protected int checkLevel() {
		return (int) (player.getBalance() / STARTING_MONEY);
	}

	public static int getTime() {
		// TODO Auto-generated method stub
		return globalTime;
	}
}
