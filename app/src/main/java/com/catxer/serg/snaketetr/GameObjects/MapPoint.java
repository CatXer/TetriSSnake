package com.catxer.serg.snaketetr.GameObjects;

import android.graphics.Point;

public class MapPoint extends Point {

    private boolean free = true;
    private boolean wall;
    private boolean spawnable;

    public MapPoint(int x, int y, boolean isWall) {
        super(x, y);
        wall = isWall;
        spawnable = !isWall;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setSpawnable(boolean spannable) {
        spawnable = spannable;
    }

    public boolean isFree() {
        return free;
    }

    public boolean isEmpty() {
        return free && !wall;
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isSpawnable() {
        return spawnable;
    }
}
