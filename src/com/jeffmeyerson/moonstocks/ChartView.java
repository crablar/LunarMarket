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

	final float scale = 20;// getWidth() / 20;
	private Paint paint;

	private ChartFrame currentFrame;

	public void setCurrentFrame(ChartFrame frame){
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
		
		if (currentFrame.getNextFrame() != null){
			System.out.println("Frame before: " + currentFrame);
			System.out.println("Getting next frame...");
			currentFrame = currentFrame.getNextFrame();
			System.out.println("Frame after: " + currentFrame);
			System.out.println("The next frame will be " + currentFrame.getNextFrame());

		}
		
		Log.d(this.toString(), "Inside ChartView.  currentFrame: "
				+ currentFrame.toString());

		// Get the list of dataPoints
		List<DataPoint> dataPoints= currentFrame.getDataPoints();
		
		// Points to be drawn on the canvas
		ArrayList<Integer> integerPoints = new ArrayList<Integer>();
		
		// Convert the DataPoints to rounded Integers
		for(int i = 0; i < dataPoints.size(); i++){
			double pricePoint = dataPoints.get(i).getPrice();
			Integer roundedPrice = new Integer((int) (pricePoint / 1));
			integerPoints.add(roundedPrice);
		}
		paint.setStrokeWidth(3);

		Log.d(this.toString(), "In ChartView; integerPoints: " + integerPoints.toString());
		
		for (int i = 1; i < integerPoints.size(); i++) {
			if (integerPoints.get(i) < integerPoints.get(i - 1)) {
				paint.setColor(Color.GREEN);
			} else {
				paint.setColor(Color.RED);
			}
			canvas.drawLine((i - 1) * scale, integerPoints.get(i - 1) * scale, i
					* scale, integerPoints.get(i) * scale, paint);
		}
	}
}