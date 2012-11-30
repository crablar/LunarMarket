package com.jeffmeyerson.moonstocks.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jeffmeyerson.moonstocks.activities.MoonActivity;
import com.jeffmeyerson.moonstocks.pricefunctions.PriceFunction;

public class ChartView extends View {

	// canvas
	private Canvas canvas;

	// constants
	private static final int MAX_POINTS = 100;
	private static final int SCREEN_HEIGHT = 300;
	private static final float SCALE = 3;// getWidth() / 20;
	private static final int INTERPOLATION_LEVEL = 10;

	// these are static, not constant
	private static double MAX_PRICE;
	private static double MIN_PRICE;

	// member variables
	private List<Float> points;
	private Paint paint;
	private boolean interpolate = false;

	void initialize() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// TODO: evaluate performance of using ArrayList vs. LinkedList here
		points = new ArrayList<Float>();
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

	/**
	 * Set the max and the min price to display things better.
	 * 
	 * @param max
	 * @param min
	 */
	public void setMaxAndMin(double max, double min) {
		MAX_PRICE = max;
		MIN_PRICE = min;
	}

	public void toggleInterpolation() {
		this.interpolate = !interpolate;
		if (!interpolate) {
			while (points.size() > MAX_POINTS) {
				points.remove(0);
			}
		}
	}

	/**
	 * Adds a point to the ChartView. This automatically scrolls the ChartView
	 * and deletes the point that falls off the edge.
	 * 
	 * @param point
	 */
	public void addPoint(Float point) {
		points.add(point);
		if (interpolate) {
			if (points.size() > MAX_POINTS + INTERPOLATION_LEVEL) {
				for (int i = 0; i < INTERPOLATION_LEVEL; i++) {
					points.remove(0);
				}
			}
		} else {
			while (points.size() > MAX_POINTS) {
				points.remove(0);
			}
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		this.canvas = canvas;
		super.onDraw(canvas);

		int IL; // short for InterpolationLevel, done for conciseness below.
		if (interpolate) {
			IL = INTERPOLATION_LEVEL;
		} else {
			IL = 1;
		}

		paint.setStrokeWidth(3);

		for (int i = IL; i < points.size(); i += IL) {
			int[] argb = generateTimedARGB();
//			paint.setARGB(a, r, g, b);
//			float[] picturePoints = generatePicturePoints(i);
//			canvas.drawLines(picturePoints, paint);
			
			canvas.drawARGB(argb[0], argb[1], argb[2], argb[3]);
			
			if (points.get(i - IL) < points.get(i)) {
				paint.setColor(Color.GREEN);
			} else if (points.get(i - IL) > points.get(i)) {
				paint.setColor(Color.RED);
			} else {
				paint.setColor(Color.YELLOW);
				Log.d(this.toString(), "NO PRICE CHANGE");
			}
			canvas.drawLine((i - IL) * SCALE,
					SCREEN_HEIGHT - points.get(i - IL), i * SCALE,
					SCREEN_HEIGHT - points.get(i), paint);
		}

	}

	private float[] generatePicturePoints(int parameter) {
		float[] result = new float[(parameter % points.size()) * 4];
		float begin = 50;
		for (int i = 0; i < result.length; i += 4) {
			result[i] = begin;
			result[i + 1] = begin * 2;
			result[i + 2] = begin;
			result[i + 3] = begin * 2;
		}
		return result;
	}

	public Canvas drawPicture(Canvas canvas, int a, int r, int g, int b) {
		// paint.setARGB(a, r, g, 255);
		// RectF rectf = new RectF(50, 50, 50, 50);
		// canvas.drawOval(rectf, paint);
		// paint.setARGB(a, r, 255, b);
		// rectf = new RectF(100, 100, 100, 100);
		// paint.setARGB(a, 255, g, b);
		// rectf = new RectF(150, 150, 150, 150);
		// paint.setARGB(a, r, 255, b);
		// rectf = new RectF(200, 200, 200, 200);
		return canvas;
	}
	
	public int[] generateTimedARGB(){
		int time = MoonActivity.getTime();
		
		// Convert time to seconds
		time = (time / 1000) % 255;
		
		int a = time * 10 % 255;
		int r = time * 20 % 255;
		int g = 255 - time * 20 % 255;
		int b = (127 + (time * 20)) % 255;
		
		int[] result = {a, r, g, b};
		return result;
	}

}