package com.catxer.serg.snaketetr.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.Activity.PauseDialog;
import com.catxer.serg.snaketetr.GameObjects.Snake;
import com.catxer.serg.snaketetr.Mechanics.GamePanel;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

public class GameFragment extends Fragment implements View.OnClickListener {


    public static GamePanel gamePanel;
    public static TextView info;
    private static int RateScore = 0;
    private static int EatsCount = 0;
    private static String score;

    public GameFragment() {
        // Required empty public constructor
    }

    public void addScore(int size) {
        RateScore += size;
        updateUI();
    }

    public void addEat() {
        EatsCount++;
        updateUI();
    }

    public void updateUI() {
        score = "GM:" + gamePanel.getGameMode() + "\n\nscore:" + RateScore + "\nneat:" + EatsCount;
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info.setText(score);

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        ImageView top = view.findViewById(R.id.top);
        top.setOnClickListener(this);

        ImageView down = view.findViewById(R.id.down);
        down.setOnClickListener(this);

        ImageView right = view.findViewById(R.id.right);
        right.setOnClickListener(this);

        ImageView left = view.findViewById(R.id.left);
        left.setOnClickListener(this);

        ImageView pause = view.findViewById(R.id.pause);
        pause.setOnClickListener(this);

        info = view.findViewById(R.id.score);

        //Choose Type Game
        StartNewGame(0);

        return view;
    }

    private void StartNewGame(int GameMode) {
        RateScore = 0;
        EatsCount = 0;
        gamePanel = new GamePanel(this, GameMode);
        BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new CanvasFragment(), R.id.CanvasContainer);
        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top:
                if (Snake.direction != 3)
                    Snake.direction = 1;
                break;
            case R.id.right:
                if (Snake.direction != 4)
                    Snake.direction = 2;
                break;
            case R.id.down:
                if (Snake.direction != 1)
                    Snake.direction = 3;
                break;
            case R.id.left:
                if (Snake.direction != 2)
                    Snake.direction = 4;
                break;
            case R.id.pause:
                gamePanel.pause();
                new PauseDialog(Objects.requireNonNull(getContext())).show();
                break;

        }
        if (GamePanel.GameOver) {
            StartNewGame(1);
            //draw Retry Screen
        }
    }
}
