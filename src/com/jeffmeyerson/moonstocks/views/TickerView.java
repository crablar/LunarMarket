package com.jeffmeyerson.moonstocks.views;

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

    public void setText(String s) {
        text.setText(s);
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
