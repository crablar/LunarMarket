package com.jeffmeyerson.moonstocks.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BuyButton extends View {

    private Paint paint;
    private Path arrow;

    void initialize() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrow = new Path();
    }

    public BuyButton(Context context) {
        super(context);
        initialize();
    }

    public BuyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BuyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(128, 64, 255, 64));

        final float h = this.getHeight();
        final float w = this.getWidth();
        arrow.reset();
        arrow.moveTo(0, h/2);
        arrow.lineTo(w/2, 0);
        arrow.lineTo(w, h/2);
        arrow.close();
        // draw the up part of the arrow
        canvas.drawPath(arrow, paint);
        canvas.drawRect(w/4, h/2, 3*(w/4), h, paint);
    }
}
