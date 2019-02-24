package com.nikalexion.milasiskas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LivesGame extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";

    int scoreValue;
    int teamsValue;
    int timeValue;
    int pasaValue;

    TextView scoreText;
    TextView teamsText;
    TextView timeText;
    TextView pasaText;
    Button pasoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lives_game);

        scoreText = findViewById(R.id.scoreText);
        teamsText = findViewById(R.id.teamsText);
        timeText = findViewById(R.id.timeText);
        pasaText = findViewById(R.id.pasaText);

        scoreValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeScore", 120);
        teamsValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTeams", 3);
        timeValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTime", 120);
        pasaValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModePasa", 3);

        scoreText.setText(String.valueOf(scoreValue) + " ζωές απομένουν");
        teamsText.setText(String.valueOf(teamsValue) + " ομάδες");
        timeText.setText(String.valueOf(timeValue) + " δευτερόλεπτα παιχνιδιού");
        if (pasaValue <=5){
            pasaText.setText(String.valueOf(pasaValue) + " πάσα απομένουν");
        }else{
            pasaText.setText("Απεριόριστος αριθμός πάσων");
        }

        pasoButton = findViewById(R.id.pasoButton);
        pasoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (pasaValue <=5){
                    pasaValue = pasaValue - 1;
                    pasaText.setText(String.valueOf(pasaValue) + " πάσα απομένουν");
                    if (pasaValue <= 0){
                        pasoButton.setEnabled(false);
                    }
                }
            }

        });
        if (pasaValue <= 0){
            pasoButton.setEnabled(false);
        }

    }
}
