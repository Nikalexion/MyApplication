package com.nikalexion.milasiskas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
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

public class ScoreGame extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";
    int timeValue;
    int pasaValue;

    TextView timeText;
    TextView pasaText;
    Button pasoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_game);

        timeText = findViewById(R.id.timeText);
        pasaText = findViewById(R.id.pasaText);

        timeValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("scoreTime", 120);
        pasaValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("scorePasa", 3);

        timeText.setText(String.valueOf(timeValue) + " δευτερόλεπτα παιχνιδιού");
        pasaText.setText(String.valueOf(pasaValue) + " πάσα απομένουν");

        pasoButton = findViewById(R.id.pasoButton);
        pasoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                pasaValue = pasaValue - 1;
                pasaText.setText(String.valueOf(pasaValue) + " πάσα απομένουν");
                if (pasaValue <= 0){
                    pasoButton.setEnabled(false);
                }
            }
        });
        if (pasaValue <= 0){
            pasoButton.setEnabled(false);
        }

    }
}
