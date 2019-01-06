package com.catxer.serg.snaketetr.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.catxer.serg.snaketetr.Mechanics.Settings;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        FrameLayout layout = settingsView.findViewById(R.id.set_frame);
        layout.setBackgroundColor(Settings.lightTheme ? Settings.BG_light : Settings.BG_dark);

        SeekBar difficult = settingsView.findViewById(R.id.set_diff);
        SeekBar x_blocks = settingsView.findViewById(R.id.set_Block);
        ImageView back = settingsView.findViewById(R.id.set_back);

        difficult.setOnSeekBarChangeListener(this);
        x_blocks.setOnSeekBarChangeListener(this);
        back.setOnClickListener(this);

        difficult.setProgress((470-Settings.NormalDaley)/40);
        x_blocks.setProgress(Settings.X_block_count-10);


        CheckBox CB = settingsView.findViewById(R.id.CB_debug);
        CB.setChecked(Settings.Debug);
        CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Settings.Debug = b;
            }
        });

        return settingsView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_back:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.set_diff:
                Settings.NormalDaley = 470 - seekBar.getProgress()*40;
                break;
            case R.id.set_Block:
                Settings.X_block_count = seekBar.getProgress()+10;
                break;
        }
    }
}
