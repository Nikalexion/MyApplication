package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;

public class MainGame extends AppCompatActivity {


    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_EPILOGES = "EPILOGES";

    private TextView leksi;
    private String[] pinakasL;
    private static final Random rgenerator = new Random();
    private String tixeaLeksi;
    private CountDownTimer xronos;
    private MediaPlayer mp;
    private MediaPlayer expl;
    private int ligosXronos = 0;
    //i diafimisi
    private InterstitialAd mInterstitialAd;

    private boolean mistake = false;
    private int gameTime;
    private int startTime;
    LinearLayout layclick;
    private boolean stillPlaying = true;
    private Button lathos;

    private FirebaseAnalytics mFirebaseAnalytics;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            mp.stop();
            mp.release();
            mp = null;
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Πατήστε ξανά για έξοδο απο το παιχνίδι", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        int min = getSharedPreferences(PREF_EPILOGES, 0).getInt("minTime", 60);
        int max = getSharedPreferences(PREF_EPILOGES, 0).getInt("maxTime", 80);
        gameTime = (rgenerator.nextInt(max - min + 1) + min) * 1000;
        startTime = gameTime;

        mp = MediaPlayer.create(this, R.raw.beep);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        expl = MediaPlayer.create(this, R.raw.explosion);
        expl.setAudioStreamType(AudioManager.STREAM_MUSIC);


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

        lathos = (Button) findViewById(R.id.lathos);
        lathos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mistake = true;
                endGame();
            }
        });


        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRq1 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C29EE19EA0561C4586ADCA4FBE4BFC9E")
                .addTestDevice("D735FD1E8FC5790A99D0863BA60E7780")
                .build();
        adView.loadAd(adRq1);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5861682469694178/1479320640");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

            }
        });


        AdRequest adRq2 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C29EE19EA0561C4586ADCA4FBE4BFC9E")
                .addTestDevice("D735FD1E8FC5790A99D0863BA60E7780")
                .build();
        mInterstitialAd.loadAd(adRq2);

    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void goToNextLevel() {
        // An patiseis X sto ad i den fortisei se paei sto epomeno activity
        startActivity(new Intent(getApplicationContext(), Ending.class));
        finish();
    }

    private void arrayBuilder() {
        SharedPreferences epiloges = getSharedPreferences(PREF_EPILOGES, 0);

        String[] katigories = getResources().getStringArray(R.array.categories);
        int Array_Count = katigories.length;

        for (int i = 0; i < Array_Count; i++) {
            if (epiloges.getBoolean(getResources().getStringArray(R.array.categories)[i], false)) {
                int getResHelper = getResources().getIdentifier(katigories[i], "array", getPackageName());
                String[] concatHelper = getResources().getStringArray(getResHelper);
                pinakasL = concat(pinakasL, concatHelper);
            }
        }

        if (pinakasL == null) {
            pinakasL = getResources().getStringArray(R.array.random);
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
        hideSystemUI();
        if (stillPlaying){
            timeCreator(gameTime);
        }else{
            lastClock(gameTime);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        showSystemUI();
        xronos.cancel();
        super.onPause();
    }

    private void timeCreator(final int timeToFinish) {
        int ticker = 2000;
        if (gameTime < startTime/4){
            ticker = 500;
        }
        else if (gameTime < startTime*2/4){
            ticker = 1000;
        }
        else if (gameTime < startTime*3/4) {
            ticker = 1500;
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
                gameTime = 0;
                endGame();
            }
        }.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    public void endGame(){
        lathos.setEnabled(false);
        lathos.setVisibility(View.INVISIBLE);
        xronos.cancel();
        mp.stop();
        mp.release();
        mp = null;
        expl.start();
        layclick.setClickable(false);
        stillPlaying = false;
        if (mistake){
            leksi.setText("Η Ομάδα σου έχασε!");
        }else{
            leksi.setText("Τέλος Χρόνου! Η Ομάδα σου έχασε!");
        }
        //prosthiki xrimaton
        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        SharedPreferences.Editor lefta_editor = lefta.edit();

        //prosthiki ton poson game epaikse o sigekrimenos xristis
        int counter = lefta.getInt("games", 0);
        counter = counter + 1;
        lefta_editor.putInt("games", counter);
        lefta_editor.apply();

        //katagrafi telous game
        Bundle params = new Bundle();
        params.putInt("time_played",getSharedPreferences(PREF_EPILOGES, 0).getInt("minTime", 60));
        params.putInt("games_played", counter);
        params.putBoolean("mistake_was_made",mistake);
        mFirebaseAnalytics.logEvent("round_ended", params);

        gameTime = 3 * 1000;
        lastClock(gameTime);
    }

    private void lastClock(final int timeToFinish) {
        int ticker = 1000;
        xronos = new CountDownTimer(timeToFinish, ticker) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                afterEnd();
            }
        }.start();
    }

    public void afterEnd(){
        expl.stop();
        expl.release();
        expl = null;
        xronos.cancel();
        showInterstitial(); //dixnei tin diafimisi
        goToNextLevel();    //paei sto ending PRIN tin diafimisi gia na min fenete periergo
    }

}






