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
    private static double MAX_PRICE;
    private static double MIN_PRICE;
    
    // member variables
    private List<Float> points;
    private Paint paint;
    

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
    
    /**
     * Adds a point to the ChartView. This automatically scrolls the ChartView
     * and deletes the point that falls off the edge.
     * @param point
     */
    public void addPoint(Float point) {
        points.add(point);
        if (points.size() > MAX_POINTS) {
            points.remove(0);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(3);

        for (int i = 1; i < points.size(); i++) {
            if (points.get(i - 1) < points.get(i))
                paint.setColor(Color.GREEN);
            if (points.get(i - 1) > points.get(i)) 
                paint.setColor(Color.RED);
            if (points.get(i - 1) == points.get(i))
            	paint.setColor(Color.GRAY);
            canvas.drawLine((i - 1) * SCALE, SCREEN_HEIGHT - points.get(i - 1), i * SCALE, SCREEN_HEIGHT - points.get(i), paint);
        }
    }
}