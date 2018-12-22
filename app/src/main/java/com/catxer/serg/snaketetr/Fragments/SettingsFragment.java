package com.catxer.serg.snaketetr.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catxer.serg.snaketetr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View settingsView;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        return settingsView;
    }

}
