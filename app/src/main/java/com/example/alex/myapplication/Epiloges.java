package com.example.alex.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

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
    private CheckBox food;
    private CheckBox animals;
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

        String[] onomataKatigorion = getResources().getStringArray(R.array.category_names);
        String[] katigories = getResources().getStringArray(R.array.categories);
        int Array_Count = katigories.length;

        LinearLayout my_layout = (LinearLayout)findViewById(R.id.activity_epiloges);

        for (int i = 0; i < Array_Count; i++) {

            TableRow row =new TableRow(this);
            row.setId(i);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setId(i);
            checkBox.setText(onomataKatigorion[i]);
            checkBox.setChecked(epiloges.getBoolean(katigories[i],false));
            checkBox.setEnabled(agores.getBoolean(katigories[i],false));
            row.addView(checkBox);
            my_layout.addView(row);
        }



        /* todo na thimithoume na ksanavaloume afto to koumbi gia epistrofi apo edo


        //To koumbi gia epistrofi sto arxiko menou
        home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
        */
    }

    public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
        saveInSp(getResources().getStringArray(R.array.categories)[buttonView.getId()],isChecked);
    }

    private void saveInSp(String key, boolean value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF_EPILOGES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


}
