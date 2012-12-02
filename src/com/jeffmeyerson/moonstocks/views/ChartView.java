package com.jeffmeyerson.moonstocks.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {

	// constants
	private static final int MAX_POINTS = 100;
	private static final int INTERPOLATION_LEVEL = 10;

    // used to configure the behavior of the ChartView
    private boolean interpolate = false;
    private boolean showAvg;

    // used to scale the ChartView
    private float maxPrice = 300;
    private float minPrice = 0;
    
	// general drawing primitives
	private Paint paint;
    private final DashPathEffect dottedLine = new DashPathEffect(new float[] {8, 10}, 0);
    private final Path path = new Path();
    private final RectF rect = new RectF();

    // used by the grid
    private int verticalGridLines = 10;
    private int horizontalGridLines = 10;

    // used by the line chart
	private final List<Float> points = new ArrayList<Float>();

	// used by the moving average
    private float average;

	// this is for the bobble at the end of the stock graph
	private int startAngle = 0;

	// OMG SPARKLES
    private boolean triggerSparkles = false;
	private LevelUpAnimation levelUpAnimation = null;

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
		maxPrice = Collections.max(points);
        minPrice = Collections.min(points);
        // add some padding to the range
        float range = maxPrice - minPrice;
        maxPrice += (range/2);
        minPrice -= (range/2);
	}

	public void showAverage(boolean showAvg) {
	    this.showAvg = showAvg;
	}
	
    public void setAverage(float average) {
        this.average = average;
    }

	public void doLevelUpAnimation() {
	    triggerSparkles = true;
	}

    public void setGridLines(int verticalGridLines, int horizontalGridLines) {
        this.verticalGridLines = verticalGridLines;
        this.horizontalGridLines = horizontalGridLines;
    }

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

        final int h = canvas.getHeight();
        final int w = canvas.getWidth();
        final float vScale = 3;
        final float hOffset = minPrice;
        final float hScale = h / (maxPrice - minPrice);

        // draw ourselves a nice pretty griddy
        paint.setStrokeWidth(1);
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Style.FILL_AND_STROKE);
        final int priceStep = (int) ((maxPrice - minPrice) / horizontalGridLines);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(20);
        for (int i = 0; i < horizontalGridLines; i++) {
            canvas.drawLine(0, (h / horizontalGridLines) * i, w, (h / horizontalGridLines) * i, paint);
            float textWidth = paint.measureText("$" + ((priceStep * i) + (int)minPrice));
            canvas.drawText("$" + ((priceStep * i) + (int)minPrice), w - textWidth - 5, h - (h / horizontalGridLines) * i, paint);
        }

        for (int i = 0; i < verticalGridLines; i++) {
            canvas.drawLine((w / verticalGridLines) * i, 0, (w / verticalGridLines) * i, h, paint);
        }
        paint.setStyle(Style.STROKE);
        canvas.drawRect(1,1,w-1,h-1, paint);

        // decide whether to interpolate
		int IL; // short for InterpolationLevel, done for conciseness below.
		if (interpolate) {
			IL = INTERPOLATION_LEVEL;
		} else {
			IL = 1;
		}

        // draw the stock graph
		paint.setStrokeWidth(3);
		for (int i = IL; i < points.size(); i += IL) {
			if (points.get(i - IL) < points.get(i)) {
				paint.setColor(Color.GREEN);
			} else if (points.get(i - IL) > points.get(i)) {
				paint.setColor(Color.RED);
			} else {
				paint.setColor(Color.YELLOW);
			}
			float startX = (i - IL) * vScale;
			float startY = h - ((points.get(i - IL) - hOffset) * hScale);
			float endX = i * vScale;
			float endY = h - ((points.get(i) - hOffset) * hScale);

            canvas.drawLine(startX, startY, endX, endY, paint);
		}

        // draw the bobble
        float tailX = 0;
        float tailY = 0;
		if (points.size() > 0) {
		    paint.setStyle(Style.STROKE);
		    paint.setColor(Color.WHITE);
		    tailX = points.size() * vScale;
            tailY = h - ((points.get(points.size() - 1) - hOffset) * hScale);
		    startAngle += 25;
		    rect.set(tailX - 15, tailY - 15, tailX + 15, tailY + 15);
		    canvas.drawArc(rect, startAngle, 270, false, paint);
		}

        // draw the moving average line
		if (showAvg) {
		    paint.setColor(Color.YELLOW);
            paint.setStyle(Style.STROKE);
            paint.setPathEffect(dottedLine);
            paint.setStrokeWidth(1);
            path.reset();
            path.moveTo(0, h - ((average - hOffset) * hScale));
            path.lineTo(w, h - ((average - hOffset) * hScale));
            canvas.drawPath(path, paint);
            paint.setPathEffect(null);
		}

        // draw the sparkles
		if (triggerSparkles) {
            // TODO: fix this warning properly
            levelUpAnimation = new LevelUpAnimation(tailX, tailY);
            triggerSparkles = false;
		}
		if (levelUpAnimation != null) {
		    levelUpAnimation.draw(canvas, paint);
		}

	}
}