package com.catxer.serg.snaketetr.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catxer.serg.snaketetr.Mechanics.GamePanel;

public class CanvasFragment extends Fragment {

    private GamePanel panel;

    public CanvasFragment() {
    }
    @SuppressLint("ValidFragment")
    public CanvasFragment(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return panel;
    }

}