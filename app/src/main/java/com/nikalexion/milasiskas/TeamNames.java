package com.nikalexion.milasiskas;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
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

public class TeamNames extends AppCompatActivity {

    int plithosOmadon;
    Button teamNamesButton;
    int xroma1;
    int xroma2;
    int xroma3;
    int xroma4;

    int spliter1;
    int spliter2;
    int spliter;

    RadioButton arrayRadio[][] = new RadioButton[4][8];
    boolean xrwmata[][] = new boolean[4][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_names);

        plithosOmadon = getSharedPreferences("EPILOGES", 0).getInt("teamModeTeams", 2);


        //to na passareis variable se R.id me loops ktlp einai pio "vari"
        //kai poliploko xoris logo opote kalitera manually stin prokimeni periptosi
        arrayRadio[0][0] = findViewById(R.id.xroma11);
        arrayRadio[0][1] = findViewById(R.id.xroma12);
        arrayRadio[0][2] = findViewById(R.id.xroma13);
        arrayRadio[0][3] = findViewById(R.id.xroma14);
        arrayRadio[0][4] = findViewById(R.id.xroma15);
        arrayRadio[0][5] = findViewById(R.id.xroma16);
        arrayRadio[0][6] = findViewById(R.id.xroma17);
        arrayRadio[0][7] = findViewById(R.id.xroma18);
        arrayRadio[1][0] = findViewById(R.id.xroma21);
        arrayRadio[1][1] = findViewById(R.id.xroma22);
        arrayRadio[1][2] = findViewById(R.id.xroma23);
        arrayRadio[1][3] = findViewById(R.id.xroma24);
        arrayRadio[1][4] = findViewById(R.id.xroma25);
        arrayRadio[1][5] = findViewById(R.id.xroma26);
        arrayRadio[1][6] = findViewById(R.id.xroma27);
        arrayRadio[1][7] = findViewById(R.id.xroma28);
        arrayRadio[2][0] = findViewById(R.id.xroma31);
        arrayRadio[2][1] = findViewById(R.id.xroma32);
        arrayRadio[2][2] = findViewById(R.id.xroma33);
        arrayRadio[2][3] = findViewById(R.id.xroma34);
        arrayRadio[2][4] = findViewById(R.id.xroma35);
        arrayRadio[2][5] = findViewById(R.id.xroma36);
        arrayRadio[2][6] = findViewById(R.id.xroma37);
        arrayRadio[2][7] = findViewById(R.id.xroma38);
        arrayRadio[3][0] = findViewById(R.id.xroma41);
        arrayRadio[3][1] = findViewById(R.id.xroma42);
        arrayRadio[3][2] = findViewById(R.id.xroma43);
        arrayRadio[3][3] = findViewById(R.id.xroma44);
        arrayRadio[3][4] = findViewById(R.id.xroma45);
        arrayRadio[3][5] = findViewById(R.id.xroma46);
        arrayRadio[3][6] = findViewById(R.id.xroma47);
        arrayRadio[3][7] = findViewById(R.id.xroma48);
        xroma1 = 0;
        xroma2 = 1;
        if (plithosOmadon >= 3){
            xroma3 =2;
        }
        if (plithosOmadon == 4){
            xroma4 = 3;
        }

        //ksexwrizei ta epilegmena Radio otan anoigei to screen
        for (int i = 0; i < plithosOmadon; i++){
            xrwmata[i][i] = true;
            radioDisable(i,i);
        }


        RadioGroup groupXromaton1 = findViewById(R.id.groupXromaton1);
        groupXromaton1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = findViewById(checkedId);

                //xroma1 = checkedRadioButton.getSolidColor();


                //vriskei tis diastaseis toy koympioy poy ekanes enable
                spliter = Integer.parseInt((String) checkedRadioButton.getTag());
                spliter1 = spliter /10;
                spliter2 = spliter % 10;

                xroma1 = spliter2;


                radioDisable(spliter1,spliter2);

            }

        });

        RadioGroup groupXromaton2 = findViewById(R.id.groupXromaton2);
        groupXromaton2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = findViewById(checkedId);
                //xroma2 = checkedRadioButton.getSolidColor();

                //vriskei tis diastaseis toy koympioy poy ekanes enable
                spliter = Integer.parseInt((String) checkedRadioButton.getTag());
                spliter1 = spliter /10;
                spliter2 = spliter % 10;

                xroma2 = spliter2;


                radioDisable(spliter1,spliter2);
            }
        });


        if (plithosOmadon >= 3) {
            RadioGroup groupXromaton3 = findViewById(R.id.groupXromaton3);
            groupXromaton3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton checkedRadioButton = findViewById(checkedId);
                    //xroma3 = checkedRadioButton.getSolidColor();

                    //vriskei tis diastaseis toy koympioy poy ekanes enable
                    spliter = Integer.parseInt((String) checkedRadioButton.getTag());
                    spliter1 = spliter /10;
                    spliter2 = spliter % 10;

                    xroma3 = spliter2;


                    radioDisable(spliter1,spliter2);
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
                    //xroma4 = checkedRadioButton.getSolidColor();

                    //vriskei tis diastaseis toy koympioy poy ekanes enable
                    spliter = Integer.parseInt((String) checkedRadioButton.getTag());
                    spliter1 = spliter /10;
                    spliter2 = spliter % 10;

                    xroma4 = spliter2;


                    radioDisable(spliter1,spliter2);
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
                SharedPreferences sp = getSharedPreferences("EPILOGES", 0);
                SharedPreferences.Editor editor = sp.edit();

                editor.putString("onoma1", ((EditText)findViewById(R.id.onomaOmadas1)).getText().toString());
                editor.putInt("xroma1", xroma1);

                editor.putString("onoma2", ((EditText)findViewById(R.id.onomaOmadas2)).getText().toString());
                editor.putInt("xroma2", xroma2);
                if (plithosOmadon >= 3) {
                    editor.putString("onoma3", ((EditText)findViewById(R.id.onomaOmadas3)).getText().toString());
                    editor.putInt("xroma3", xroma3);
                }
                if (plithosOmadon >= 4) {
                    editor.putString("onoma4", ((EditText)findViewById(R.id.onomaOmadas4)).getText().toString());
                    editor.putInt("xroma4", xroma4);
                }

                editor.apply();
                int mode = getSharedPreferences("EPILOGES", 0).getInt("teamModeMode", 0);
                if (mode == 0){
                    startActivity(new Intent(getApplicationContext(), ScoreGame.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), LivesGame.class));
                }
                finish();
            }
        });



    }

    void radioDisable(int x, int y){

        //vriskei to palio poy htan enabled kai kanei enable ta koympia poy htan disable
            for (int l =0; l < 8; l++){
                if (xrwmata[x][l] == true){
                    for (int k =0; k < plithosOmadon; k++){
                        arrayRadio[k][l].setEnabled(true);
                    }
                    xrwmata[x][l] = false;
                }
            }
            xrwmata[x][y] = true;
         //kanei disable ta kainoyrgia
        for (int i = 0; i < plithosOmadon; i++){
            if (i != x){
                arrayRadio[i][y].setEnabled(false);
            }
        }
    }
}
