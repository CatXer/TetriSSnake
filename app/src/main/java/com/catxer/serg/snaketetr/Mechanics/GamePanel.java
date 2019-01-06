package com.catxer.serg.snaketetr.Mechanics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.Fragments.GameFragment;
import com.catxer.serg.snaketetr.Fragments.GameOverFragment;
import com.catxer.serg.snaketetr.GameObjects.Block;
import com.catxer.serg.snaketetr.GameObjects.EatBlock;
import com.catxer.serg.snaketetr.GameObjects.MapPoint;
import com.catxer.serg.snaketetr.GameObjects.Snake;
import com.catxer.serg.snaketetr.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import static com.catxer.serg.snaketetr.Mechanics.Settings.X_block_count;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static MapPoint[][] Field;
    public static HashMap<Integer, Snake> snakes;
    public static boolean isDown = false;
    public static boolean GameOver;
    public static int Y_block_count;
    public static int CubeSize;

    private Bitmap groundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    private GameLoop gameLoop;
    private int GAME_MODE;
    private GameFragment fragment;
    private EatBlock eatBlock;

    /**
     * **************************************
     * -> Init Block ->
     */
    @SuppressLint("UseSparseArrays")
    public GamePanel(GameFragment fragment, int GAME_MODE) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.GAME_MODE = GAME_MODE;
        GameOver = false;
        InitDisplay(Objects.requireNonNull(fragment.getActivity()));
        InitField();
        getHolder().addCallback(this);
        snakes = new HashMap<>();
        Snake s = new Snake(7, 3, GamePanel.GenerateID());
        snakes.put(s.getID(), s);
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

        int FRAME_WIDTH = (int) (metricsB.widthPixels * Settings.FRAME_WIDTH_K);
        int FRAME_HEIGHT = (int) (metricsB.heightPixels * Settings.FRAME_HEIGHT_K);

        CubeSize = FRAME_WIDTH / (X_block_count);
        Y_block_count = FRAME_HEIGHT / CubeSize;

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameLoop = new GameLoop(getHolder(), this);
        gameLoop.start();
        gameLoop.setDaley(800);
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
        if (!GameOver)
            Update_snakes();
        else
            GameOver();
    }

    private void Update_snakes() {
        isDown = true;
        for (Snake s : snakes.values()) {
            Update_map();
            s.update();
            if (s.isAlive() && s.getHead().getPoint().equals(eatBlock.getPoint())) {
                fragment.addEat();
                if (GAME_MODE == 1) {
                    eatBlock.setColor(Color.TRANSPARENT);
                    s.setAlive(false);
                    gameLoop.setDaley(30);
                } else {
                    ////////////////////////////////////////
                    switch (eatBlock.getType()) {
                        case 0:
                            s.addBody();
                            break;
                        case 1:
                            s.setRandomColor();
                            break;
                        case 2:
                            fragment.addScore(s.size() - 3);
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

        }
        if (GAME_MODE == 1)
            UpdateSpawnT();


    }

    private void UpdateSpawnT() {
        if (isDown && !checkLine()) {
            gameLoop.setDaley(Settings.NormalDaley);
            Snake s = new Snake(4, 1, GamePanel.GenerateID());
            snakes.put(s.getID(), s);
            for (int i = 1, j = 1; i <= X_block_count; i++) {
                if (i % (X_block_count - 1) == 0 && i / (X_block_count - 1) >= 1) {
                    j++;
                    i = 1;
                }
                if (j == Y_block_count - 1)
                    break;
                Field[i][j].setSpawnable(false);
                Field[i][j].setFree(true);
            }
            for (Snake sss : snakes.values())
                for (Block b : sss.getBlocks())
                    Field[b.getX()][b.getY()].setFree(false);
            setSpawnZone(5, 1);

            eatBlock.move();
            eatBlock.setColor(Color.GREEN);
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
        for (Snake s : snakes.values())
            for (Block b : s.getBlocks())
                Field[b.getX()][b.getY()].setFree(false);
    }

    private void setSpawnZone(int X, int Y) {
        int y = Y;
        while (Field[X][y].isEmpty() && !Field[X][y].isSpawnable()) {

            Field[X][y].setSpawnable(true);

            if (Field[X + 1][y].isEmpty() && !Field[X + 1][y].isSpawnable()) {
                setSpawnZone(X + 1, y);
            }
            if (Field[X - 1][y].isEmpty() && !Field[X - 1][y].isSpawnable()) {
                setSpawnZone(X - 1, y);
            }
            if (Field[X][y - 1].isEmpty() && !Field[X][y - 1].isSpawnable()) {
                setSpawnZone(X, y - 1);
            }

            y++;

        }

    }

    private boolean checkLine() {
        Update_map();
        @SuppressLint("UseSparseArrays") HashMap<Integer, ArrayList<Integer>> coords = new HashMap<>();
        int y = Y_block_count - 1;
        ArrayList<Integer> xcoords = new ArrayList<>();
        for (int x = 1; x < X_block_count; x++) {
            if (!Field[x][y].isFree()) {
                xcoords.add(x);
            } else {
                xcoords = new ArrayList<>();
            }
            if (xcoords.size() == 6) {
                coords.put(y, new ArrayList<>(xcoords));
            }
            if (x == X_block_count - 1 && y > 1) {
                x = 0;
                xcoords = new ArrayList<>();
                y--;
            }
        }
        if (coords.size() > 0) {
            for (Iterator<Snake> iter = snakes.values().iterator(); iter.hasNext(); ) {
                if (iter.next().remove(coords))
                    iter.remove();
            }
            System.out.println(Arrays.toString(coords.keySet().toArray()));
            System.out.println(snakes.values().size());
            gameLoop.setDaley(400);
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


        if (gameLoop.getDaley() > Settings.NormalDaley) {
            gameLoop.setDaley(gameLoop.getDaley() - 100 > Settings.NormalDaley ? gameLoop.getDaley() - 100 : Settings.NormalDaley);
            drawField(canvas, Color.argb(-gameLoop.getDaley() / 10, 177, 177, 177));
        } else
            drawField(canvas, Color.DKGRAY);
        for (Snake s : snakes.values())
            s.draw(canvas);
        eatBlock.draw(canvas);


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
                Paint p = new Paint();
                p.setColor(background);
                Rect r = new Rect(CubeSize, CubeSize, CubeSize * 2, CubeSize * 2);
                r.set(Field[i][j].x - r.width() / 2, Field[i][j].y - r.height() / 2,
                        Field[i][j].x + r.width() / 2, Field[i][j].y + r.height() / 2);
                canvas.drawRect(r, p);
            }if (Settings.Debug&&!Field[i][j].isFree()) {
                Paint p = new Paint();
                p.setColor(Color.RED);
                Rect r = new Rect(CubeSize, CubeSize, CubeSize * 2, CubeSize * 2);
                r.set(Field[i][j].x - r.width() / 2, Field[i][j].y - r.height() / 2,
                        Field[i][j].x + r.width() / 2, Field[i][j].y + r.height() / 2);
                canvas.drawRect(r, p);
            }


        }
    }

    public void GameOver() {
        GameOver = true;
        gameLoop.Stop();
        BaseActivity.setFragment(Objects.requireNonNull(fragment.getActivity()), new GameOverFragment(GAME_MODE), R.id.MainContainer, R.anim.fade_in, R.anim.fade_out, false, "GO-screen");

    }

    /**
     * <- End Draw Objects <-
     * ***************************************
     **/


    public void pause() {
        gameLoop.setPaused(!gameLoop.isPaused());
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

    public GameLoop getLoop() {
        return gameLoop;
    }

    public static int GenerateID() {
        Random r = new Random();
        int id = 0;
        do {
            id = r.nextInt(1000);
        } while (snakes.containsKey(id));
        return id;
    }
}

