package com.jeffmeyerson.moonstocks.views;

import java.io.InputStream;
import java.util.List;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Stock;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * Used for the scrolling stock ticker that appears across the top of the screen.
 * @author josh
 *
 */
public class TickerView extends HorizontalScrollView {

    // Constants.
    private final long SCROLL_TIME = 20000;
    private final long SCROLL_TIME_INTERVAL = 50;

    TextView text;

    public TickerView(Context context) {
        super(context);
        initialize(context);
    }

    public TickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public TickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        text = new TextView(context);
        this.addView(text);
    }

    public void setCompanies(List<Company> companies) {
        InputStream inputStream = null;
        String marketScroll = "";
        for (final Company company : companies) {
            // TODO: Make programmatic
            // Put the raw text file into an InputStream
            if(company.getTicker().equals("EVIL")){
                inputStream = this.getResources().openRawResource(R.raw.evil_vals);
            }
            else if(company.getTicker().equals("BDST")){
                inputStream = this.getResources().openRawResource(R.raw.bdst_vals);
            }
            else if(company.getTicker().equals("WMC")){
                inputStream = this.getResources().openRawResource(R.raw.wmc_vals);
            }
            marketScroll += company.getTicker();
            marketScroll += " " + getStockPrice(inputStream, 0);
            marketScroll += "     ";
        }
        text.setText(marketScroll + marketScroll + marketScroll);
    }

    // TODO: this should be refactored to not need to create a new stock every time
    private String getStockPrice(InputStream is, int time) {
        String update = "";
        Stock stock = new Stock(is);

        if (time == 0) {
            return "0.0";
        }

        Double priceNew = stock.getUninterpolatedPrice(time);
        Double priceOld = stock.getUninterpolatedPrice(time);
        Double change = priceNew - priceOld;

        if(change > 0){
            update += "+";
        }
        update += change;
        return update;
    }

    public void scroll() {
        new CountDownTimer(SCROLL_TIME, SCROLL_TIME_INTERVAL) { 

            public void onTick(long millisUntilFinished) {

                // Disable Scrolling by setting up an OnTouchListener to do nothing
                setOnTouchListener( new OnTouchListener(){ 
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true; 
                    }
                });

                int pos = (int) (1.0 * (SCROLL_TIME - millisUntilFinished) / SCROLL_TIME * (text.getWidth() - getWidth()));
                scrollTo(pos, 0);
            } 

            public void onFinish() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                         scrollTo(0, 0);
                         scroll();
                    }
                }, 2000);
            } 
         }.start(); 
    }
}
