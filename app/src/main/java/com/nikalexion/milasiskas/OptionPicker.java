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


    Button teamNamesButton;
    Spinner modeSelector;

    //metavlites gia to score seekbar
    SeekBar scoreSeekBar;
    TextView scoreText;
    int scoreStep = 10;
    int scoreMax = 50;
    int scoreMin = 10;
    int scoreValue = 20;

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
    int timeValue = 120;

    //metavlites gia to pasa seekbar
    SeekBar pasaSeekBar;
    TextView pasaText;
    int pasaStep = 1;
    int pasaMax = 6;
    int pasaMin = 0;
    int pasaValue = 3;

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
        teamNamesButton = findViewById(R.id.goToTeamNamesButton);

        //vres to spinner
        modeSelector = findViewById(R.id.modeSwitchSpinner);
        //oi epiloges tou spinner se string
        String[] modes = new String[]{"Με Σκορ", "Με ζωές"};
        //dimiourgia tou adapter gia to pos na emfanistoun TODO (to simple_spinner_item borei na alaxtei se kati diko mas)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modes);

        // Epilogi tou layout gia to dropdown menu tou spinner TODO (to simple_spinner_dropdown_item borei na alaxtei se kati diko mas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //allagei tou paliou (adiou) adapter ston neo etoimo adapter
        modeSelector.setAdapter(adapter);
        modeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    scoreStep = 10;
                    scoreMax = 50;
                    scoreMin = 10;
                    scoreValue = 20;
                    scoreBarController();
                    scoreText.setText("Στους " + String.valueOf(scoreValue) + " πόντους");
                }else{
                    scoreStep = 1;
                    scoreMax = 10;
                    scoreMin = 1;
                    scoreValue = 3;
                    scoreBarController();
                    scoreText.setText("Κάθε ομάδα έχει " + String.valueOf(scoreValue) + " ζωές");
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //to teamNamesButton button trexei to game analogos tou mode pou exei epilexthei kai stelenei se preferences ta options
        teamNamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SharedPreferences sp = getSharedPreferences(PREF_EPILOGES, 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("teamModeScore", scoreValue);
                editor.putInt("teamModeTeams", teamsValue);
                editor.putInt("teamModeTime", timeValue);
                editor.putInt("teamModePasa", pasaValue);
                editor.putInt("teamModeProtosPaiktis", 0);
                if (modeSelector.getSelectedItemPosition()==0){
                    //teamModeMode == 0 simaianei ScoreGame
                    editor.putInt("teamModeMode", 0);
                    //midenismos ton score
                    editor.putString("teamModeScoreOmadon", "0,0,0,0,");
                }else{
                    //teamModeMode == 1 simaianei LivesGame
                    editor.putInt("teamModeMode", 1);

                    //arxikopoisi ton zoon
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < teamsValue; i++) {
                        str.append(scoreValue).append(",");
                    }
                    editor.putString("teamModeScoreOmadon", str.toString());
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
        //TODO edo afto ginete anapoda apoti tharepe alla doulevei (an to kano me sosti seira tote allazontas to max epireazete to scoreValue)
        scoreSeekBar.setProgress( (scoreValue - scoreMin) / scoreStep);
        scoreSeekBar.setMax( (scoreMax - scoreMin) / scoreStep);

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
        pasaSeekBar.setProgress( (pasaValue - pasaMin) / pasaStep);
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
