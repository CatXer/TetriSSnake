package com.catxer.serg.snaketetr.GameObjects;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.catxer.serg.snaketetr.Mechanics.GamePanel;
import com.catxer.serg.snaketetr.Mechanics.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.catxer.serg.snaketetr.Mechanics.GamePanel.CubeSize;

public class Snake extends ArrayList<Block> {

    private static final int length = 3;

    public static int direction = 0; // 1-top 2-right 3-bottom 4-left;
    public static int dead_direction = 0;

    private int color;
    private boolean Alive = true;
    private Block head;
    private int ID;

    public int getID() {
        return ID;
    }

    public Snake(int x, int y, int id) {
        direction = 2;
        dead_direction = 4;
        head = new Block(color, x, y);
        for (int i = 0; i < length; i++) {
            x--;
            Block bs = new Block(color, x, y);
            this.add(bs);
        }
        setRandomColor();
        ID = id;
    }


    private void moveHead() {
        int X = head.getX();
        int Y = head.getY();

        switch (direction) {
            case 1:
                Y--;
                break;
            case 2:
                X++;
                break;
            case 3:
                Y++;
                break;
            case 4:
                X--;
                break;
        }
        head.move(X, Y);
        dead_direction = direction - 2 > 0 ? direction - 2 : direction + 2;
        if (!GamePanel.Field[X][Y].isFree() || GamePanel.Field[X][Y].isWall()) {
            direction = 0;
            Alive = false;
            GamePanel.GameOver = true;
        }
    }

    private void moveBody() {
        for (int i = 0; i < size(); i++)
            if (i == 0)
                get(0).move(head.getX_old(), head.getY_old());
            else
                get(i).move(get(i - 1).getX_old(), get(i - 1).getY_old());
    }

    public Block getHead() {
        return head;
    }

    public void draw(Canvas canvas) {
        if (head != null) {
            head.draw(canvas);
            if (Settings.Debug) {
                Paint p = new Paint();
                if (head.isFront()) {
                    p.setColor(Color.rgb(0, 0, 90));
                    RectF r = new RectF(CubeSize / 2, CubeSize / 2, CubeSize, CubeSize);
                    r.set(GamePanel.Field[head.getX()][head.getX()].x - r.width() / 2, GamePanel.Field[head.getX()][head.getY()].y - r.height() / 2,
                            GamePanel.Field[head.getX()][head.getY()].x + r.width() / 2, GamePanel.Field[head.getX()][head.getY()].y + r.height() / 2);
                    canvas.drawOval(r, p);
                }
                p.setColor(Color.GREEN);
                p.setTextSize(40);
                p.setFakeBoldText(true);
                canvas.drawText("" + ID, GamePanel.Field[head.getX()][head.getX()].x, GamePanel.Field[head.getX()][head.getY()].y, p);
            }
        }

        for (Block bs : this) {
            bs.draw(canvas);

            if (Settings.Debug) {
                Paint p = new Paint();
                if (bs.isFront()) {
                    p.setColor(Color.rgb(0, 0, 0));
                    RectF r = new RectF(CubeSize / 2, CubeSize / 2, CubeSize, CubeSize);
                    r.set(GamePanel.Field[bs.getX()][bs.getX()].x - r.width() / 2, GamePanel.Field[bs.getX()][bs.getY()].y - r.height() / 2,
                            GamePanel.Field[bs.getX()][bs.getY()].x + r.width() / 2, GamePanel.Field[bs.getX()][bs.getY()].y + r.height() / 2);
                    canvas.drawOval(r, p);
                }
                p.setColor(Color.GREEN);
                p.setTextSize(40);
                p.setFakeBoldText(true);
                canvas.drawText("" + ID, GamePanel.Field[bs.getX()][bs.getX()].x, GamePanel.Field[bs.getX()][bs.getY()].y, p);
            }
        }
    }

    public void update() {
        if (!GamePanel.GameOver)
            if (isAlive()) {
                GamePanel.isDown = false;
                moveHead();
                moveBody();
            } else {
                markFront();
                if (CanFall()) {
                    GamePanel.isDown = false;
                    for (Block b : this)
                        b.move(b.getX(), b.getY() + 1);
                    if (head != null)
                        head.move(head.getX(), head.getY() + 1);
                }
            }
    }

    private boolean CanFall() {
        for (Block b : this)
            if (b.isFront() && !GamePanel.Field[b.getX()][b.getY() + 1].isEmpty())
                return false;
        if (head != null)
            return !head.isFront() || GamePanel.Field[head.getX()][head.getY() + 1].isEmpty();
        return true;
    }

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<>(this);
        if (head != null)
            blocks.add(head);
        return blocks;
    }

    private void markFront() {
        @SuppressLint("UseSparseArrays") HashMap<Integer, Block> temp = new HashMap<>();
        for (Block b : this) {
            int X = b.getX();
            int Y = b.getY();
            if (temp.containsKey(X) && temp.get(X).getY() < Y)
                temp.put(X, b);
            else if (!temp.containsKey(X))
                temp.put(X, b);
        }


        if (head != null && (!temp.containsKey(head.getX()) || temp.get(head.getX()).getY() < head.getY())) {
            head.setFront(true);
            temp.remove(head.getX());
        }

        for (Block b : temp.values())
            b.setFront(true);
    }

    public boolean remove(HashMap<Integer, ArrayList<Integer>> coords) {
        ArrayList<Block> removeList = new ArrayList<>();
        for (int blockId = 0; blockId < size(); blockId++) {
            Block b = get(blockId);
            if (coords.containsKey(b.getY()) && coords.get(b.getY()).contains(b.getX()))
                removeList.add(b);
        }
        if (head != null && coords.containsKey(head.getY()) && coords.get(head.getY()).contains(head.getX()))
            head = null;
        for (Block b : removeList)
            remove(b);
        return size() == 0 && getHead() == null;
    }

    public void setAlive(boolean alive) {
        Alive = alive;
    }

    public boolean isAlive() {
        return Alive;
    }

    public void addBody() {
        Block lstB = get(size() - 1);
        this.add(new Block(color, lstB.getX_old(), lstB.getY_old()));

    }

    public void setRandomColor() {
        Random r = new Random();
        color = Color.argb(200, r.nextInt(255), r.nextInt(255), r.nextInt(255));
        head.setColor(color);
        for (Block block : this)
            block.setColor(color);
    }
}