package com.jeffmeyerson.moonstocks.activities;

import java.util.LinkedList;
import java.util.List;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.pojos.CompanyModel;
import com.jeffmeyerson.moonstocks.pojos.Player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Context context = this;

	// The amount of money the player has upon beginning a new game
	private final int STARTING_MONEY = 1000;

	// Plays launchpad music
	private MediaPlayer mp;

	// The object representing the person playing the game
	private Player player;

    // Navigation buttons
    // TODO: switch to using ActionBar for navigation between activities
	private Button newsStandButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create CompanyModels
		List<CompanyModel> companyModels = getCompanyModels();

        // Start playing music
		mp = MediaPlayer.create(this, R.raw.main_menu);
		mp.setLooping(true);

		// Initialize news button
		newsStandButton = (Button) findViewById(R.id.newsStandButton);
		
		// Check for a persisted player
		Bundle extras = this.getIntent().getExtras();
		if (extras != null && extras.containsKey("EXTRA_PLAYER")) {
			extras.get("EXTRA_PLAYER");
		} else {
			player = new Player();
			player.setBalance(STARTING_MONEY);
			player.setName("Jeff");
		}

		newsStandButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsStandActivity.class);
				startActivity(intent);
			}

		});

        // Iterate through the companies on the market and add a row to the table for each one.
        TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
        for (final CompanyModel cm : companyModels) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // Add a button to take the player to the StockActivity for that company.
            Button companyButton = new Button(this);
            companyButton.setText(cm.getTickerName());
            companyButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, StockActivity.class);
                    intent.putExtra("EXTRA_TICKER_ID", cm.getTickerName());
                    startActivity(intent);
                }
            });
            row.addView(companyButton);

            TextView stockPrice = new TextView(this);
            stockPrice.setText("$7");   // TODO: hardcode this for now until stock prices are tracked
            stockPrice.setGravity(Gravity.CENTER);
            stockPrice.setTextAppearance(this, android.R.style.TextAppearance_Large);
            row.addView(stockPrice);

            TextView sharesOwned = new TextView(this);
            sharesOwned.setText("1");
            // TODO: this crashes the program for some reason
            //sharesOwned.setText(player.getSharesOwned(cm.getTickerName()));
            sharesOwned.setGravity(Gravity.CENTER);
            sharesOwned.setTextAppearance(this, android.R.style.TextAppearance_Large);
            row.addView(sharesOwned);

            marketTable.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

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