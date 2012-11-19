package com.jeffmeyerson.moonstocks.views;

import java.io.InputStream;
import java.util.List;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.activities.MoonActivity;
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

    private void updateText() {
        String newText = "";
        int time = MoonActivity.getTime();
        List<Company> companyList = MoonActivity.companyList;
        for (int i = 0; i < companyList.size(); i++) {
        	Company company = companyList.get(i);
        	Stock stock = company.getStock();
        	
            Double priceNew = stock.getPrice(time);
            Double priceOld = stock.getPrice(time-1000);
            Double priceChange = priceNew - priceOld;
        	
        	newText += company.getTicker() + " " + priceChange;
            newText += "     ";
        }
        text.setText(newText + newText + newText);
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
