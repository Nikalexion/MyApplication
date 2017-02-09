package com.example.alex.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Ending extends AppCompatActivity {

    //testaki git
    private Button neoGame;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        //To koumbi gia epanekinisi paixnidiou
        neoGame = (Button) findViewById(R.id.neoGame);
        neoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainGame.class));
            }
        });

        //To koumbi gia epistrofi sto arxiko menou
        home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
