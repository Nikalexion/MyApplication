package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;
import java.util.StringTokenizer;

public class LivesGame extends AppCompatActivity {
    RelativeLayout scoreBG;

    private TextView leksi;
    private String[] pinakasL; //pinakas lekseon
    private static final Random rgenerator = new Random();
    private CountDownTimer xronos;
    private MediaPlayer mp;
    private MediaPlayer expl;
    private int ligosXronos = 0;
    //i diafimisi
    private InterstitialAd mInterstitialAd;

    private boolean mistake = false;
    private int gameTime;
    private int startTime;
    private boolean stillPlaying = true;
    private Button lathos;
    Button nextButton;
    Button pasoButton;
    //arithmos tou  arithmos omadon, diarkia paixnidiou, arithmos paso
    int teamsValue;     //arithmos omadon
    int activeTeam = 0;     //arithmos omadas pou paizei
    int timeValue;
    int pasaValue;      //max pasa kathe paikti
    int arithmosPaso;   //prosorinos arithmos paso pou exei o kathe paiktis

    String[] onomataOmadon;
    int[] xromataOmadon;
    int[] scoreOmadon;

    TextView teamName;



    boolean doubleBackToExitPressedOnce = false;

    //elenxos gia diplo "back" gia na stamatisei to paixnidi
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
        setContentView(R.layout.activity_lives_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //travaei tis rithimseis apo ta sharedPreferences
        teamsValue = getSharedPreferences("EPILOGES", 0).getInt("teamModeTeams", 2);
        timeValue = getSharedPreferences("EPILOGES", 0).getInt("teamModeTime", 120);
        pasaValue = getSharedPreferences("EPILOGES", 0).getInt("teamModePasa", 3);
        activeTeam = getSharedPreferences("EPILOGES", 0).getInt("teamModeProtosPaiktis", 0);

        onomataOmadon = new String[teamsValue];
        xromataOmadon = new int[teamsValue];
        scoreOmadon = new int[teamsValue];
        //travaei poses zoes exei i kathe omada kai ta vazei sto "scoreOmadon" (score = zoes)
        String savedString = getSharedPreferences("EPILOGES", 0).getString("teamModeScoreOmadon", "1,1,0,0,");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        for (int i = 0; i < teamsValue; i++) {
            scoreOmadon[i] = Integer.parseInt(st.nextToken());
        }
        //Panta paizoun toulaxiston 2 omades, an einai 3 kai pano benei kai sta if kai tis ftiaxnei
        onomataOmadon[0] = getSharedPreferences("EPILOGES", 0).getString("onoma1", "omada 1");
        xromataOmadon[0] = getSharedPreferences("EPILOGES", 0).getInt("xroma1", 0);
        onomataOmadon[1] = getSharedPreferences("EPILOGES", 0).getString("onoma2", "omada 2");
        xromataOmadon[1] = getSharedPreferences("EPILOGES", 0).getInt("xroma2", 0);

        if (teamsValue >= 3) {
            onomataOmadon[2] = getSharedPreferences("EPILOGES", 0).getString("onoma3", "omada 3");
            xromataOmadon[2] = getSharedPreferences("EPILOGES", 0).getInt("xroma3", 0);
        }
        if (teamsValue >= 4) {
            onomataOmadon[3] = getSharedPreferences("EPILOGES", 0).getString("onoma4", "omada 4");
            xromataOmadon[3] = getSharedPreferences("EPILOGES", 0).getInt("xroma4", 0);
        }
        //vriskei to xroma tis kathe omadas
        for (int i = 0; i < teamsValue; i++){
            switch (xromataOmadon[i]){
                case 0:
                    xromataOmadon[i] = Color.CYAN;
                    break;
                case 1:
                    xromataOmadon[i] = Color.RED;
                    break;
                case 2:
                    xromataOmadon[i] = Color.BLUE;
                    break;
                case 3:
                    xromataOmadon[i] = Color.MAGENTA;
                    break;
                case 4:
                    xromataOmadon[i] = Color.YELLOW;
                    break;
                case 5:
                    xromataOmadon[i] = Color.WHITE;
                    break;
                case 6:
                    xromataOmadon[i] = Color.rgb(255,165,0);
                    break;
                case 7:
                    xromataOmadon[i] = Color.parseColor("#7C4100");
                    break;
            }
        }
        //arxikopoiei to text pou grafei poia omada paizei proti
        teamName = findViewById(R.id.onomaActiveOmadas);
        teamName.setText(onomataOmadon[activeTeam]);
        scoreBG = findViewById(R.id.activity_lives_game);
        scoreBG.setBackgroundColor(xromataOmadon[activeTeam]);


        //ftiaxnei ton xrono tou giro me tixea apoklisi mexri 20s gia na min borei kapoios na metraei me sigouria
        int min = timeValue;
        int max = timeValue + 20;
        gameTime = (rgenerator.nextInt(max - min + 1) + min) * 1000;
        startTime = gameTime;

        arithmosPaso = pasaValue;
        //arxikopoiei tous media player gia tous ixous
        mp = MediaPlayer.create(this, R.raw.beep);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        expl = MediaPlayer.create(this, R.raw.explosion);
        expl.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //ftiaxnei pinaka me tis lekseis
        arrayBuilder();

        leksi = (TextView) findViewById(R.id.leksiPaixnidiou);
        neaLeksi(); //travaei mia tixaia leksi apo ton pinaka pou ftiaxtike apo to arrayBuilder

        //to button gia epitiximeni leksi (kai allagi omadas)
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neaLeksi();
                //alagi omadas
                do{
                    activeTeam = activeTeam +1;
                    if (activeTeam >= teamsValue){
                        activeTeam = 0;
                    }
                }while(scoreOmadon[activeTeam] <= 0);

                teamName.setText(onomataOmadon[activeTeam]);
                scoreBG.setBackgroundColor(xromataOmadon[activeTeam]);

                //refresh paso
                if (pasaValue > 0){
                    arithmosPaso = pasaValue;
                    pasoButton.setEnabled(true);
                    if (pasaValue <= 5) {
                        pasoButton.setText(String.valueOf(arithmosPaso));
                    }
                }
            }
        });
        //to buttong gia paso
        pasoButton = findViewById(R.id.pasoButton);
        if (pasaValue <= 5) {
            pasoButton.setText(String.valueOf(arithmosPaso));
        }
        pasoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neaLeksi(); //allazei leksi kai meta mionei ta pasa kai elenxei an exoun minei kialla
                if (pasaValue <= 5) {
                    arithmosPaso = arithmosPaso - 1;
                    pasoButton.setText(String.valueOf(arithmosPaso));
                    if (arithmosPaso <= 0) {
                        pasoButton.setEnabled(false);
                    }
                }
            }
        });
        //apenergopoiei ta pasa an paizete xoris pasa (0 pasa)
        if (arithmosPaso <= 0){
            pasoButton.setEnabled(false);
        }
        //ligei to paixnidi an kapoios kanei lathos kai patisei tin vomva gia na to liksei
        lathos = findViewById(R.id.lathos);
        lathos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mistake = true;
                endGame();
            }
        });

        //banner ad
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRq1 = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C29EE19EA0561C4586ADCA4FBE4BFC9E")
                .addTestDevice("D735FD1E8FC5790A99D0863BA60E7780")
                .build();
        adView.loadAd(adRq1);

        //etoimazei tin Interstitial ad (full screen ad) sto telous tou girou
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5861682469694178/1479320640");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdClosed() {
                goToNextLevel();
            }
        });
        //sinexeia tou Interstitial ad
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
    //dixnei tin Interstitial ad (full screen ad) sto telos tou girou
    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else{
            goToNextLevel();
        }
    }

    // Apothikevei to score (i to xasimo mias zois) kai paei sto ScoreScreen i to LivesScreen
    private void goToNextLevel() {

        //etoimazei to neo score apo tin arxi (overwrite) me tin omada pou exei xasei 1 zoi sto endGame
        StringBuilder scoreGiaMetafora = new StringBuilder();
        for (int i = 0; i < teamsValue; i++) {
            scoreGiaMetafora.append(scoreOmadon[i]).append(",");
        }

        SharedPreferences sp = getSharedPreferences("EPILOGES", 0);
        SharedPreferences.Editor editor = sp.edit();
        //an kapoia omada apoklistei, vriskei tin epomeni omada stin seira, i opoia paizei proti ston anerxomeno giro
        while(scoreOmadon[activeTeam] <= 0){
            activeTeam = activeTeam +1;
            if (activeTeam >= teamsValue){
                activeTeam = 0;
            }
        }
        //pernaei ta score/zoes kai to poios paizei protos
        editor.putInt("teamModeProtosPaiktis", activeTeam);
        editor.putString("teamModeScoreOmadon", scoreGiaMetafora.toString());
        editor.apply();


        startActivity(new Intent(getApplicationContext(), LivesScreen.class));
        finish();
    }
    //travaei lekseis se enan pinaka lekseon simfona me tis epilegmenes katigories (sto Shop activity)
    private void arrayBuilder() {
        SharedPreferences epiloges = getSharedPreferences("EPILOGES", 0);

        String[] katigories = getResources().getStringArray(R.array.categories);
        int Array_Count = katigories.length;

        for (int i = 0; i < Array_Count; i++) {
            if (epiloges.getBoolean(getResources().getStringArray(R.array.categories)[i], false)) {
                int getResHelper = getResources().getIdentifier(katigories[i], "array", getPackageName());
                String[] concatHelper = getResources().getStringArray(getResHelper);
                pinakasL = concat(pinakasL, concatHelper);
            }
        }
        //an den exei epilexthei KAMIA katigoria, tote apla paizei me tin katigoria "tixaies lekseis"
        if (pinakasL == null) {
            pinakasL = getResources().getStringArray(R.array.random);
        }
    }
    //H "concat" enonei kai epistrefei 2 pinakes pou tis dinontai
    //an stin thesi "a" dothei kenos pinakas apla epistrefei ton pinaka b
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

    //H onResume kanei to paixnidi na sinexisei na doulevei kanonika meta to minimize tis efarmogis
    //episeis i onResume trexei molis ksekinisei o giros, opote to "timeCreator" trexei apefthias
    //afou to "stillPlaying" einai stin arxi true
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

    //kanei pafsi to paixnidi otan ginete minimize i efarmogi
    @Override
    protected void onPause() {
        showSystemUI();
        xronos.cancel();
        super.onPause();
    }

    //H timeCreator ftiaxnei tous diafoterikous ixous analoga me to poso xronos emeine
    //kai kanei kai tin miosi tou xronou ftiaxnontas enan CountDownTimer
    //oi "ticker" xrisimevoun sto na akougete pio grigora o ixos tis vomvas
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
                //elenxei se kathe tick an exei paei sta simia allagis ixou
                //(1/4, 2/4, 3/4, kai elenxei kai tin metavliti "ligosXronos"
                //gia na dei an exei allaxtei o ticker, allios ton allazei
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
            //otan liksei o xronos ligei to paixnidi
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
    //ksekinaei tin liksei tou paixnidiou apenergopoiei arketa pragmata
    //anakoinonei tin liksei tou girou kai trexei to "lastClock"
    //to opoio einai 2 defterolepta prin to transition sto LivesScreen/ScoreScreen
    public void endGame(){
        lathos.setEnabled(false);
        lathos.setVisibility(View.INVISIBLE);
        xronos.cancel();
        mp.stop();
        mp.release();
        mp = null;
        expl.start();
        lathos.setEnabled(false);
        pasoButton.setEnabled(false);
        nextButton.setEnabled(false);
        stillPlaying = false;
        if (mistake){
            leksi.setText("Η Ομάδα σου έχασε!");
        }else{
            leksi.setText("Τέλος Χρόνου! Η Ομάδα σου έχασε!");
        }
        //xanei 1 zoi
        scoreOmadon[activeTeam] = scoreOmadon[activeTeam] - 1;
        gameTime = 2 * 1000;
        lastClock(gameTime); //2 defterolepta prin tin metavasi stin epomeni othoni
    }
    //den kanei kati apla perimenei na perasei o xronos kai na fonaksei to "afterEnd"
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
    //klinei to telefteo arxeio ixou (to expl), dixnei tin diafimisi kai paei stin epomeni othoni
    public void afterEnd(){
        expl.stop();
        expl.release();
        expl = null;
        xronos.cancel();
        showInterstitial(); //dixnei tin diafimisi
    }
    //travaei mia tixea leksei apo ton pinaka "pinakasL"
    public void neaLeksi(){
        leksi.setText(pinakasL[rgenerator.nextInt(pinakasL.length)]);
    }

}




