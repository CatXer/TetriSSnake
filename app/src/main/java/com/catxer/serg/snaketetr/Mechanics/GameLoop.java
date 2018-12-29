package com.catxer.serg.snaketetr.Mechanics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private int Daley = 160;
    private boolean Paused;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean isRunning;

    public GameLoop(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        Paused = false;
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        super.run();
        Canvas canvas = null;
        isRunning = true;
        while (isRunning) {
            if (!Paused) {
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            try {
                sleep(Daley);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setDaley(int daley){
        Daley = daley;
    }

    public int getDaley() {
        return Daley;
    }

    void Stop() {
        isRunning = false;
    }

    public void setPaused(boolean paused) {
        Paused = paused;
    }

    public boolean isPaused() {
        return Paused;
    }
}
