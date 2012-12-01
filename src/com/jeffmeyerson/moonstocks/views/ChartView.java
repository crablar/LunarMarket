package com.jeffmeyerson.moonstocks.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jeffmeyerson.moonstocks.activities.MoonActivity;

public class ChartView extends View {

	// constants
	private static final int MAX_POINTS = 100;
	private static final int SCREEN_HEIGHT = 300;
	private static final float SCALE = 3;// getWidth() / 20;
	private static final int INTERPOLATION_LEVEL = 10;

	// these are static, not constant
	private static double MAX_PRICE;
	private static double MIN_PRICE;

    // member variables
    private boolean interpolate = false;

	// used for general drawing
	private Paint paint;

    // this is for the line graph
	private List<Float> points;
	private Path path = new Path();

	// this is for the bobble at the end of the stock graph
	private int startAngle = 0;
	private RectF bobble = new RectF();

	// OMG SPARKLES
	private LevelUpAnimation levelUpAnimation = null;

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
	
	public void doLevelUpAnimation() {
	    float tailX = points.size() * SCALE;
        float tailY = SCREEN_HEIGHT - points.get(points.size() - 1);
	    levelUpAnimation = new LevelUpAnimation(tailX, tailY);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

        final int h = canvas.getHeight();
        final int w = canvas.getWidth();
        final int GRID_LINES = 10;

        // draw ourselves a nice pretty griddy
        paint.setStrokeWidth(1);
        paint.setColor(Color.LTGRAY);

        for (int i = 0; i < GRID_LINES; i++) {
            // vertical lines
            canvas.drawLine((w / GRID_LINES) * i, 0, (w / GRID_LINES) * i, h, paint);
            // horizontal lines
            canvas.drawLine(0, (h / GRID_LINES) * i, w, (h / GRID_LINES) * i, paint);
        }
        // draw a box around the whole shebang
        canvas.drawRect(1,1,w-1,h-1, paint);

		int IL; // short for InterpolationLevel, done for conciseness below.
		if (interpolate) {
			IL = INTERPOLATION_LEVEL;
		} else {
			IL = 1;
		}

		paint.setStrokeWidth(3);

		for (int i = IL; i < points.size(); i += IL) {
			int[] argb = generateTimedARGB();
			// paint.setARGB(a, r, g, b);
			// float[] picturePoints = generatePicturePoints(i);
			// canvas.drawLines(picturePoints, paint);

			canvas.drawARGB(argb[0], argb[1], argb[2], argb[3]);

			if (points.get(i - IL) < points.get(i)) {
				paint.setColor(Color.GREEN);
			} else if (points.get(i - IL) > points.get(i)) {
				paint.setColor(Color.RED);
			} else {
				paint.setColor(Color.YELLOW);
			}
			float startX = (i - IL) * SCALE;
			float startY = SCREEN_HEIGHT - points.get(i - IL);
			float endX = i * SCALE;
			float endY = SCREEN_HEIGHT - points.get(i);
			// this proved to be too slow.
//			path.reset();
//			path.moveTo(startX, startY);
//			path.lineTo(endX, endY);
//			path.lineTo(endX, canvas.getHeight());
//			path.lineTo(startX, canvas.getHeight());
//			path.close();
//			canvas.drawPath(path, paint);
            canvas.drawLine(startX, startY, endX, endY, paint);
		}

		if (points.size() > 0) {
		    paint.setStyle(Style.STROKE);
		    paint.setColor(Color.WHITE);
		    float tailX = points.size() * SCALE;
		    float tailY = SCREEN_HEIGHT - points.get(points.size() - 1);
		    startAngle += 25;
		    bobble.set(tailX - 15, tailY - 15, tailX + 15, tailY + 15);
		    canvas.drawArc(bobble, startAngle, 270, false, paint);
		}

		if (levelUpAnimation != null) {
		    levelUpAnimation.draw(canvas, paint);
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

	public int[] generateTimedARGB() {
		int time = MoonActivity.getTime();

		// Convert time to seconds
		time = (time / 1000);
		
//		// a fluctuates like a sin wave with a period of 512 seconds
//		int a = (time % 511 > 255) ? 255 - (time % 255) : time % 255;
//		
//		// a fluctuates like a sin wave with a period of 256 seconds
//		int r = (time % 255 > 127) ? 127 - (time % 127) : time % 255;
//		
//		// a fluctuates like a sin wave with a period of 256 seconds
//		int g = (time % 127 > 63) ? 63 - (time % 63) : time % 255;
//		
//		// a fluctuates like a sin wave with a period of 256 seconds
//		int b = (time % 63 > 31) ? 31 - (time % 31) : time % 255;

		int[] result = { 0, 255, 255, 255 };
		return result;
	}

}