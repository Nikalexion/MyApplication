package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class OptionPicker extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";


    Button launchGame;
    Spinner modeSelector;

    //metavlites gia to score seekbar
    SeekBar scoreSeekBar;
    TextView scoreText;
    int scoreStep = 1;
    int scoreMax = 5;
    int scoreMin = 1;
    int scoreValue = 3;

    //metavlites gia to teams seekbar
    SeekBar teamsSeekBar;
    TextView teamsText;
    int teamsStep = 1;
    int teamsMax = 4;
    int teamsMin = 2;
    int teamsValue = 2;

    //metavlites gia to time seekbar
    SeekBar timeSeekBar;
    TextView timeText;
    //prosoxi i praksi (max-min)/step na vgainei panta int arithmos
    int timeStep = 10;
    int timeMax = 180;
    int timeMin = 60;
    int timeValue;

    //metavlites gia to pasa seekbar
    SeekBar pasaSeekBar;
    TextView pasaText;
    int pasaStep = 1;
    int pasaMax = 6;
    int pasaMin = 0;
    int pasaValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_picker);

        //travaei apo ta preferences tin arxiki timi tou timeValue, an den yparxei vazei 120
        timeValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTime", 120);
        //travaei apo ta preferences tin arxiki timi tou pasaValue, an den yparxei vazei 3
        pasaValue = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModePasa", 3);

        scoreBarController();
        teamsBarController();
        timeBarController();
        pasaBarController();

        //dilono ta buttons gia na boro na ta xrisimopoieiso
        launchGame = findViewById(R.id.launchScoreModeButton);

        //vres to spinner
        modeSelector = findViewById(R.id.modeSwitchSpinner);
        //oi epiloges tou spinner se string
        String[] modes = new String[]{"Score mode", "Lives mode", "Last man standing"};
        //dimiourgia tou adapter gia to pos na emfanistoun TODO (to simple_spinner_item borei na alaxtei)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modes);

        // Epilogi tou layout gia to dropdown menu TODO (to simple_spinner_dropdown_item borei na alaxtei)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //allagei tou paliou (adiou) adapter ston neo etoimo adapter
        modeSelector.setAdapter(adapter);
        modeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                launchGame.setText("Start " + parent.getSelectedItem().toString());
                if (position == 1){
                    scoreText.setText("Κάθε ομάδα έχει " + String.valueOf(scoreValue) + " ζωές");
                }else{
                    scoreText.setText("Στους " + String.valueOf(scoreValue) + " πόντους");
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //to launchGame button trexei to game analogos tou mode pou exei epilexthei kai stelenei se preferences ta options
        launchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SharedPreferences sp = getSharedPreferences(PREF_EPILOGES, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("teamModeScore", scoreValue);
                editor.putInt("teamModeTeams", teamsValue);
                editor.putInt("teamModeTime", timeValue);
                editor.putInt("teamModePasa", pasaValue);
                if (modeSelector.getSelectedItemPosition()==1){
                    editor.putInt("teamModeMode", 1);
                }else{
                    editor.putInt("teamModeMode", 0);
                }
                editor.apply();
                startActivity(new Intent(getApplicationContext(), TeamNames.class));
                finish();
            }
        });

    }

    private void scoreBarController(){
        scoreSeekBar = findViewById(R.id.scoreBar);
        scoreText = findViewById(R.id.scoreText);

        //prosoxi i praksi na vgainei panta int arithmos!
        scoreSeekBar.setMax( (scoreMax - scoreMin) / scoreStep);
        scoreSeekBar.setProgress( (scoreValue - scoreMin) / scoreStep);
        scoreText.setText("Στους " + String.valueOf(scoreValue) + " πόντους");

        // otan allazei to to seekerBar ginete apefthias ananeosi tou ti grafei apo kato
        scoreSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                scoreValue = scoreMin + (progress * scoreStep);
                if (modeSelector.getSelectedItemPosition()==1){
                    scoreText.setText("Κάθε ομάδα έχει " + String.valueOf(scoreValue) + " ζωές");
                }else{
                    scoreText.setText("Στους " + String.valueOf(scoreValue) + " πόντους");
                }
            }
        });

    }

    private void teamsBarController(){
        teamsSeekBar = findViewById(R.id.teamsBar);
        teamsText = findViewById(R.id.teamsText);

        //prosoxi i praksi na vgainei panta int arithmos!
        teamsSeekBar.setMax( (teamsMax - teamsMin) / teamsStep);
        teamsSeekBar.setProgress( (teamsValue - teamsMin) / teamsStep);
        teamsText.setText(String.valueOf(teamsValue) + " Ομάδες");

        // otan allazei to to seekerBar ginete apefthias ananeosi tou ti grafei apo kato
        teamsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                teamsValue = teamsMin + (progress * teamsStep);
                teamsText.setText(String.valueOf(teamsValue) + " Ομάδες");
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
