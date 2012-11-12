package com.jeffmeyerson.moonstocks.activities;

import java.util.LinkedList;
import java.util.List;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Player;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * A base class that other MoonStocks activities can extend from.
 * It fills in the ActionBar with the standard options if you want it,
 * and handles putting the correct Intent extras in place for transitioning
 * to the various other Activities.
 * 
 * It also includes some game related constants.
 * 
 * It also handles a MediaPlayer for you. In your OnCreate method, you can
 * call play(id) and MoonActivity will start playing a song with that id,
 * and handle pauses and suspends appropriately.
 * 
 * Finally, it provides some useful utility functions like getCompanies.
 * @author josh
 *
 */
public abstract class MoonActivity extends Activity {

    // Constants
    public static final int STARTING_MONEY = 5000;

    // Game data exposed to inheriting classes
    protected Player player;
    protected int time;

    // Media player data. Hidden from children.
    private MediaPlayer mp;
    private int music_id = 0;

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
            return true;
        } else if (id == R.id.menu_news) {
            Intent intent = new Intent(this, NewsActivity.class);
            intent.putExtra("time", time);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_system_details) {
            Intent intent = new Intent(this, SystemDetailsActivity.class);
            intent.putExtra("player", Utility.serialize(player));
            intent.putExtra("time", time);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_stock_market) {
            Intent intent = new Intent(this, MarketActivity.class);
            startActivity(intent);
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start playing music
        if (mp == null && music_id != 0) {
            mp = MediaPlayer.create(this, music_id);
            mp.setLooping(true);
        }

        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.release();
        mp = null;
    }

    /**
     * Read in the list of companies from companies.xml.
     */
    protected List<Company> getCompanies() {
        List<Company> result = new LinkedList<Company>();
        String[] companyStrings = getResources().getStringArray(R.array.companies);

        for (String companyString : companyStrings) {
            String[] companyArr = companyString.split(" ");
            String ticker = companyArr[0];
            String name = companyArr[1];
            Company company = new Company(ticker, name);
            result.add(company);
        }
        return result;
    }
}
