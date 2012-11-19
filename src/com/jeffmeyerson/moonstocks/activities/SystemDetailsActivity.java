package com.jeffmeyerson.moonstocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Protocol;
import com.jeffmeyerson.moonstocks.views.TickerView;

public class SystemDetailsActivity extends MoonActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_details);

        // Set up the scrolling stock ticker at the top.
        TickerView tickerView = (TickerView) findViewById(R.id.stock_scroller);

        tickerView.scroll();
		
		play(R.raw.evil);

		// Get the data from the Intent
		// TODO: Move this to MoonActivity
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			if (extras.containsKey("player")) {
				player = (Player) Utility.deserialize(extras
						.getByteArray("player"));
			} else {
				Log.d("SystemDetailsActivity.onCreate",
						"intent extras lacked player field");
			}
		}

		if (player == null) {
			player = new Player();
		}

		// Fill TextViews with information
		TextView playerNameView = (TextView) findViewById(R.id.playerText);
		playerNameView.setText("TradeBot Class xF4D2: " + player.getName());
		TextView balanceView = (TextView) findViewById(R.id.balanceText);
		balanceView.setText("Balance: $" + +player.getBalance());
		TextView netProfitsView = (TextView) findViewById(R.id.netProfitText);
		double netProfits = player.getBalance() - MarketActivity.STARTING_MONEY;
		netProfitsView.setText("Net profits: $" + netProfits);
		TextView protocolView = (TextView) findViewById(R.id.protocolText);
		protocolView.setText(Protocol.getProtocolVerbose());

	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void quitToMarket(View view) {
		onBackPressed();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		player = (Player) Utility.deserialize(savedInstanceState
				.getByteArray("player"));

	}
}
