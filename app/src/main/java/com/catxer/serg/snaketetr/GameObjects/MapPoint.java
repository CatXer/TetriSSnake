package com.catxer.serg.snaketetr.GameObjects;

import android.graphics.Point;

public class MapPoint extends Point {

    private boolean free = true;
    private boolean wall;

    public MapPoint(int x, int y, boolean isWall) {
        super(x, y);
        wall = isWall;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isFree() {
        return free;
    }

    public boolean isWall() {
        return wall;
    }
}
