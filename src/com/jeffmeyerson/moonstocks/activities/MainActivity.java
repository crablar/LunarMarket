package com.jeffmeyerson.moonstocks.activities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Stock;

public class MainActivity extends Activity {

	private Context context = this;

	// The amount of money the player has upon beginning a new game
	private final int STARTING_MONEY = 5000;
	
	// Plays launchpad music
	private MediaPlayer mp;

	// The object representing the person playing the game
	private Player player;

	// Navigation buttons
	// TODO: switch to using ActionBar for navigation between activities
	private Button newsStandButton;
	private Button systemDetailsButton;

	private int time;
	
	private int size;

	private String fileName = "mainactivity";

	private SharedPreferences mPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("Running", "onCreate");

		time = 0;

		mPrefs = getSharedPreferences("mainactivity_prefs", MODE_PRIVATE);

		size = mPrefs.getInt("fileSize", 0);

		// Initialize news button
		newsStandButton = (Button) findViewById(R.id.newsStandButton);

		// Initialize systemDetails button
		systemDetailsButton = (Button) findViewById(R.id.systemDetailsButton);

		// Check for a persisted player

		FileInputStream fin;
		byte[] buffer = new byte[size];
		try {
			Log.d("fileError", "reading file");
			fin = openFileInput(fileName);
			// InputStreamReader isReader = new InputStreamReader(fin);
			// Fill the buffer with data from file
			fin.read(buffer);
			Log.d("fileError", "read buffer size: " + buffer.length);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d("fileError", "read file not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("fileError", "read IO exception");
			e.printStackTrace();
		}

		if (size > 0) {
			player = (Player) deserializeObject(buffer);
			Log.d("playerinfo", "balance: " + player.getBalance());
			Log.d("playerinfo", "name: " + player.getName());
		} else {
			// Log.d("onCreate", "creating new player");
			player = new Player();
			player.setBalance(STARTING_MONEY);
			player.setName("Jeff");
		}

		// Load company data from XML.
		List<Company> companies = getCompanies();

		newsStandButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsStandActivity.class);
				intent.putExtra("time", time);
				startActivity(intent);
			}
		});
		
		systemDetailsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, SystemDetailsActivity.class);
				intent.putExtra("player", serializeObject(player));
				intent.putExtra("time", time);
				startActivity(intent);
			}
		});

		// Iterate through the companies on the market and add a row to the
		// table for each one.
		TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
		for (final Company c : companies) {
			TableRow row = new TableRow(this);
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			// Add a button to take the player to the StockActivity for that
			// company.
			Button companyButton = new Button(this);
			companyButton.setText(c.getTicker());
			companyButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(context, StockActivity.class);
					intent.putExtra("EXTRA_TICKER_ID", c.getTicker());
					intent.putExtra("player", serializeObject(player));
					startActivityForResult(intent, 1);
				}
			});
			row.addView(companyButton);

			TextView stockPrice = new TextView(this);
			stockPrice.setText("$" + c.getPrice());
			stockPrice.setGravity(Gravity.CENTER);
			stockPrice.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			row.addView(stockPrice);

			TextView sharesOwned = new TextView(this);
			sharesOwned.setText(String.valueOf(player.getSharesOwned(c
					.getTicker())));
			sharesOwned.setGravity(Gravity.CENTER);
			sharesOwned.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			row.addView(sharesOwned);

			marketTable.addView(row, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

	}

	/**
	 * Create a list of CompanyModels as defined by the companies.xml file.
	 * 
	 * @return
	 */
	private List<Company> getCompanies() {
		List<Company> result = new LinkedList<Company>();
		String[] companyStrings = getResources().getStringArray(
				R.array.companies);

		for (String companyString : companyStrings) {
			String[] companyArr = companyString.split(" ");
			String ticker = companyArr[0];
			String name = companyArr[1];
			Company company = new Company(ticker, name);
			result.add(company);
		}
		return result;
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
		} catch (IOException ioe) {
			Log.e("serializeObject", "error", ioe);

			return null;
		}
	}

	public static Object deserializeObject(byte[] b) {
		try {
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(b));
			Object object = in.readObject();
			in.close();

			return object;
		} catch (ClassNotFoundException cnfe) {
			Log.e("deserializeObject", "class not found error", cnfe);

			return null;
		} catch (IOException ioe) {
			Log.e("deserializeObject", "io error", ioe);

			return null;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putByteArray("player", serializeObject(player));
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		player = (Player) deserializeObject(savedInstanceState
				.getByteArray("player"));
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Start playing music
		if (mp == null) {
			mp = MediaPlayer.create(this, R.raw.main_menu);

			mp.setLooping(true);
		}

		mp.start();

		Log.d("Running", "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.pause();
		Log.d("Running", "onPause");
		update();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mp.release();
		mp = null;
		Log.d("Running", "onStop");
		update();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// mp.release();
		Log.d("running", "onDestroy");
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {

				updateTable(data);
				update();

			}

			if (resultCode == RESULT_CANCELED) {

				// Write your code on no result return

			}

		}// onAcrivityResult
	}

	private void update() {
		Log.d("Running", "update()");

		SharedPreferences.Editor ed = mPrefs.edit();

		try {
			Log.d("fileError", "writing file");
			FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(serializeObject(player));
			size = serializeObject(player).length;
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

	private void updateTable(Intent data) {
		TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);

		player = (Player) deserializeObject(data.getByteArrayExtra("player"));
		time = data.getExtras().getInt("time");

		InputStream inputStream = null;

		for (int i = 1; i <= getCompanies().size(); i++) {
			TableRow row = (TableRow) marketTable.getChildAt(i); // gets the row
			String company = (String) ((Button) row.getChildAt(0)).getText();

			// TODO: Make programmatic
			// Put the raw text file into an InputStream
			if (company.equals("EVIL")) {
				inputStream = this.getResources().openRawResource(
						R.raw.evil_vals);
			} else if (company.equals("BDST")) {
				inputStream = this.getResources().openRawResource(
						R.raw.bdst_vals);
			} else if (company.equals("WMC")) {
				inputStream = this.getResources().openRawResource(
						R.raw.wmc_vals);
			}

			Stock stock = new Stock(inputStream);
			((TextView) row.getChildAt(1)).setText("$"
					+ String.valueOf(stock.getPrice(data.getExtras().getInt(
							"time"))));
			((TextView) row.getChildAt(2)).setText(String.valueOf(player
					.getSharesOwned(company)));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reset_game:
			player = new Player();
			player.setBalance(STARTING_MONEY);
			player.setName("Jeff");
			return true;
		}
		return false;
	}

}