package com.jeffmeyerson.moonstocks.activities;

import com.jeffmeyerson.moonstocks.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class NewsStandActivity extends Activity {

    private Context context = this;

    private Button lunarMarketOpensButton;
    private Button carefulWithTheMoonButton;
    private Button buyStockNotGlobusButton;
    private Button freezeAndThawButton;
    private Button bankInitializationButton;

    private MediaPlayer mp;
    private long scrollTime = 20000;
    private long scrollTimeInterval = 50;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_stand);

        mp = MediaPlayer.create(this, R.raw.austin_hambrick);
        mp.setLooping(true);

        // Set up the scrolling stock ticker at the top.
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.stock_scroller);    
        final TextView tv = (TextView) findViewById(R.id.scroll_text);
        scrollRight(hsv, tv);

        // Set up buttons for the articles.
        lunarMarketOpensButton = (Button) findViewById(R.id.lunar_market_opens_button);
        carefulWithTheMoonButton = (Button) findViewById(R.id.careful_with_the_moon_button);
        buyStockNotGlobusButton = (Button) findViewById(R.id.buy_stock_not_globus_button);
        freezeAndThawButton = (Button) findViewById(R.id.freeze_and_thaw_button);
        bankInitializationButton = (Button) findViewById(R.id.bank_initialization_button);

        
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

        freezeAndThawButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "freeze_and_thaw");
                startActivity(intent);
            }
        });
        bankInitializationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("EXTRA_ARTICLE_NAME", "bank_initialization");
                startActivity(intent);
            }
        });
    }

    public void scrollRight(final HorizontalScrollView h, final TextView tv){
        new CountDownTimer(scrollTime, scrollTimeInterval) { 

            public void onTick(long millisUntilFinished) {

                // Disable Scrolling by setting up an OnTouchListener to do nothing
                h.setOnTouchListener( new OnTouchListener(){ 
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true; 
                    }
                });

                int pos = (int) (1.0 * (scrollTime - millisUntilFinished) / scrollTime * (tv.getWidth() - h.getWidth()));
                h.scrollTo(pos, 0);
            } 

            public void onFinish() {
                h.scrollTo(0, 0);
                scrollRight(h, tv);
            } 
         }.start(); 
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
