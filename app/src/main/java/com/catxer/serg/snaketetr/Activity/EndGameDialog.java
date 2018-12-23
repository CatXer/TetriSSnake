package com.catxer.serg.snaketetr.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.catxer.serg.snaketetr.Fragments.GameFragment;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

public class EndGameDialog extends Dialog implements View.OnClickListener {

    public EndGameDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        GameFragment.gamePanel.pause();
        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_1;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.FILL);
        setContentView(R.layout.exit_game_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();

    }

    private void init() {
        TextView exit = findViewById(R.id.exit_yes);
        TextView continuum = findViewById(R.id.exit_no);
        exit.setOnClickListener(this);
        continuum.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_no:
                dismiss();
                GameFragment.gamePanel.pause();
                break;
            case R.id.exit_yes:
                dismiss();
                GameFragment.gamePanel.GameOver();
                break;
        }
    }
}
