package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
    int highScore = 0;
    int winningTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        maxScoreValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeScore", 3);
        teamsValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTeams", 2);

        onomataOmadon = new String[teamsValue];
        scoreOmadon = new int[teamsValue];



        /*  TODO nomizo kapos etsi ginete to dinamiko (thelei kai tags sto layout)
        RelativeLayout parentView = findViewById(R.id.activity_score_screen);
        //teamScoringHelper = parentView.findViewWithTag(0);
        String nameHelper;
        for (int i = 0; i < teamsValue; i++) {
            nameHelper = "onoma" + String.valueOf(i+1);
            teamScoringHelper = parentView.findViewWithTag(i);
            onomataOmadon[i] = getSharedPreferences(PREF_EPILOGES, 0).getString(nameHelper, "omada 1");
            teamScoringHelper.setText(onomataOmadon[i] + ": " + scoreOmadon[i]);
        }
        */

        onomataOmadon[0] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma1", "omada 1");
        onomataOmadon[1] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma2", "omada 2");
        if (teamsValue >= 3) {
            onomataOmadon[2] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma3", "omada 3");
        }
        if (teamsValue >= 4) {
            onomataOmadon[3] = getSharedPreferences(PREF_EPILOGES, 0).getString("onoma4", "omada 4");
        }

        //travaei ta score apo ta preferences
        String savedString = getSharedPreferences(PREF_EPILOGES, 0).getString("teamModeScoreOmadon", "0,0,0,0,");
        StringTokenizer st = new StringTokenizer(savedString, ",");


        //elenxos gia nikiti kai isopalies
        for (int i = 0; i < teamsValue; i++) {
            scoreOmadon[i] = Integer.parseInt(st.nextToken());

            if (scoreOmadon[i]>= maxScoreValue){
                if (scoreOmadon[i] > highScore){
                    highScore = scoreOmadon[i];
                    winningTeam = i;
                    gameStillGoing = false;
                }else if(scoreOmadon[i] == highScore){
                    gameStillGoing = true;
                }
            }
        }

        //TODO OLA afta dinamika OPOSDIPOTE
        teamScoringHelper = findViewById(R.id.score1);
        if (gameStillGoing) {
            teamScoringHelper.setText(onomataOmadon[0] + ": " + scoreOmadon[0]);
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        teamScoringHelper = findViewById(R.id.score2);
        if (gameStillGoing) {
            teamScoringHelper.setText(onomataOmadon[1] + ": " + scoreOmadon[1]);
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        teamScoringHelper = findViewById(R.id.score3);
        if (gameStillGoing) {
            if (teamsValue >= 3) {
                teamScoringHelper.setText(onomataOmadon[2] + ": " + scoreOmadon[2]);
            } else {
                teamScoringHelper.setVisibility(View.INVISIBLE);
                teamScoringHelper.setEnabled(false);
            }
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        teamScoringHelper = findViewById(R.id.score4);
        if (gameStillGoing) {
            if (teamsValue >= 4) {
                teamScoringHelper.setText(onomataOmadon[3] + ": " + scoreOmadon[3]);
            } else {
                teamScoringHelper.setVisibility(View.INVISIBLE);
                teamScoringHelper.setEnabled(false);
            }
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        //To koumbi gia epanafora stin arxiki
        Button arxikoMenu = findViewById(R.id.newGame);

        //To koumbi gia ton epomeno giro
        Button epomenosGiros = findViewById(R.id.nextRoundButton);

        //An den exei liksei, krivei to koumbi gia tin arxiki, allios tou vazei setOnClickListener kai allazei to text allou koumbiou
        if (gameStillGoing) {
            arxikoMenu.setVisibility(View.INVISIBLE);
            arxikoMenu.setEnabled(false);
            if (highScore == 0){
                epomenosGiros.setText("ΕΠΟΜΕΝΟΣ ΓΥΡΟΣ");
            }else{
                epomenosGiros.setText("ΙΣΟΠΑΛΙΑ\n ΕΠΟΜΕΝΟΣ ΓΥΡΟΣ");
            }
            //eksafanizei tin anakoinosi nikiti
            teamScoringHelper = findViewById(R.id.winnerAnnouncer);
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);

        }else{
            arxikoMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            epomenosGiros.setText("ΜΗΔΕΝΙΣΜΟΣ ΣΚΟΡ");

            teamScoringHelper = findViewById(R.id.winnerAnnouncer);
            teamScoringHelper.setText("Νίκησε η ομάδα "+ onomataOmadon[winningTeam] +" με "+ highScore +" ποντους");
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
    }
}
