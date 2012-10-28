package com.jeffmeyerson.moonstocks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewsStandActivity extends Activity {

	private Context context = this;

	private Button lunarMarketOpensButton;
	private Button carefulWithTheMoonButton;
	private Button buyStockNotGlobusButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_stand);
		System.out.println("HELLO");

		lunarMarketOpensButton = (Button) findViewById(R.id.lunar_market_opens_button);
		carefulWithTheMoonButton = (Button) findViewById(R.id.careful_with_the_moon_button);
		buyStockNotGlobusButton = (Button) findViewById(R.id.buy_stock_not_globus_button);
		
		System.out.println("HELLO");
		
		lunarMarketOpensButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				intent.putExtra("EXTRA_ARTICLE_NAME", "lunar_market_opens");
				startActivity(intent);
			}
		});
		carefulWithTheMoonButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				intent.putExtra("EXTRA_ARTICLE_NAME", "careful_with_the_moon");
				startActivity(intent);
			}
		});
		buyStockNotGlobusButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, NewsActivity.class);
				intent.putExtra("EXTRA_ARTICLE_NAME", "buy_stock_not_globus");
				startActivity(intent);
			}
		});
	}
}
