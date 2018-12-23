package com.catxer.serg.snaketetr.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.catxer.serg.snaketetr.Mechanics.GamePanel;

import java.util.Random;

public class EatBlock extends Block {

    private static final int stand_color = Color.RED;
    private static final int easy_color = Color.YELLOW;
    private static final int remove_color = Color.GREEN;
    private int Type;


    public EatBlock(int type) {
        super(stand_color, 0, 0);
        Type = type;
        switch (Type) {
            case 0:
                break;
            case 1:
                setColor(easy_color);
                break;
            case 2:
                setColor(remove_color);
                break;
        }
        move();
    }

    public void move() {
        int x;
        int y;
        setOldXY(getX(), getY());
        do {
            x = new Random().nextInt(GamePanel.X_block_count - 1);
            y = new Random().nextInt(GamePanel.Y_block_count - 1);
            setXY(x, y);
        } while (!GamePanel.Field[x][y].isFree() || GamePanel.Field[x][y].isWall());

        Rect rect = this.getRect();
        rect.set(GamePanel.Field[x][y].x - rect.width() / 2, GamePanel.Field[x][y].y - rect.height() / 2,
                GamePanel.Field[x][y].x + rect.width() / 2, GamePanel.Field[x][y].y + rect.height() / 2);

    }

    public int getType() {
        return Type;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getColor());
        canvas.drawRect(getRect(), paint);
    }
}