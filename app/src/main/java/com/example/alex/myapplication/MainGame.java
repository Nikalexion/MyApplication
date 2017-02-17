package com.example.alex.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class MainGame extends AppCompatActivity {


    // TODO Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown.";
    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_EPILOGES = "EPILOGES";

    private TextView leksi;
    private String[] pinakasL;
    private static final Random rgenerator = new Random();
    private String tixeaLeksi;
    private CountDownTimer xronos;
    private CountDownTimer telosXronou;
    private MediaPlayer mp;
    private MediaPlayer expl;
    private int ligosXronos = 0;
    //i diafimisi
    private InterstitialAd mInterstitialAd;

    private int min = 5;
    private int max = 8;
    private int gameTime = (rgenerator.nextInt(max - min + 1) + min) * 1000;
    private final int startTime = gameTime;
    LinearLayout layclick;
    private boolean stillPlaying = true;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            mp.stop();
            super.onBackPressed();
            return;
        }
        //TODO keimeno sta ellinika
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mp = MediaPlayer.create(this, R.raw.beep);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        expl = MediaPlayer.create(this, R.raw.explosion);
        expl.setAudioStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        layclick = (LinearLayout) findViewById(R.id.layouter);
        layclick.setClickable(true);

        arrayBuilder();

        leksi = (TextView) findViewById(R.id.leksiPaixnidiou);
        tixeaLeksi = pinakasL[rgenerator.nextInt(pinakasL.length)];
        leksi.setText(tixeaLeksi);


        layclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void arrayBuilder() {
        SharedPreferences epiloges = getSharedPreferences(PREF_EPILOGES, 0);
        if (epiloges.getBoolean("random", true)) {
            pinakasL = getResources().getStringArray(R.array.random_array);
        }
        if (epiloges.getBoolean("tainiesENG", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.tainiesENG_array));
        }
        if (epiloges.getBoolean("parimies", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.parimies_array));
        }
        if (epiloges.getBoolean("tainiesGR", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.tainiesGR_array));
        }
        if (epiloges.getBoolean("paidika", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.paidika_array));
        }
        if (epiloges.getBoolean("tvGR", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.tvGR_array));
        }
        if (epiloges.getBoolean("myths", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.myths_array));
        }
        if (epiloges.getBoolean("food", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.food_array));
        }
        if (epiloges.getBoolean("animals", true)) {
            pinakasL = concat(pinakasL, getResources().getStringArray(R.array.animals_array));
        }
        //TODO prosorino fix asfalias min ksekinisei to paixnidi xoris pinaka
        if (pinakasL == null) {
            pinakasL = getResources().getStringArray(R.array.random_array);
        }
    }

    private String[] concat(String[] a, String[] b) {
        //fix gia ton proto pinaka pou paei na enothei
        if (a == null) {
            return b;
        }
        int aLen = a.length;
        int bLen = b.length;
        String[] c = new String[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    @Override
    protected void onResume() {
        timeCreator(gameTime);
        super.onResume();
    }

    @Override
    protected void onPause() {
        xronos.cancel();
        super.onPause();
    }

    private void timeCreator(final int timeToFinish) {
        int ticker = 1000;
        if (gameTime < startTime/4){
            ticker = 100;
        }
        else if (gameTime < startTime*2/4){
            ticker = 300;
        }
        else if (gameTime < startTime*3/4) {
            ticker = 600;
        }
        xronos = new CountDownTimer(timeToFinish, ticker) {
            public void onTick(long millisUntilFinished) {
                gameTime = (int) millisUntilFinished;
                mp.start();
                if (gameTime < startTime/4){
                    if (ligosXronos == 2) {
                        xronos.cancel();
                        timeCreator(gameTime);
                        ligosXronos = 3;
                    }
                }
                else if (gameTime < startTime*2/4){
                    if (ligosXronos == 1) {
                        xronos.cancel();
                        timeCreator(gameTime);
                        ligosXronos = 2;
                    }
                }
                else if (gameTime < startTime*3/4) {
                    if (ligosXronos == 0){
                        xronos.cancel();
                        timeCreator(gameTime);
                        ligosXronos = 1;
                    }
                }
            }

            public void onFinish() {
                if (stillPlaying) {
                    xronos.cancel();
                    mp.stop();
                    expl.start();
                    layclick.setClickable(false);
                    leksi.setText("Τέλος Χρόνου! Η Ομάδα σου έχασε!");
                    stillPlaying = false;
                    gameTime = 3 * 1000;
                    timeCreator(gameTime);
                } else {
                    expl.stop();
                    SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
                    int counter = lefta.getInt("lefta", 0);
                    counter = counter + 5;
                    SharedPreferences.Editor lefta_editor = lefta.edit();
                    lefta_editor.putInt("lefta", counter);
                    lefta_editor.apply();
                    showInterstitial(); //emberiexei to goToNextLevel me ta intent
                    xronos.cancel();
                    finish();   //psofaei otan telionei o xronos
                }
            }
        }.start();
    }
}






