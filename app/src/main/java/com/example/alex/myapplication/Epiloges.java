package com.example.alex.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Epiloges extends AppCompatActivity implements OnCheckedChangeListener {

    public static final String PREF_EPILOGES = "EPILOGES";
    public static final String PREF_AGORES = "AGORES";
    private CheckBox random;
    private CheckBox tainiesENG;
    private CheckBox parimies;
    private CheckBox tainiesGR;
    private CheckBox paidika;
    private CheckBox tvGR;
    private CheckBox tvENG;
    private CheckBox myths;
    private CheckBox new1;
    private CheckBox new2;
    private CheckBox new3;

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epiloges);

        SharedPreferences epiloges = getApplicationContext().getSharedPreferences(PREF_EPILOGES, 0);
        SharedPreferences agores = getApplicationContext().getSharedPreferences(PREF_AGORES, 0);

        random = (CheckBox) findViewById(R.id.random_box);
        random.setChecked(epiloges.getBoolean("random",true));
        random.setOnCheckedChangeListener(this);

        tainiesENG = (CheckBox) findViewById(R.id.tainiesENG_box);
        tainiesENG.setChecked(epiloges.getBoolean("tainiesENG",true));
        tainiesENG.setOnCheckedChangeListener(this);

        parimies = (CheckBox) findViewById(R.id.parimies_box);
        parimies.setChecked(epiloges.getBoolean("parimies",true));
        parimies.setOnCheckedChangeListener(this);

        tainiesGR = (CheckBox) findViewById(R.id.tainiesGR_box);
        tainiesGR.setChecked(epiloges.getBoolean("tainiesGR",true));
        tainiesGR.setOnCheckedChangeListener(this);

        paidika = (CheckBox) findViewById(R.id.paidika_box);
        paidika.setChecked(epiloges.getBoolean("paidika",true));
        paidika.setOnCheckedChangeListener(this);

        tvGR = (CheckBox) findViewById(R.id.tvGR_box);
        tvGR.setChecked(epiloges.getBoolean("tvGR",true));
        tvGR.setOnCheckedChangeListener(this);

        tvENG = (CheckBox) findViewById(R.id.tvENG_box);
        tvENG.setChecked(epiloges.getBoolean("tvENG",true));
        tvENG.setOnCheckedChangeListener(this);

        myths = (CheckBox) findViewById(R.id.myths_box);
        myths.setChecked(epiloges.getBoolean("myths",true));
        myths.setOnCheckedChangeListener(this);

        new1 = (CheckBox) findViewById(R.id.new1);
        new1.setChecked(epiloges.getBoolean("new1",false));
        new1.setEnabled(agores.getBoolean("new1",false));
        new1.setOnCheckedChangeListener(this);

        new2 = (CheckBox) findViewById(R.id.new2);
        new2.setChecked(epiloges.getBoolean("new2",false));
        new2.setEnabled(agores.getBoolean("new2",false));
        new2.setOnCheckedChangeListener(this);

        new3 = (CheckBox) findViewById(R.id.new3);
        new3.setChecked(epiloges.getBoolean("new3",false));
        new3.setEnabled(agores.getBoolean("new3",false));
        new3.setOnCheckedChangeListener(this);


        //To koumbi gia epistrofi sto arxiko menou
        home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    private void saveInSp(String key, boolean value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF_EPILOGES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        switch(buttonView.getId()){
            case R.id.random_box:
                saveInSp("random",isChecked);
                break;
            case R.id.tainiesENG_box:
                saveInSp("tainiesENG",isChecked);
                break;
            case R.id.parimies_box:
                saveInSp("parimies",isChecked);
                break;
            case R.id.tainiesGR_box:
                saveInSp("tainiesGR",isChecked);
                break;
            case R.id.paidika_box:
                saveInSp("paidika",isChecked);
                break;
            case R.id.tvGR_box:
                saveInSp("tvGR",isChecked);
                break;
            case R.id.tvENG_box:
                saveInSp("tvENG",isChecked);
                break;
            case R.id.myths_box:
                saveInSp("myths",isChecked);
                break;
            case R.id.new1:
                saveInSp("new1",isChecked);
                break;
            case R.id.new2:
                saveInSp("new2",isChecked);
                break;
            case R.id.new3:
                saveInSp("new3",isChecked);
                break;
        }
    }

}
