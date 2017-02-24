package com.nikalexion.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Ending extends AppCompatActivity {


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
                finish();
            }
        });

        //To koumbi gia epistrofi sto arxiko menou
        home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

}
