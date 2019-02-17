package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.TextView;

public class OptionPicker extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";

    //metavlites gia to time seekbar
    SeekBar timeSeekBar;
    TextView timeText;

    //prosoxi i praksi (max-min)/step na vgainei panta int arithmos
    int timeStep = 10;
    int timeMax = 180;
    int timeMin = 60;
    //travaei apo ta preferences tin arxiki timi tou timeValue, an den yparxei vazei 120
    int timeValue = 120;
    //metavlites gia to pasa seekbar
    SeekBar pasaSeekBar;
    TextView pasaText;

    int pasaStep = 1;
    int pasaMax = 6;
    int pasaMin = 0;
    //travaei apo ta preferences tin arxiki timi tou pasaValue, an den yparxei vazei 3
    int pasaValue = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_picker);
        timeValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("scoreTime", 120);
        pasaValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("scorePasa", 3);

        timeBarController();
        pasaBarController();

        Button launchGame = findViewById(R.id.launchScoreModeButton);
        launchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SharedPreferences sp = getSharedPreferences(PREF_EPILOGES, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("scoreTime", timeValue);
                editor.putInt("scorePasa", pasaValue);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), ScoreGame.class));
                finish();
            }
        });

    }

    private void timeBarController(){
        timeSeekBar = findViewById(R.id.timeBar);
        timeText = findViewById(R.id.timeText);

        //prosoxi i praksi na vgainei panta int arithmos!
        timeSeekBar.setMax( (timeMax - timeMin) / timeStep);
        timeSeekBar.setProgress( (timeValue - timeMin) / timeStep);
        timeText.setText(String.valueOf(timeValue) + " δευτερόλεπτα παιχνιδιού");

        // otan allazei to to seekerBar ginete apefthias ananeosi tou ti grafei apo kato
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                timeValue = timeMin + (progress * timeStep);
                timeText.setText(String.valueOf(timeValue) + " δευτερόλεπτα παιχνιδιού");
            }
        });

    }

    private void pasaBarController(){
        pasaSeekBar = findViewById(R.id.pasaBar);
        pasaText = findViewById(R.id.pasaText);

        //prosoxi i praksi na vgainei panta int arithmos!
        pasaSeekBar.setMax( (pasaMax - pasaMin) / pasaStep);
        pasaSeekBar.setProgress(pasaValue);
        pasaText.setText(arithmosPason(pasaValue));

        // otan allazei to to seekerBar ginete apefthias ananeosi tou ti grafei apo kato
        pasaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                pasaValue = pasaMin + (progress * pasaStep);
                pasaText.setText(arithmosPason(pasaValue));
            }
        });
    }

    //ekfrazei to posa paso exei dialeksei o paiktis se sosti protasi analoga tis epiloges
    private String arithmosPason(int kodikosPason){
        if(kodikosPason == 6) {
            return "Απεριόριστος αριθμός πάσο";
        }else if (kodikosPason == 0){
            return "Χωρίς χρήση πάσο";
        }else{
            return "Μέχρι "+ String.valueOf(pasaValue)+" πάσο ανά παίκτη" ;
        }
    }
}
