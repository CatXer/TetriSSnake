package com.catxer.serg.snaketetr.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catxer.serg.snaketetr.Fragments.GameFragment;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;


public class PauseDialog extends Dialog implements View.OnClickListener {


    @SuppressLint("ResourceType")
    public PauseDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_1;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.FILL);
        setContentView(R.layout.pause_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();

    }

    private void init() {
        ImageView resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resume:
                dismiss();
                GameFragment.gamePanel.pause();
                break;
        }
    }
}
