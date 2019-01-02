package com.catxer.serg.snaketetr.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.catxer.serg.snaketetr.Mechanics.GamePanel;

public class Block {

    private Rect rect;
    private int color;
    private int X;
    private int Y;
    private int Y_old;
    private int X_old;
    private boolean front = false;
    public boolean removed =false;

    public Block(int color, int x, int y) {
        this.color = color;
        this.X = x;
        this.Y = y;
        rect = new Rect(GamePanel.CubeSize, GamePanel.CubeSize, GamePanel.CubeSize * 2, GamePanel.CubeSize * 2);
        rect.set(GamePanel.Field[X][Y].x - rect.width() / 2, GamePanel.Field[X][Y].y - rect.height() / 2,
                GamePanel.Field[X][Y].x + rect.width() / 2, GamePanel.Field[X][Y].y + rect.height() / 2);
    }

    public void move(int x, int y) {
        setOldXY(X, Y);
        setXY(x, y);
        rect.set(GamePanel.Field[X][Y].x - rect.width() / 2, GamePanel.Field[X][Y].y - rect.height() / 2,
                GamePanel.Field[X][Y].x + rect.width() / 2, GamePanel.Field[X][Y].y + rect.height() / 2);
    }

    public Rect getRect() {
        return rect;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setOldXY(int x, int y) {
        X_old = x;
        Y_old = y;
    }

    public void setXY(int x, int y) {
        X = x;
        Y = y;
    }

    public int getY_old() {
        return Y_old;
    }

    public int getX_old() {
        return X_old;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Point getPoint() {
        return new Point(X, Y);
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        /*if (isFront())
            paint.setColor(Color.BLACK);*/
        if (removed)
            paint.setColor(Color.BLACK);
        canvas.drawRect(this.rect, paint);
    }


}
