package com.nikalexion.milasiskas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TeamNames extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";

    int plithosOmadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_names);

        plithosOmadon = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTeams", 2);
        LinearLayout my_layout = findViewById(R.id.activity_team_names);

        for (int i = 0; i < plithosOmadon; i++) {

        }

        Button home = new Button(this);
        home.setBackgroundResource(R.drawable.button);
        home.setText("Lez go");
        home.setTextSize(30);
        home.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,0));
        my_layout.addView(home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int mode = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeMode", 0);
                if (mode == 0){
                    startActivity(new Intent(getApplicationContext(), ScoreGame.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), LivesGame.class));
                }
                finish();
            }
        });

    }
}
