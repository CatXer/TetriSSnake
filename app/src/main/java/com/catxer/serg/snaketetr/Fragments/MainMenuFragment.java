package com.catxer.serg.snaketetr.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment implements View.OnClickListener {

    private View MM_view;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MM_view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        TextView SN = MM_view.findViewById(R.id.PlaySnake);
        TextView ST = MM_view.findViewById(R.id.PlayST);
        SN.setOnClickListener(this);
        ST.setOnClickListener(this);

        return MM_view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.PlaySnake:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new GameFragment(0), R.id.GameContainer, R.anim.slide_up_anim, R.anim.slide_buttom_anim, false, "game-0");
                break;
            case R.id.PlayST:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new GameFragment(1), R.id.GameContainer, R.anim.slide_up_anim, R.anim.slide_buttom_anim, false, "game-1");
                break;
        }
    }
}
