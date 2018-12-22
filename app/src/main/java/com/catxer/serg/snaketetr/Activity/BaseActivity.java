package com.catxer.serg.snaketetr.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.catxer.serg.snaketetr.Fragments.MainMenuFragment;
import com.catxer.serg.snaketetr.Fragments.OnBackPressedListener;
import com.catxer.serg.snaketetr.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /////////////////////
        MobileAds.initialize(this, "ca-app-pub-6972982586500917~3018148147");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /////////////////////


        setFragment(this, new MainMenuFragment(), R.id.GameContainer, R.anim.slide_up_anim, R.anim.fade_out, false, "Menu");


    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public static void setFragment(FragmentActivity activity, Fragment fragment, int id_container, int anim_enter, int anim_exit, boolean backSTC, String frag_tag) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(anim_enter, anim_exit, anim_enter, anim_exit);
        transaction.replace(id_container, fragment, frag_tag);
        if (backSTC)
            transaction.addToBackStack(frag_tag);
        transaction.commit();
    }


}