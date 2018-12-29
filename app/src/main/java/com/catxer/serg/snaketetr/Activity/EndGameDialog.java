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

import com.catxer.serg.snaketetr.Mechanics.GamePanel;
import com.catxer.serg.snaketetr.R;

import java.util.Objects;

public class EndGameDialog extends Dialog implements View.OnClickListener {

    private boolean game_state;
    private GamePanel game_panel;

    public EndGameDialog(@NonNull Context context, GamePanel gamePanel) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        this.game_panel = gamePanel;
        game_state = game_panel.getLoop().isPaused();
        game_panel.getLoop().setPaused(true);
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
                game_panel.getLoop().setPaused(game_state);
                break;
            case R.id.exit_yes:
                dismiss();
                game_panel.GameOver();
                break;
        }
    }
}
