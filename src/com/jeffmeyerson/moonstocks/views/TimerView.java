package com.jeffmeyerson.moonstocks.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A global timer that counts down. In SystemDetailsActivity, it will always be
 * visible. In the other Activities it will become visible every so often to let
 * the player know how much time is remaining.
 * 
 * @author jeffreymeyerson
 * 
 */

public class TimerView extends TextView {

	private String time;
	
	public TimerView(Context context, int time_ms) {
		super(context);
	}

	// Convert to minutes:seconds, then set
	public void setTime(int time) {
		if(time > 600000)
			this.setTextColor(Color.RED);
		int ms_remaining = 3600000 - time;
		int sec_remaining = (ms_remaining / 1000) % 60;
		int min_remaining = ms_remaining / 60000;
		this.time = min_remaining + ":" + sec_remaining;
	}

	public TimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimerView(Context context) {
		super(context);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		super.setText(time);
	}

}
