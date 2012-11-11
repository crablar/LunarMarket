package com.jeffmeyerson.moonstocks.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Protocol;
import com.jeffmeyerson.moonstocks.views.TimerView;

public class SystemDetailsActivity extends Activity {
    private Handler mHandler = new Handler();
    private MediaPlayer mp;

    // data
    private Player player;
    private int time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_details);

        mp = MediaPlayer.create(this, R.raw.evil);
        mp.setLooping(true);

        // Get the data from the Intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.containsKey("time")) {
                time = extras.getInt("time");
            } else {
                Log.d("SystemDetailsActivity.onCreate", "intent extras lacked time field");
            }

            if (extras.containsKey("player")) {
                player = (Player) Utility.deserialize(extras.getByteArray("player"));
            } else {
                Log.d("SystemDetailsActivity.onCreate", "intent extras lacked player field");
            }
        }

        if (player == null) {
            player = new Player();
        }

        //Fill TextViews with information
        TextView playerNameView = (TextView) findViewById(R.id.playerText);
        playerNameView.setText("TradeBot Class xF4D2: " + player.getName());

        TextView balanceView = (TextView) findViewById(R.id.balanceText);
        balanceView.setText("Balance: $" + + player.getBalance());

        TextView netProfitsView = (TextView) findViewById(R.id.netProfitText);
        double netProfits = player.getBalance() - MainActivity.STARTING_MONEY;
        netProfitsView.setText("Net profits: $" + netProfits);

        TextView protocolView = (TextView) findViewById(R.id.protocolText);
        protocolView.setText(Protocol.getProtocolVerbose());

        Runnable timeFlux = new Runnable() {
            public void run() {

                TimerView timerView = (TimerView) findViewById(R.id.timerView);
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player = (Player) Utility.deserialize(savedInstanceState.getByteArray("player"));

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
            player.setBalance(MainActivity.STARTING_MONEY);
            player.setName("Jeff");
            return true;
        } else if (id == R.id.menu_news) {
            Intent intent = new Intent(this, NewsStandActivity.class);
            intent.putExtra("time", time);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_system_details) {
            Intent intent = new Intent(this, SystemDetailsActivity.class);
            intent.putExtra("time", time);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_stock_market) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

}
