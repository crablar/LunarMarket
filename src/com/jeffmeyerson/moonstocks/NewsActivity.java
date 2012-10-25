package com.jeffmeyerson.moonstocks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
//		System.out.println("Hello");
//
//		TextView articleTextView = (TextView) findViewById(R.id.article_text);
//
//		
//		InputStream inputStream = this.getResources().openRawResource(
//				R.raw.lunar_market_opens);
//
//		// Create a BufferedReader for the InputStream
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//				inputStream));
//
//		String line = null;
//		try {
//			while (br.readLine() != null) {
//				line = br.readLine();
//				articleTextView.append(line);
//				articleTextView.append("\n");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
