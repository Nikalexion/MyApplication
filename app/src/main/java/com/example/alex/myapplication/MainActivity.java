package com.example.alex.myapplication;

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
    private Button starter;
    private Button guidelines;
    private Button epiloges;
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
        //TODO keimeno sta ellinika
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

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


        starter = (Button) findViewById(R.id.starter);
        guidelines = (Button) findViewById(R.id.guidelines);
        epiloges = (Button) findViewById(R.id.epiloges);
        shop = (Button) findViewById(R.id.shop);
        leftakia = (TextView) findViewById(R.id.leftakia);


        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainGame.class));
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
