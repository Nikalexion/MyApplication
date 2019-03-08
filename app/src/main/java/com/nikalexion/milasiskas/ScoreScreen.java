package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;

public class ScoreScreen extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";

    int maxScoreValue;  //to score gia liksi tou paixnidiou
    int teamsValue;     //arithmos omadon

    TextView teamScoringHelper;
    String[] onomataOmadon;
    int[] scoreOmadon;
    Boolean gameStillGoing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        maxScoreValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeScore", 3);
        teamsValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTeams", 2);

        onomataOmadon = new String[teamsValue];
        scoreOmadon = new int[teamsValue];

        //travaei ta score apo ta preferences
        String savedString = getSharedPreferences(PREF_EPILOGES, 0).getString("teamModeScoreOmadon", "0,0,0,0,");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        for (int i = 0; i < teamsValue; i++) {
            scoreOmadon[i] = Integer.parseInt(st.nextToken());
            if (scoreOmadon[i]>= maxScoreValue){
                gameStillGoing = false;
            }
        }


        //TODO ola afta dinamika
        teamScoringHelper = findViewById(R.id.score1);
        onomataOmadon[0] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma1", "omada 1");
        teamScoringHelper.setText(onomataOmadon[0] + ": " + scoreOmadon[0]);

        teamScoringHelper = findViewById(R.id.score2);
        onomataOmadon[1] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma2", "omada 2");
        teamScoringHelper.setText(onomataOmadon[1] + ": " + scoreOmadon[1]);

        teamScoringHelper = findViewById(R.id.score3);
        if (teamsValue >= 3) {
            onomataOmadon[2] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma3", "omada 3");
            teamScoringHelper.setText(onomataOmadon[2] + ": " + scoreOmadon[2]);
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        teamScoringHelper = findViewById(R.id.score4);
        if (teamsValue >= 4) {
            onomataOmadon[3] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma4", "omada 4");
            teamScoringHelper.setText(onomataOmadon[3] + ": " + scoreOmadon[3]);
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        //To koumbi gia ton epomeno giro
        Button epomenosGiros = findViewById(R.id.nextRoundButton);
        if (gameStillGoing) {
            epomenosGiros.setText("Next Round");
        }else{
            epomenosGiros.setText("Reset scores");
        }
        epomenosGiros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (!gameStillGoing) {
                    //midenismos ton score
                    SharedPreferences sp = getSharedPreferences(PREF_EPILOGES, 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("teamModeScoreOmadon", "0,0,0,0,");
                    editor.apply();
                }
                    startActivity(new Intent(getApplicationContext(), ScoreGame.class));
                    finish();
            }
        });

        //TODO koumbi gia arxiko menu
        //TODO anakoinosi nikiti
        //TODO isopalies?
    }
}
