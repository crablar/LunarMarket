package com.jeffmeyerson.moonstocks.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class SellButton extends View {

    private Paint paint;
    private Path arrow;
    private RectF rect;

    public boolean clickedState = false;

    void initialize() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrow = new Path();
        rect = new RectF();
    }

    public SellButton(Context context) {
        super(context);
        initialize();
    }

    public SellButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public SellButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Button selectors?
        //
        // Button selectors?
        //
        // We don't NEED no stinkin' button selectors!
        if (clickedState) {
            paint.setColor(Color.argb(128, 128, 128, 128));
        } else {
            paint.setColor(Color.argb(128, 255, 128, 128));
        }
        
        final int border = 10;
        final float x = border;
        final float y = border;
        final float h = this.getHeight();
        final float w = this.getWidth();

        paint.setStrokeWidth(border);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(128, 255, 128, 128));

        rect.set(border/2, border/2, this.getWidth() - (border/2), this.getHeight() - (border/2));
        canvas.drawRoundRect(rect, border*2, border*2, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(128, 255, 64, 64));

        arrow.reset();
        arrow.moveTo(x, h/2);
        arrow.lineTo(w/2, h-border);
        arrow.lineTo(w-border, h/2);
        arrow.close();
        // draw the up part of the arrow
        canvas.drawPath(arrow, paint);
        canvas.drawRect(w/4, y + border, 3*(w/4), h/2, paint);

        // draw the text
        paint.setTypeface(Typeface.DEFAULT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        float textWidth = paint.measureText("Sell");
        canvas.drawText("Sell", (w/2) - (textWidth/2),h/2,paint);
    }
}
