package com.jeffmeyerson.moonstocks.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jeffmeyerson.moonstocks.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class NewsActivity extends Activity {

	private MediaPlayer mp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		TextView articleTextView = (TextView) findViewById(R.id.article_text);
		articleTextView.setMovementMethod(new ScrollingMovementMethod());

		mp = null;

		// Get the data from the Intent
		Bundle extras = getIntent().getExtras();
		String articleName = "";
		if (extras != null) {
			articleName = extras.getString("EXTRA_ARTICLE_NAME");
		}

		InputStream inputStream = null;
		if (articleName.equals("lunar_market_opens")) {
			inputStream = this.getResources().openRawResource(
					R.raw.lunar_market_opens);
			mp = MediaPlayer.create(this, R.raw.evil);
		}
		if (articleName.equals("careful_with_the_moon")) {
			inputStream = this.getResources().openRawResource(
					R.raw.careful_with_the_moon);
			mp = MediaPlayer.create(this, R.raw.evil);
		}
		if (articleName.equals("buy_stock_not_globus")) {
			inputStream = this.getResources().openRawResource(
					R.raw.buy_stock_not_globus);
			mp = MediaPlayer.create(this, R.raw.evil);
		}
		if (articleName.equals("freeze_and_thaw")) {
			inputStream = this.getResources().openRawResource(
					R.raw.freeze_and_thaw);
			mp = MediaPlayer.create(this, R.raw.evil);
		}
		if (articleName.equals("bank_initialization")) {
			inputStream = this.getResources().openRawResource(
					R.raw.bank_initialization);
			mp = MediaPlayer.create(this, R.raw.evil);
		}
		
		mp.setLooping(true);

		// Create a BufferedReader for the InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));

		String line = "";
		try {
			line = br.readLine();
			while (line != null) {
				System.out.println(line);
				articleTextView.append(line);
				articleTextView.append("\n");
				line = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
            return true;
        } else if (id == R.id.menu_news) {
            Intent intent = new Intent(this, NewsStandActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_system_details) {
            Intent intent = new Intent(this, SystemDetailsActivity.class);
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
