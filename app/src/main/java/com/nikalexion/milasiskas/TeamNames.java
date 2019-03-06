package com.nikalexion.milasiskas;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.nikalexion.milasiskas.R.color.xrwma1;
import static com.nikalexion.milasiskas.R.color.xrwma2;

public class TeamNames extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";

    int plithosOmadon;
    Button teamNamesButton;
    String xroma1 = "11";
    String xroma2 = "22";
    String xroma3 = "33";
    String xroma4 = "44";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_names);

        plithosOmadon = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeTeams", 2);



        //TODO lipei to kathe koumbi na apokliei ta alla koumbia tou idiou xromatos
        RadioGroup groupXromaton1 = findViewById(R.id.groupXromaton1);
        groupXromaton1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = findViewById(checkedId);
                //TODO edo tha prepei na travaei kati allo (oxi to text string) gia na travaei xroma apo kathe koumbi
                xroma1 = checkedRadioButton.getText().toString();
            }

        });

        RadioGroup groupXromaton2 = findViewById(R.id.groupXromaton2);
        groupXromaton2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = findViewById(checkedId);
                xroma2 = checkedRadioButton.getText().toString();
            }
        });


        if (plithosOmadon >= 3) {
            RadioGroup groupXromaton3 = findViewById(R.id.groupXromaton3);
            groupXromaton3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = findViewById(checkedId);
                    xroma3 = checkedRadioButton.getText().toString();
                }
            });
        }else{
            //eksanafisi ton epilogon tou group 3 an einai kato apo 3
            findViewById(R.id.groupXromaton3).setVisibility(View.INVISIBLE);
            findViewById(R.id.groupXromaton3).setEnabled(false);
            findViewById(R.id.onomaOmadas3).setVisibility(View.INVISIBLE);
            findViewById(R.id.onomaOmadas3).setEnabled(false);
        }

        if (plithosOmadon >=4 ) {
            RadioGroup groupXromaton4 = findViewById(R.id.groupXromaton4);
            groupXromaton4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = findViewById(checkedId);
                    xroma4 = checkedRadioButton.getText().toString();
                }
            });
        }else{
            //eksanafisi ton epilogon tou group 4 an einai kato apo 4
            findViewById(R.id.groupXromaton4).setVisibility(View.INVISIBLE);
            findViewById(R.id.groupXromaton4).setEnabled(false);
            findViewById(R.id.onomaOmadas4).setVisibility(View.INVISIBLE);
            findViewById(R.id.onomaOmadas4).setEnabled(false);
        }



        teamNamesButton = findViewById(R.id.launchScoreModeButton);
        teamNamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SharedPreferences sp = getSharedPreferences(PREF_EPILOGES, 0);
                SharedPreferences.Editor editor = sp.edit();

                //TODO edo dinamika onomata anti gia "onoma1 xroma1" gia na ta ftiaxnei kai dinamika sto game (pane scoreGame na katalaveis)
                editor.putString("onoma1", ((EditText)findViewById(R.id.onomaOmadas1)).getText().toString());
                editor.putString("xroma1", xroma1);

                editor.putString("onoma2", ((EditText)findViewById(R.id.onomaOmadas2)).getText().toString());
                editor.putString("xroma2", xroma2);
                if (plithosOmadon >= 3) {
                    editor.putString("onoma3", ((EditText)findViewById(R.id.onomaOmadas3)).getText().toString());
                    editor.putString("xroma3", xroma3);
                }
                if (plithosOmadon >= 4) {
                    editor.putString("onoma4", ((EditText)findViewById(R.id.onomaOmadas4)).getText().toString());
                    editor.putString("xroma4", xroma4);
                }

                editor.apply();
                int mode = getSharedPreferences(PREF_EPILOGES, 0).getInt("teamModeMode", 0);
                if (mode == 0){
                    startActivity(new Intent(getApplicationContext(), ScoreGame.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), LivesGame.class));
                }
                finish();
            }
        });









        /*
        //TODO to layout den einai pleon Linear den ksero an afto "ta gamaei ola"
        LinearLayout my_layout = findViewById(R.id.activity_team_names);

        for (int i = 0; i < plithosOmadon; i++) {
            //TODO dinamika plaisia epilogon gia kathe omada
        }

      //TODO edo einai to koumbi gia an ginei dinamiko (den eimai sigouros an tha xrisimefsei)
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
*/

    }
}
