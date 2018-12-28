package com.catxer.serg.snaketetr.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.Mechanics.GamePanel;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment implements View.OnClickListener {
    private View GO_view;
    private int GAME_MODE;

    public GameOverFragment() {
    }

    @SuppressLint("ValidFragment")
    public GameOverFragment(int GM) {
        GAME_MODE = GM;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GO_view = inflater.inflate(R.layout.fragment_game_over, container, false);

        ImageView close = GO_view.findViewById(R.id.go_exit_m);
        close.setOnClickListener(this);
        ImageView replay = GO_view.findViewById(R.id.go_replay);
        replay.setOnClickListener(this);


        return GO_view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_replay:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new GameFragment(GAME_MODE), R.id.MainContainer, R.anim.fade_in, R.anim.fade_out, false, "game-" + GAME_MODE);
                break;
            case R.id.go_exit_m:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new MainMenuFragment(), R.id.MainContainer, R.anim.fade_in, R.anim.fade_out, false, "Menu");
                break;
        }
    }

}
