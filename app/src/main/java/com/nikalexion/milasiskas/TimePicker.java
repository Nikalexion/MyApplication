package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TimePicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        //To koumbi gia normal dia"AGORES"rkia (60-80)
        Button normal = (Button) findViewById(R.id.normalTime);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                timeChosen(60,80);
            }
        });

        //To koumbi gia extended diarkia (120-140)
        Button extended = (Button) findViewById(R.id.extendedTime);
        extended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                timeChosen(120,140);
            }
        });
    }

    public void timeChosen (int min, int max){
        SharedPreferences sp = getSharedPreferences("EPILOGES", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("minTime", min);
        editor.putInt("maxTime", max);
        editor.apply();
        startActivity(new Intent(getApplicationContext(), MainGame.class));
        finish();
    }

}
