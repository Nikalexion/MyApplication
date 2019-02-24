package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
public class MainActivity extends AppCompatActivity {

    public static final String PREF_AGORES = "AGORES";
    public static final String PREF_EPILOGES = "EPILOGES";

    private Button shop;
    private FirebaseAnalytics mFirebaseAnalytics;


    //To olo "press back again to leave"
    boolean doubleBackToExitPressedOnce = false;

    //pfk = plithos free katigorion
    private int pfk = 6;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Πατήστε ξανά για έξοδο απο την εφαρμογή", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5861682469694178~7665455042");


        if(getSharedPreferences(PREF_EPILOGES, 0).getBoolean("protiFora",true)) {
            SharedPreferences.Editor defaultAgores = getSharedPreferences(PREF_AGORES, 0).edit();
            SharedPreferences.Editor defaultEpiloges = getSharedPreferences(PREF_EPILOGES, 0).edit();

            defaultEpiloges.putBoolean("protiFora", false);
            defaultEpiloges.apply();

            String[] katigories = getResources().getStringArray(R.array.categories);
            for (int i = 0; i < pfk; i++) {
                defaultAgores.putBoolean(katigories[i], true);
                defaultAgores.apply();
                defaultEpiloges.putBoolean(katigories[i], true);
                defaultEpiloges.apply();
            }

        }

        Button starter = (Button) findViewById(R.id.starter);
        Button guidelines = (Button) findViewById(R.id.guidelines);
        Button epiloges = (Button) findViewById(R.id.epiloges);
        Button shop = (Button) findViewById(R.id.shop);
        Button promotion = (Button) findViewById(R.id.promotion);


        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fireLog("start_new_round");
                startActivity(new Intent(getApplicationContext(), ModePicker.class));
            }
        });

        guidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fireLog("read_rules");
                startActivity(new Intent(getApplicationContext(), Guide.class));
            }
        });

        epiloges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fireLog("go_to_epiloges");
                startActivity(new Intent(getApplicationContext(), Epiloges.class));
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fireLog("go_to_shop");
                startActivity(new Intent(getApplicationContext(), Shop.class));
            }
        });

        promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fireLog("go_to_promo");
                startActivity(new Intent(getApplicationContext(), Promotion.class));
            }
        });

    }

    @Override
    protected void onStart() {
        SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);

        String[] katigories = getResources().getStringArray(R.array.categories);

        Boolean colorFlag = false;
        for (int i = 0; i < katigories.length - pfk; i++) {
            final String kat = katigories[i+pfk];
            if(!(agorasmena.getBoolean(kat,false))) {
                colorFlag = true;
            }
        }


        super.onStart();
    }

    public void fireLog(String epilogi){

        Bundle params = new Bundle();
        params.putString("epilogh xrhsth", "apo_menu");
        mFirebaseAnalytics.logEvent(epilogi, params);
    }

}
