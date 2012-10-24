package com.jeffmeyerson.moonstocks;

import java.util.LinkedList;
import java.util.List;

import plain_java.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public final static String EXTRA_PLAYER = "com.jeffmeyerson.moonstocks.plain_java.Player";

	private static final String TAG = "Debugging";

	private Context context = this;

	// The amount of money the player has upon beginning a new game
	private final int STARTING_MONEY = 1000;

	// Plays launchpad music
	private MediaPlayer mp;

	// The object representing the person playing the game
	private Player player;

	// Buttons for companies
	private Button evilButton;
	private Button bdstButton;
	private Button wmcButton;
	private Button newsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create CompanyModels
		List<CompanyModel> companyModels = getCompanyModels();

		// Initialize player
		player = new Player(STARTING_MONEY, "Jeff");

		mp = MediaPlayer.create(this, R.raw.main_menu);
		mp.setLooping(true);

		// Initialize company buttons
		evilButton = (Button) findViewById(R.id.evilButton);
		bdstButton = (Button) findViewById(R.id.bdstButton);
		wmcButton = (Button) findViewById(R.id.wmcButton);
		
		// Initialize news button
		newsButton = (Button) findViewById(R.id.newsButton);

		// Add onclick listeners to existing buttons
		evilButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, StockActivity.class);
				intent.putExtra("EXTRA_TICKER_ID", "EVIL");
				startActivity(intent);
			}
		});
		// Add onclick listeners to existing buttons
		bdstButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, StockActivity.class);
				intent.putExtra("EXTRA_TICKER_ID", "BDST");
				startActivity(intent);
			}
		});
		// Add onclick listeners to existing buttons
		wmcButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, StockActivity.class);
				intent.putExtra("EXTRA_TICKER_ID", "WMC");
				startActivity(intent);
			}
		});
		
		newsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				startActivity(intent);
			}
			
		});

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

		for (String companyString : companyStrings) {
			String[] companyArr = companyString.split(" ");
			String tickerName = companyArr[0];
			String companyName = companyArr[1];
			CompanyModel companyModel = new CompanyModel(tickerName,
					companyName);
			result.add(companyModel);
		}
		return result;
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

}