package com.jeffmeyerson.moonstocks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.views.TickerView;

public class NewsActivity extends MoonActivity {

    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // play a lovely little tune
        play(R.raw.evil);

        // Set up the scrolling stock ticker at the top.
        TickerView tickerView = (TickerView) findViewById(R.id.stock_scroller);

        tickerView.setCompanies(getCompanies());
        tickerView.scroll();

        // Set up buttons for the articles.
        // TODO: this should totally be programmatic
        Button lunarMarketOpensButton = (Button) findViewById(R.id.lunar_market_opens_button);
        lunarMarketOpensButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "lunar_market_opens");
                startActivity(intent);
            }
        });

        Button carefulWithTheMoonButton = (Button) findViewById(R.id.careful_with_the_moon_button);
        carefulWithTheMoonButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "careful_with_the_moon");
                startActivity(intent);
            }
        });

        Button buyStockNotGlobusButton = (Button) findViewById(R.id.buy_stock_not_globus_button);
        buyStockNotGlobusButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "buy_stock_not_globus");
                startActivity(intent);
            }
        });

        Button freezeAndThawButton = (Button) findViewById(R.id.freeze_and_thaw_button);
        freezeAndThawButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "freeze_and_thaw");
                startActivity(intent);
            }
        });

        Button bankInitializationButton = (Button) findViewById(R.id.bank_initialization_button);
        bankInitializationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "bank_initialization");
                startActivity(intent);
            }
        });
    }
}
