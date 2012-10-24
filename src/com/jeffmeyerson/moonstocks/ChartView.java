package com.jeffmeyerson.moonstocks;

import java.util.ArrayList;
import java.util.List;

import plain_java.ChartFrame;
import plain_java.DataPoint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

class ChartView extends View {

	final float scale = 30;// getWidth() / 20;
	private Paint paint;

	private ChartFrame currentFrame;

	public void setCurrentFrame(ChartFrame frame) {
		currentFrame = frame;
	}

	void initialize() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		if (currentFrame == null) {
			currentFrame = new ChartFrame();
		} else
			currentFrame.update();
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

		if (currentFrame.getNextFrame() != null) {
			currentFrame = currentFrame.getNextFrame();
		}

		// Get the list of dataPoints
		List<DataPoint> dataPoints = currentFrame.getDataPoints();

		// Points to be drawn on the canvas
		ArrayList<Integer> integerPoints = new ArrayList<Integer>();

		// Convert the DataPoints to rounded Integers
		for (int i = 0; i < dataPoints.size(); i++) {
			double pricePoint = dataPoints.get(i).getPrice() * scale;
			Integer roundedPrice = new Integer((int) (pricePoint / 1));
			integerPoints.add(roundedPrice);
		}
		paint.setStrokeWidth(3);

		for (int i = 1; i < integerPoints.size(); i++) {
			if (integerPoints.get(i) < integerPoints.get(i - 1)) {
				paint.setColor(Color.GREEN);
			} else {
				paint.setColor(Color.RED);
			}
			canvas.drawLine((i - 1) * scale, integerPoints.get(i - 1), i
					* scale, integerPoints.get(i), paint);
		}
	}
}