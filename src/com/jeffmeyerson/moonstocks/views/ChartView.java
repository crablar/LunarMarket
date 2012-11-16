package com.jeffmeyerson.moonstocks.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
    public void setMaxAndMin(double max, double min){
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
        super.onDraw(canvas);

        paint.setStrokeWidth(3);

        int IL; // short for InterpolationLevel, done for conciseness below.
        if (interpolate) {
            IL = INTERPOLATION_LEVEL;
        } else {
            IL = 1;
        }

        for (int i = IL; i < points.size(); i += IL) {
            if (points.get(i - IL) < points.get(i)) {
                paint.setColor(Color.GREEN);
            } else if (points.get(i - IL) > points.get(i)) { 
                paint.setColor(Color.RED);
            } else if (points.get(i - IL) == points.get(i)) {
            	paint.setColor(Color.GRAY);
            }
            canvas.drawLine((i - IL) * SCALE, SCREEN_HEIGHT - points.get(i - IL), i * SCALE, SCREEN_HEIGHT - points.get(i), paint);
        }
    }
}