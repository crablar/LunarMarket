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

    private TextView text;

    // This time is local only to the ticker. Not synchronized with global time currently.
    private int time;
    private List<Company> companies;

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
        time = 1;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
        updateText();
    }

    private void updateText() {
        time++;
        String newText = "";
        InputStream is = null;
        for (Company company : companies) {
            if (company.getTicker().equals("EVIL")) {
                is = this.getResources().openRawResource(R.raw.evil_vals);
            } else if (company.getTicker().equals("BDST")) {
                is = this.getResources().openRawResource(R.raw.bdst_vals);
            } else if (company.getTicker().equals("WMC")) {
                is = this.getResources().openRawResource(R.raw.wmc_vals);
            }
            newText += company.getTicker();
            newText += " " + getStockPrice(is);
            newText += "     ";
        }
        text.setText(newText + newText + newText);
    }

    // TODO: this should be refactored to not need to create a new stock every time
    private String getStockPrice(InputStream is) {
        if (time == 0) {
            return "--";
        }

        String update = "";
        Stock stock = new Stock(is);

        Double priceNew = stock.getUninterpolatedPrice(time);
        Double priceOld = stock.getUninterpolatedPrice(time-1);
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
                         updateText();
                         scrollTo(0, 0);
                         scroll();
                    }
                }, 2000);
            } 
         }.start(); 
    }
}
