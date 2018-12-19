package com.catxer.serg.snaketetr.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catxer.serg.snaketetr.Mechanics.GamePanel;

public class CanvasFragment extends Fragment {


    public CanvasFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return GameFragment.gamePanel;
    }

}