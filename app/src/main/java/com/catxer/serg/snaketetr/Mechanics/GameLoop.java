package com.catxer.serg.snaketetr.Mechanics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    static int Daley = 160;
    static final int NormalDaley = 160;
    private static boolean Paused = false;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean isRunning;

    public GameLoop(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
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

    void Stop() {
        isRunning = false;
    }

    public void pause() {
        Paused = !Paused;
    }
}
