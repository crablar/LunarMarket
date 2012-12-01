package com.jeffmeyerson.moonstocks.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jeffmeyerson.moonstocks.R;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class ArticleActivity extends MoonActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("class", "In ArticleActivity");
		setContentView(R.layout.activity_article);
		TextView articleTextView = (TextView) findViewById(R.id.article_text);
		articleTextView.setMovementMethod(new ScrollingMovementMethod());

		// Get the data from the Intent
		Bundle extras = getIntent().getExtras();
		String articleName = "";
		if (extras != null) {
			articleName = extras.getString("EXTRA_ARTICLE_NAME");
		}

		InputStream inputStream = null;
		if (articleName.equals("lunar_market_opens")) {
			inputStream = this.getResources().openRawResource(R.raw.lunar_market_opens);
			play(R.raw.evil);
		}
		if (articleName.equals("careful_with_the_moon")) {
			inputStream = this.getResources().openRawResource(R.raw.careful_with_the_moon);
			play(R.raw.evil);
		}
		if (articleName.equals("buy_stock_not_globus")) {
			inputStream = this.getResources().openRawResource(R.raw.buy_stock_not_globus);
			play(R.raw.evil);
		}
		if (articleName.equals("freeze_and_thaw")) {
			inputStream = this.getResources().openRawResource(R.raw.freeze_and_thaw);
			play(R.raw.evil);
		}
		if (articleName.equals("bank_initialization")) {
			inputStream = this.getResources().openRawResource(R.raw.bank_initialization);
			play(R.raw.evil);
		}

		// Create a BufferedReader for the InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

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

}
