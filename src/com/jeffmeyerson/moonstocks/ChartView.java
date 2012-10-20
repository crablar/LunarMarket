package com.jeffmeyerson.moonstocks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

class ChartView extends View {
	
	final float scale = 20;//getWidth() / 20;
	private Paint paint;
	
	void initialize() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	
	public ChartView(Context context) {
		super(context);
		initialize();
	}

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public ChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// some sample points. in the future, this should come from the music data.
		List<Integer> points = new ArrayList<Integer>();
		points.add(5);
		points.add(3);
		points.add(7);
		points.add(2);
		
		paint.setStrokeWidth(3);
		
		for (int i = 1; i < points.size(); i++) {
			if (points.get(i) < points.get(i-1)) {
				paint.setColor(Color.GREEN);
			} else {
				paint.setColor(Color.RED);
			}
			canvas.drawLine((i-1) * scale, points.get(i-1) * scale, i * scale, points.get(i) * scale, paint);
		}
	}
}