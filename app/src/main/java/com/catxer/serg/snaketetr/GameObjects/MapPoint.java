package com.catxer.serg.snaketetr.GameObjects;

import android.graphics.Point;

public class MapPoint extends Point {

    private boolean free;
    private boolean wall;
    private boolean no_generability;

    public MapPoint(int x, int y, boolean isWall) {
        super(x, y);
        wall = isWall;
        no_generability = isWall;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setNoGenerability(boolean generability) {
        this.no_generability = generability;
    }

    public boolean isFree() {
        return free;
    }

    public boolean isEmpty() {
        return isFree() && !wall;
    }

    public boolean isWall() {
        return wall;
    }

    public boolean isNoGenerability() {
        return no_generability;
    }
}
