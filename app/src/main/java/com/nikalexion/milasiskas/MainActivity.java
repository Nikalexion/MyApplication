package com.nikalexion.milasiskas;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_AGORES = "AGORES";
    public static final String PREF_EPILOGES = "EPILOGES";

    private Button shop;
    private TextView leftakia;
    private FirebaseAnalytics mFirebaseAnalytics;


    //String versionName = BuildConfig.VERSION_NAME;

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


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
        shop = (Button) findViewById(R.id.shop);
        Button promotion = (Button) findViewById(R.id.promotion);
        leftakia = (TextView) findViewById(R.id.leftakia);


        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fireLog("start_new_round");
                startActivity(new Intent(getApplicationContext(), TimePicker.class));
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
        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        int counter = lefta.getInt("lefta", 0);
        leftakia.setText(Integer.toString(counter));
        leftakia.setTextColor(Color.YELLOW);
        SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);

        String[] kostiKatigorion = getResources().getStringArray(R.array.costs);
        String[] katigories = getResources().getStringArray(R.array.categories);

        Boolean colorFlag = false;
        for (int i = 0; i < katigories.length - pfk; i++) {
            final String kat = katigories[i+pfk];
            final String cost = kostiKatigorion[i+pfk];
            if(!(agorasmena.getBoolean(kat,false))&& (counter >= Integer.parseInt(cost))) {
                colorFlag = true;
            }
        }

        if (colorFlag) {
            shop.setBackgroundResource(R.drawable.shop_purchasable);
        }else{
            shop.setBackgroundResource(R.drawable.btn_bg);
        }

        super.onStart();
    }

    public void fireLog(String epilogi){
        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        int counter = lefta.getInt("lefta", 0);

        Bundle params = new Bundle();
        params.putString("lefta", Integer.toString(counter));
        mFirebaseAnalytics.logEvent(epilogi, params);
    }

}
