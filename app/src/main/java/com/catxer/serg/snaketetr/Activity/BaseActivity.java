package com.catxer.serg.snaketetr.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.catxer.serg.snaketetr.Fragments.GameFragment;
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
        setFragment(this, new GameFragment(), R.id.GameContainer);


    }

    public static void setFragment(FragmentActivity activity, Fragment fragment, int id) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }


}