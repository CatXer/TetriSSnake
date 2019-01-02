package com.catxer.serg.snaketetr.Mechanics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import static com.catxer.serg.snaketetr.Mechanics.Settings.X_block_count;

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
                    //canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gamePanel.update();
                        //this.gamePanel.draw(canvas);


                        for (int i = 0, j = 0; i <= X_block_count; i++) {
                            if (i % X_block_count == 0 && i / X_block_count >= 1) {
                                j++;
                                i = 0;
                            }
                            if (j == GamePanel.Y_block_count)
                                break;
                            GamePanel.Field[i][j].setSpawnable(false);
                        }
                        setSpawnZone(1, 1);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            //surfaceHolder.unlockCanvasAndPost(canvas);
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

    private void setSpawnZone(int X, int Y) throws InterruptedException {
        int y = Y;
        Canvas canvas = null;
        while (GamePanel.Field[X][y].isEmpty()&&!GamePanel.Field[X][y].isSpawnable()) {
            canvas = this.surfaceHolder.lockCanvas();

            GamePanel.Field[X][y].setSpawnable(true);
            gamePanel.draw(canvas);


            surfaceHolder.unlockCanvasAndPost(canvas);
            Thread.sleep(8);
            if (GamePanel.Field[X + 1][y].isEmpty() && !GamePanel.Field[X + 1][y].isSpawnable()) {
                setSpawnZone(X + 1, y);
            }
            if (GamePanel.Field[X - 1][y].isEmpty() && !GamePanel.Field[X - 1][y].isSpawnable()) {
                setSpawnZone(X - 1, y);
            }
            if (GamePanel.Field[X][y-1].isEmpty() && !GamePanel.Field[X][y-1].isSpawnable()) {
                setSpawnZone(X, y-1);
            }

            y++;

        }
    }

    public void setDaley(int daley) {
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
