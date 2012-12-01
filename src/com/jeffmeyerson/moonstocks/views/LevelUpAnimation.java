package com.jeffmeyerson.moonstocks.views;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public class LevelUpAnimation {

    private static final int NUM_SPARKLES = 100;
    
    List<Sparkle> sparkles;

    private class Sparkle {
        Sparkle(float x, float y, float xVel, float yVel, int color) {
            this.x = x;
            this.y = y;
            this.xVel = xVel;
            this.yVel = yVel;
            this.color = color;
        }
        float x;
        float y;
        float xVel;
        float yVel;
        int color;
    }

    public LevelUpAnimation(float x, float y) {
        Random rand = new Random();
        sparkles = new LinkedList<Sparkle>();

        for (int i = 0; i < NUM_SPARKLES; i++) {
            sparkles.add(new Sparkle(x, y, (rand.nextFloat() - 0.5f) * 15f, rand.nextFloat() * -20, rand.nextInt()));
        }
    }
    
    public void draw(Canvas canvas, Paint paint) {
        Iterator<Sparkle> i = sparkles.iterator();
        while (i.hasNext()) {
            Sparkle s = i.next();
            s.yVel += 1; // simulate gravity
            s.x += s.xVel;
            s.y += s.yVel;
            if (s.x > canvas.getWidth() || s.x < 0 || s.y > canvas.getHeight()) {
                i.remove();
            } else  {
                paint.setColor(s.color);
                canvas.drawPoint(s.x, s.y, paint);
            }
        }
    }

    public boolean finished() {
        return sparkles.isEmpty();
    }
}
