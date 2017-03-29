package com.nikalexion.milasiskas;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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


    String versionName = BuildConfig.VERSION_NAME;

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

        vChecker();

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

    protected void vChecker () {

        new GetLatestVersion();

        String tag = "LOG_TAG";

        String latestVersion = "";
        String currentVersion = versionName;
        Log.d(tag, "Current version = " + currentVersion);
        try {
            latestVersion = new GetLatestVersion().execute().get();
            Log.d(tag, "Latest version = " + latestVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(!currentVersion.equals(latestVersion)) {
            AlertDialog alertDialog;
            ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyAlertDialogStyle);
            AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
            alertDialog = builder.create();
            alertDialog.getWindow().setLayout(200, 400);


            builder.setTitle("Νέο Update");
            //Start setting up the builder
            builder.setMessage("Ένα νέο update είναι διαθέσιμο στο google play. Θέλετε να το κατεβάσετε;");

            // Add the buttons
            builder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        Intent rateIntent = rateIntentForUrl(("market://details"));
                        startActivity(rateIntent);
                    } catch (ActivityNotFoundException e) {
                        Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                        startActivity(rateIntent);
                    }

                }
            });
            builder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Set other dialog properties

            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {


                }
            });
            dialog.show();
        }
    }

    protected Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private class GetLatestVersion extends AsyncTask<String, String, String> {
        String latestVersion;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //It retrieves the latest version by scraping the content of current version from play store at runtime
                String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                latestVersion = doc.getElementsByAttributeValue("itemprop","softwareVersion").first().text();

            }catch (Exception e){
                e.printStackTrace();

            }

            return latestVersion;
        }
    }

}
