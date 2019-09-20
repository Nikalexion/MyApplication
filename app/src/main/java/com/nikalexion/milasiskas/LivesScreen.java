package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.StringTokenizer;

public class LivesScreen extends AppCompatActivity {

    int scoreValue;  //to score gia liksi tou paixnidiou
    int teamsValue;  //arithmos omadon
    int teamsAlive; //airthmos zontanon omadon

    TextView teamScoringHelper;
    String[] onomataOmadon;
    int[] scoreOmadon;
    Boolean gameStillGoing = true;
    int winningTeam;
    ImageView niki;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lives_screen);

        scoreValue = getSharedPreferences("EPILOGES", 0).getInt("teamModeScore", 3);
        teamsValue = getSharedPreferences("EPILOGES", 0).getInt("teamModeTeams", 2);
        teamsAlive = teamsValue;

        onomataOmadon = new String[teamsValue];
        scoreOmadon = new int[teamsValue];

        niki = findViewById(R.id.imageView);
        niki.setVisibility(View.INVISIBLE);
        mp = MediaPlayer.create(this, R.raw.winner);


        onomataOmadon[0] = getSharedPreferences("EPILOGES", 0).getString("onoma1", "omada 1");
        onomataOmadon[1] = getSharedPreferences("EPILOGES", 0).getString("onoma2", "omada 2");
        if (teamsValue >= 3) {
            onomataOmadon[2] = getSharedPreferences("EPILOGES", 0).getString("onoma3", "omada 3");
        }
        if (teamsValue >= 4) {
            onomataOmadon[3] = getSharedPreferences("EPILOGES", 0).getString("onoma4", "omada 4");
        }

        //travaei tis zoes apo ta preferences
        String savedString = getSharedPreferences("EPILOGES", 0).getString("teamModeScoreOmadon", "1,0,0,0,");
        StringTokenizer st = new StringTokenizer(savedString, ",");


        //elenxos gia plithos zontanon
        for (int i = 0; i < teamsValue; i++) {
            //gemisma tou pinaka scoreOmadon[i]
            scoreOmadon[i] = Integer.parseInt(st.nextToken());
            if (scoreOmadon[i]<= 0){
                teamsAlive = teamsAlive - 1;
            }else{
                winningTeam = i;
            }
        }
        if (teamsAlive <= 1){
            gameStillGoing = false;
        }

        teamScoringHelper = findViewById(R.id.score1);
        if (gameStillGoing) {
            teamScoringHelper.setText(onomataOmadon[0] + ": " + scoreOmadon[0] + "Ζωές");
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        teamScoringHelper = findViewById(R.id.score2);
        if (gameStillGoing) {
            teamScoringHelper.setText(onomataOmadon[1] + ": " + scoreOmadon[1] + "Ζωές");
        }else{
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);
        }

        teamScoringHelper = findViewById(R.id.score3);
        if (gameStillGoing) {
            if (teamsValue >= 3) {
                teamScoringHelper.setText(onomataOmadon[2] + ": " + scoreOmadon[2] + "Ζωές");
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
                teamScoringHelper.setText(onomataOmadon[3] + ": " + scoreOmadon[3] + "Ζωές");
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
            epomenosGiros.setText("Επομενοσ Γυροσ");

            //eksafanizei tin anakoinosi nikiti
            teamScoringHelper = findViewById(R.id.winnerAnnouncer);
            teamScoringHelper.setVisibility(View.INVISIBLE);
            teamScoringHelper.setEnabled(false);

        }else{
            mp.start();
            niki.setVisibility(View.VISIBLE);
            arxikoMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            epomenosGiros.setText("Επανεκκινηση");

            teamScoringHelper = findViewById(R.id.winnerAnnouncer);
            teamScoringHelper.setText("Νίκησε η ομάδα "+ onomataOmadon[winningTeam]);
        }

        epomenosGiros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (!gameStillGoing) {
                    //midenismos ton score
                    SharedPreferences sp = getSharedPreferences("EPILOGES", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < teamsValue; i++) {
                        str.append(scoreValue).append(",");
                    }
                    editor.putString("teamModeScoreOmadon", str.toString());
                    editor.apply();
                }
                startActivity(new Intent(getApplicationContext(), LivesGame.class));
                finish();
            }
        });
    }
}
