package com.jeffmeyerson.moonstocks.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BuyButton extends View {

    private Paint paint;

    void initialize() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        paint.setColor(Color.GRAY);

        final float h = this.getHeight();
        final float w = this.getWidth();
        // TODO: draw the buy icon here
        canvas.drawLine(0, h/2, w/2, 0, paint);
        canvas.drawLine(w/2, 0, w, h/2, paint);
        canvas.drawLine(0, h/2, w, h/2, paint);
    }
}