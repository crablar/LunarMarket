package com.jeffmeyerson.moonstocks.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
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

	public TimerView(Context context, int time_ms) {
		super(context);
	}

	// Convert to seconds, then set
	public void setTime(int time) {
		super.setText("" + time / 1000.0);
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

}
