package com.catxer.serg.snaketetr.Mechanics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.catxer.serg.snaketetr.Fragments.GameFragment;
import com.catxer.serg.snaketetr.GameObjects.Block;
import com.catxer.serg.snaketetr.GameObjects.EatBlock;
import com.catxer.serg.snaketetr.GameObjects.MapPoint;
import com.catxer.serg.snaketetr.GameObjects.Snake;
import com.catxer.serg.snaketetr.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {


    private static final double FRAME_WIDTH_K = 0.75f;
    private static final double FRAME_HEIGHT_K = 0.6f;
    public static final int X_block_count = 16;

    private Bitmap groundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

    private static int FRAME_HEIGHT;
    private static int FRAME_WIDTH;
    private static GameLoop thread;

    public int GAME_MODE;
    public static int Y_block_count;
    public static int CubeSize;
    public static MapPoint[][] Field;
    public static ArrayList<Snake> snake;
    public static boolean newSpawn = false;
    public static boolean GameOver;

    private GameFragment fragment;

    private EatBlock eatBlock;

    /**
     * **************************************
     * -> Init Block ->
     */
    public GamePanel(GameFragment fragment, int GAME_MODE) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.GAME_MODE = GAME_MODE;
        GameOver = false;
        InitDisplay(Objects.requireNonNull(fragment.getActivity()));
        InitField();
        getHolder().addCallback(this);
        snake = new ArrayList<>();
        snake.add(new Snake(7, 3));
        eatBlock = new EatBlock(2);
        setFocusable(true);
    }

    private void InitField() {
        Field = new MapPoint[X_block_count][Y_block_count];
        int x = 10;
        int y = 10;
        for (int x_rel = 0, y_rel = 0; x_rel < X_block_count * Y_block_count; x_rel++) {
            if (x_rel % X_block_count == 0 && x_rel / X_block_count >= 1) {
                y_rel++;
                x = 10;
                y += CubeSize;
            }
            if (y_rel == 0 || y_rel == Y_block_count - 1 || x_rel % X_block_count == 0 || x_rel % X_block_count == X_block_count - 1) {
                Field[x_rel % X_block_count][y_rel] = new MapPoint(x, y, true);
            } else {
                Field[x_rel % X_block_count][y_rel] = new MapPoint(x, y, false);
            }

            x += CubeSize;
        }
    }

    private void InitDisplay(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        FRAME_WIDTH = (int) (metricsB.widthPixels * FRAME_WIDTH_K);
        FRAME_HEIGHT = (int) (metricsB.heightPixels * FRAME_HEIGHT_K);

        CubeSize = FRAME_WIDTH / (X_block_count);
        Y_block_count = FRAME_HEIGHT / CubeSize;

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new GameLoop(getHolder(), this);
        thread.start();
        GameLoop.Daley = 800;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /**
     * <- end Init Block <-
     * **************************************
     * -> Update Objects ->
     **/
    public void update() {
        Update_snakes();
    }

    private void Update_snakes() {
        newSpawn = true;
        for (Snake s : snake) {
            s.update();
            if (s.isAlive() && s.getHead().getPoint().equals(eatBlock.getPoint()))
                if (GAME_MODE == 1) {
                    fragment.addEat();
                    eatBlock.setColor(Color.TRANSPARENT);
                    s.setAlive(false);
                    GameLoop.Daley = 30;
                } else {
                    ////////////////////////////////////////
                    switch (eatBlock.getType()) {
                        case 0:
                            s.addBody();
                            break;
                        case 1:
                            if (s.size() > 3)
                                s.remove(s.size() - 1);
                            else s.addBody();
                            break;
                        case 2:
                            for (int i = s.size() - 1; i > 2; i--)
                                s.remove(i);
                            break;
                    }
                    int type = new Random().nextInt(100);
                    if (type < 100 && type >= 45)
                        type = 0;
                    else if (type < 45 && type >= 20)
                        type = 1;
                    else
                        type = 2;
                    eatBlock = new EatBlock(type);
                    ////////////////////////////////////////////////
                }
        }
        Update_map();
        if (GAME_MODE == 1)
            UpdateSpawnT();
    }

    private void UpdateSpawnT() {
        if (newSpawn && !checkLine()) {
            GameLoop.Daley = 160;
            snake.add(new Snake(4, 1));
            eatBlock.move();
            eatBlock.setColor(Color.GREEN);
            newSpawn = false;
        }
    }

    private void Update_map() {
        for (int i = 0, j = 0; i <= X_block_count; i++) {
            if (i % X_block_count == 0 && i / X_block_count >= 1) {
                j++;
                i = 0;
            }
            if (j == Y_block_count)
                break;
            Field[i][j].setFree(true);
        }
        for (Snake s : snake)
            for (Block b : s.getBlocks())
                Field[b.getX()][b.getY()].setFree(false);
    }

    private boolean checkLine() {
        @SuppressLint("UseSparseArrays") HashMap<Integer, ArrayList<Integer>> coords = new HashMap<>();
        int y = 0;
        ArrayList<Integer> xcoords = new ArrayList<>();
        for (int x = 0; x < X_block_count; x++) {
            if (!Field[x][y].isFree()) {
                xcoords.add(x);
            } else {
                xcoords = new ArrayList<>();
            }
            if (xcoords.size() == 6) {
                coords.put(y, new ArrayList<>(xcoords));
            }
            if (x == X_block_count - 1 && y < Y_block_count - 1) {
                x = -1;
                xcoords = new ArrayList<>();
                y++;
            }
        }
        if (coords.size() > 0) {
            for (Snake s : new ArrayList<>(snake))
                s.remove(coords);
            GameLoop.Daley = 400;
            fragment.addScore(coords.size());
            return true;
        } else {
            return false;
        }
    }

    /**
     * <- end Update Objects <-
     * **************************************
     * -> Draw Objects ->
     **/
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (GameLoop.Daley > GameLoop.NormalDaley) {
            GameLoop.Daley = GameLoop.Daley - 100 > GameLoop.NormalDaley ? GameLoop.Daley - 100 : GameLoop.NormalDaley;
            drawField(canvas, Color.argb(-GameLoop.Daley / 10, 177, 177, 177));
        } else
            drawField(canvas, Color.DKGRAY);
        for (Snake s : snake)
            s.draw(canvas);
        eatBlock.draw(canvas);
        if (GameOver) {
            drawGO(canvas);
        }
    }

    private void drawField(Canvas canvas, int background) {
        Paint paintL = new Paint();
        canvas.drawColor(Color.BLACK);
        for (int i = 0, j = 0; i <= X_block_count; i++) {
            if (i % X_block_count == 0 && i / X_block_count >= 1) {
                j++;
                i = 0;
            }
            if (j == Y_block_count)
                break;
            canvas.drawBitmap(groundImage, Field[i][j].x - groundImage.getWidth() / 2, Field[i][j].y - groundImage.getHeight() / 2, paintL);

            if (Field[i][j].isWall()) {
                paintL.setColor(background);
                Rect r = new Rect(CubeSize, CubeSize, CubeSize * 2, CubeSize * 2);
                r.set(Field[i][j].x - r.width() / 2, Field[i][j].y - r.height() / 2,
                        Field[i][j].x + r.width() / 2, Field[i][j].y + r.height() / 2);
                canvas.drawRect(r, paintL);
            }
        }
    }

    private void drawGO(Canvas canvas) {
        thread.Stop();
        canvas.drawColor(Color.BLACK);
        Paint paintL = new Paint();
        paintL.setColor(Color.WHITE);
        paintL.setTextSize(100);


        canvas.drawText("Game Over!", 100, FRAME_HEIGHT / 2, paintL);
        paintL.setTextSize(80);
        canvas.drawText("Press any to replay.", 0, FRAME_HEIGHT / 2 + 200, paintL);
    }

    /**
     * <- end Draw Objects <-
     * ***************************************
     **/


    public void pause() {
        thread.pause();
    }

    public String getGameMode() {
        switch (GAME_MODE) {
            case 0:
                return "S";
            case 1:
                return "ST";
        }
        return null;
    }
}

