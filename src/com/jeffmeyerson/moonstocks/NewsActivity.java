package com.jeffmeyerson.moonstocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class NewsActivity extends Activity {

	private Context context = this;
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
			mp = MediaPlayer.create(this, R.raw.ikea_zombies);
		}
		if (articleName.equals("careful_with_the_moon")) {
			inputStream = this.getResources().openRawResource(
					R.raw.careful_with_the_moon);
			mp = MediaPlayer.create(this, R.raw.occupy_wall_suite);
		}
		if (articleName.equals("buy_stock_not_globus")) {
			inputStream = this.getResources().openRawResource(
					R.raw.buy_stock_not_globus);
			mp = MediaPlayer.create(this, R.raw.dotslashgo);
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

}
