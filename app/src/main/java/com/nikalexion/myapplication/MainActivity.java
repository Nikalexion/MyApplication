package com.nikalexion.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_AGORES = "AGORES";
    public static final String PREF_EPILOGES = "EPILOGES";

    private Button shop;
    private TextView leftakia;

    //To olo "press back again to leave"
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Πατήστε ξανά για έξοδο απο την εφαρμογή", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSharedPreferences(PREF_EPILOGES, 0).getBoolean("protiFora",true)) {
            SharedPreferences.Editor defaultAgores = getSharedPreferences(PREF_AGORES, 0).edit();
            SharedPreferences.Editor defaultEpiloges = getSharedPreferences(PREF_EPILOGES, 0).edit();

            defaultEpiloges.putBoolean("protiFora", false);
            defaultEpiloges.apply();

            String[] katigories = getResources().getStringArray(R.array.categories);
            for (int i = 0; i < 6; i++) {
                defaultAgores.putBoolean(katigories[i], true);
                defaultAgores.apply();
                defaultEpiloges.putBoolean(katigories[i], true);
                defaultEpiloges.apply();
            }

        }

        Button starter = (Button) findViewById(R.id.starter);
        Button guidelines = (Button) findViewById(R.id.guidelines);
        Button epiloges = (Button) findViewById(R.id.epiloges);
        shop = (Button) findViewById(R.id.shop);
        leftakia = (TextView) findViewById(R.id.leftakia);


        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), TimePicker.class));
            }
        });

        guidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Guide.class));
            }
        });

        epiloges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Epiloges.class));
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Shop.class));
            }
        });

    }

    @Override
    protected void onStart() {
        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        int counter = lefta.getInt("lefta", 0);
        leftakia.setText(Integer.toString(counter));
        leftakia.setTextColor(Color.YELLOW);
        SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);
        //TODO exeis kani malakia edo kanto dinamiko giati atm an agoraseis ta new1-2-3 stamatei na kitirinzei to koumbi
        if(!(agorasmena.getBoolean("new1",false))&& (counter > 1)) {
            shop.setBackgroundResource(R.drawable.shop_purchasable);
        }
        else if (!(agorasmena.getBoolean("new2",false))&& (counter > 1)) {
            shop.setBackgroundResource(R.drawable.shop_purchasable);
        }
        else if (!(agorasmena.getBoolean("new3",false))&& (counter > 1)) {
            shop.setBackgroundResource(R.drawable.shop_purchasable);
        }
        else{
            shop.setBackgroundResource(R.drawable.btn_bg);
        }


        super.onStart();
    }

}
