package com.jeffmeyerson.moonstocks.activities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Protocol;
import com.jeffmeyerson.moonstocks.pojos.Stock;
import com.jeffmeyerson.moonstocks.views.TimerView;

public class SystemDetailsActivity extends Activity {
	private Handler mHandler = new Handler();
	private Player player;
	private TextView playerNameView;
	private TextView balanceView;
	private TextView netProfitsView;
	private TextView protocolView;
	private TimerView timerView;
    private MediaPlayer mp;
    private int time;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_details);

	    mp = MediaPlayer.create(this, R.raw.withering_gaze);
        mp.setLooping(true);
		
        // Get the data from the Intent
        Bundle extras = getIntent().getExtras();
        
        time = extras.getInt("time");

        player = (Player) deserializeObject(extras.getByteArray("player"));

		playerNameView = (TextView) findViewById(R.id.playerText);

		balanceView = (TextView) findViewById(R.id.balanceText);

		netProfitsView = (TextView) findViewById(R.id.netProfitText);

		protocolView = (TextView) findViewById(R.id.protocolText);
		
		timerView = (TimerView) findViewById(R.id.timerView);

		double netProfits = player.getBalance() - 5000;
		
		//Fill TextViews with information
		playerNameView.setText("TradeBot Class xF4D2: " + player.getName());

		balanceView.setText("Balance: $" + + player.getBalance());

		netProfitsView.setText("Net profits: $" + netProfits);

		protocolView.setText(Protocol.getProtocolVerbose());
		
		Runnable timeFlux = new Runnable() {
			public void run() {

				timerView.setTime(time);

				// Put this function on the message queue
				mHandler.postDelayed(this, 1000);

				// Move to the next time interval
				time += 1000;
			}
		};

		
		// Begin running the function
		mHandler.postDelayed(timeFlux, 1000);
		
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("time", time);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void quitToMarket(View view) {
		onBackPressed();
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
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		player = (Player) deserializeObject(savedInstanceState
				.getByteArray("player"));

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
