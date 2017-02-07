package com.example.alex.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    private Button starter;
    private Button guidelines;
    private Button epiloges;
    private Button shop;
    private TextView leftakia;
    //i diafimisi

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
        super.onStart();


    }

}
