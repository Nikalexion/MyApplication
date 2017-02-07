package com.example.alex.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;
import android.os.CountDownTimer;
import android.widget.Toast;


//ton ads
import com.google.android.gms.ads.AdRequest;
//ton fullscreen ads
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
//ton banner ads
import com.google.android.gms.ads.AdView;

public class MainGame extends AppCompatActivity {


    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_EPILOGES = "EPILOGES";

    private TextView leksi;
    private String[] pinakasL;
    private static final Random rgenerator = new Random();
    private String tixeaLeksi;
    private CountDownTimer xronos;
    private CountDownTimer telosXronou;
    //i diafimisi
    private InterstitialAd mInterstitialAd;

    private int min = 5;
    private int max = 8;
    private int gameTime = (rgenerator.nextInt(max - min + 1) + min) * 1000;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        xronos.cancel();
        telosXronou.cancel();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        final LinearLayout layclick = (LinearLayout) findViewById(R.id.layouter);
        layclick.setClickable(true);

        xronos = new CountDownTimer(gameTime, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                layclick.setClickable(false);
                leksi.setText("Τέλος Χρόνου! Η Ομάδα σου έχασε!");
                telosXronou.start();
                xronos.cancel();
            }
        }.start();

        telosXronou = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
                int counter = lefta.getInt("lefta", 0);
                counter = counter + 5;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", counter);
                lefta_editor.apply();
                showInterstitial();
                xronos.cancel();
            }
        };

        arrayBuilder();

        leksi = (TextView) findViewById(R.id.leksiPaixnidiou);
        tixeaLeksi = pinakasL[rgenerator.nextInt(pinakasL.length)];
        leksi.setText(tixeaLeksi);


        layclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                tixeaLeksi = pinakasL[rgenerator.nextInt(pinakasL.length)];
                leksi.setText(tixeaLeksi);
            }
        });

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRq1 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRq1);

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Proceed to the next level on X.
                goToNextLevel();
            }
        });

        AdRequest adRq2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAd.loadAd(adRq2);


        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();

    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void goToNextLevel() {
        // An patiseis X sto ad i den fortisei se paei sto epomeno activity
        startActivity(new Intent(getApplicationContext(), Ending.class));
    }

    private void arrayBuilder(){
        SharedPreferences epiloges = getSharedPreferences(PREF_EPILOGES, 0);
        if(epiloges.getBoolean("tainiesENG",true)) {
            pinakasL = getResources().getStringArray(R.array.tainiesENG_array);
        }
        if(epiloges.getBoolean("parimies",true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.parimies_array));
        }
        if(epiloges.getBoolean("tainiesGR",true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.tainiesGR_array));
        }
        if(epiloges.getBoolean("padika",true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.paidika_array));
        }
        if(epiloges.getBoolean("tvGR",true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.tvGR_array));
        }
        //prosorini fix asfalias min ksekinisei to paixnidi xoris pinaka
        if(pinakasL == null){
            pinakasL = getResources().getStringArray(R.array.tainiesENG_array);
        }
    }

    private String[] concat(String[] a, String[] b) {
        //fix gia ton proto pinaka pou paei na enothei
        if(a==null){
            return b;
        }
        int aLen = a.length;
        int bLen = b.length;
        String[] c= new String[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}


