package com.catxer.serg.snaketetr.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameOverFragment extends Fragment implements View.OnClickListener {
    private View GO_view;

    public GameOverFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GO_view = inflater.inflate(R.layout.fragment_game_over, container, false);

        ImageView close = GO_view.findViewById(R.id.exit_menu);
        close.setOnClickListener(this);


        return GO_view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_menu:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new MainMenuFragment(), R.id.GameContainer, R.anim.fade_in, R.anim.fade_out, false, "Menu");
                break;
        }
    }

}
