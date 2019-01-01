package com.catxer.serg.snaketetr.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.catxer.serg.snaketetr.Activity.BaseActivity;
import com.catxer.serg.snaketetr.Mechanics.Settings;
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


        TextView SN = MM_view.findViewById(R.id.m_play_snake);
        TextView ST = MM_view.findViewById(R.id.m_play_ts);
        TextView sett = MM_view.findViewById(R.id.m_settings);
        TextView Exit = MM_view.findViewById(R.id.m_exit);
        FrameLayout frame = MM_view.findViewById(R.id.m_frame);


        SN.setOnClickListener(this);
        ST.setOnClickListener(this);
        sett.setOnClickListener(this);
        Exit.setOnClickListener(this);
        frame.setBackgroundColor(Settings.lightTheme ? Settings.BG_light : Settings.BG_dark);

        Animation left = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        Animation right = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);

        SN.startAnimation(left);
        ST.startAnimation(right);
        sett.startAnimation(left);

        return MM_view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_play_snake:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new GameFragment(0), R.id.MainContainer, R.anim.slide_up_anim, R.anim.slide_buttom_anim, false, "game-0");
                break;
            case R.id.m_play_ts:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new GameFragment(1), R.id.MainContainer, R.anim.slide_up_anim, R.anim.slide_buttom_anim, false, "game-1");
                break;
            case R.id.m_settings:
                BaseActivity.setFragment(Objects.requireNonNull(getActivity()), new SettingsFragment(), R.id.MainContainer, R.anim.slide_up_anim, R.anim.slide_buttom_anim, true, "settings");
                break;
            case R.id.m_exit:
                Objects.requireNonNull(getActivity()).finish();
                System.exit(0);
                break;

        }
    }
}
