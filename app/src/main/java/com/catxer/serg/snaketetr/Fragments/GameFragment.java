package com.catxer.serg.snaketetr.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.Activity.EndGameDialog;
import com.catxer.serg.snaketetr.GameObjects.Snake;
import com.catxer.serg.snaketetr.Mechanics.GamePanel;
import com.catxer.serg.snaketetr.Mechanics.Settings;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

public class GameFragment extends Fragment implements View.OnClickListener, OnBackPressedListener {


    private GamePanel gamePanel;
    private int GameMode = 2;
    private TextView info;
    private static int RateScore = 0;
    private static int EatsCount = 0;
    private static String score;
    private ImageView pause;

    public GameFragment() {
    }

    @SuppressLint("ValidFragment")
    public GameFragment(int GameMode) {
        this.GameMode = GameMode;
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
        score = "GM:" + gamePanel.getGameMode() + "\n\nscore:" + RateScore + "\neat:" + EatsCount;
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

        ImageView top = view.findViewById(R.id.control_up);
        top.setOnClickListener(this);

        ImageView down = view.findViewById(R.id.control_down);
        down.setOnClickListener(this);

        ImageView right = view.findViewById(R.id.control_right);
        right.setOnClickListener(this);

        ImageView left = view.findViewById(R.id.control_left);
        left.setOnClickListener(this);

        ImageView exit = view.findViewById(R.id.game_exit_m);
        exit.setOnClickListener(this);

        LinearLayout layout = view.findViewById(R.id.game_frame);
        layout.setBackgroundColor(Settings.lightTheme ? Settings.BG_light : Settings.BG_dark);

        pause = view.findViewById(R.id.game_pause);
        pause.setOnClickListener(this);

        info = view.findViewById(R.id.game_info_panel);

        StartNewGame(GameMode);

        return view;
    }

    public void StartNewGame(int GameMode) {
        RateScore = 0;
        EatsCount = 0;
        gamePanel = new GamePanel(this, GameMode);
        BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new CanvasFragment(gamePanel), R.id.game_container, R.anim.fade_in, R.anim.fade_out, false, "draw_field");
        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.control_up:
                if (Snake.dead_direction != 1) {
                    Snake.direction = 1;
                }
                break;
            case R.id.control_right:
                if (Snake.dead_direction != 2) {
                    Snake.direction = 2;
                }
                break;
            case R.id.control_down:
                if (Snake.dead_direction != 3) {
                    Snake.direction = 3;
                }
                break;
            case R.id.control_left:
                if (Snake.dead_direction != 4) {
                    Snake.direction = 4;
                }
                break;
            case R.id.game_pause:
                gamePanel.pause();
                if (gamePanel.getLoop().isPaused())
                    pause.setImageResource(R.drawable.baseline_play_arrow_white_36dp);
                else
                    pause.setImageResource(R.drawable.baseline_pause_white_36dp);

                break;
            case R.id.game_exit_m:
                new EndGameDialog(Objects.requireNonNull(getContext()), gamePanel).show();
                break;

        }

    }

    @Override
    public void onBackPressed() {
        new EndGameDialog(Objects.requireNonNull(getContext()), gamePanel).show();
    }
}
