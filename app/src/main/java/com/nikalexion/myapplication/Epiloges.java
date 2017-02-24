package com.nikalexion.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;

public class Epiloges extends AppCompatActivity implements OnCheckedChangeListener {

    public static final String PREF_EPILOGES = "EPILOGES";
    public static final String PREF_AGORES = "AGORES";

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
            TableRow row = new TableRow(this);
            row.setId(i);
            row.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,3));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setId(i);
            checkBox.setText(onomataKatigorion[i]);
            checkBox.setTextSize(32);
            checkBox.setChecked(epiloges.getBoolean(katigories[i],false));
            checkBox.setEnabled(agores.getBoolean(katigories[i],false));
            row.addView(checkBox);
            my_layout.addView(row);
        }


        home = new Button(this);
        home.setBackgroundResource(R.drawable.btn_bg);
        home.setText("ΑΡΧΙΚΗ");
        home.setTextSize(30);
        home.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,0));
        my_layout.addView(home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
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
