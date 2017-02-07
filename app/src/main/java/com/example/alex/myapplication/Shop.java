package com.example.alex.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Shop extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_AGORES = "AGORES";

    private Button new1;
    private Button new2;
    private Button new3;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        new1 = (Button) findViewById(R.id.new1);
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alert(new1);
            }
        });

        new2 = (Button) findViewById(R.id.new2);
        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alert(new2);
            }
        });

        new3 = (Button) findViewById(R.id.new3);
        new3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alert(new3);
            }
        });

        //to Koumbi gia epistrofi
        home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        buttonDisabler();
    }

    public void alert(final Button koumbi){

        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);
        final int paliaLefta = lefta.getInt("lefta", 0);
        String temp = "gamithike";
        switch (koumbi.getId()){
            case R.id.new1:
                temp = "new1";
                break;
            case R.id.new2:
                temp = "new2";
                break;
            case R.id.new3:
                temp = "new3";
                break;
        }
        final String agora = temp;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Start setting up the builder
        builder.setMessage("Είσαι σίγουρος ότι θες να αγοράσεις την κατηγορία "+koumbi.getText()+" ? Έχεις " +Integer.toString(paliaLefta)+ " και κοστίζει 2");

        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                koumbi.setEnabled(false);

                int neaLefta = paliaLefta - 2;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", neaLefta);
                lefta_editor.apply();

                SharedPreferences.Editor editor = agorasmena.edit();
                editor.putBoolean(agora, true);
                editor.apply();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Set other dialog properties

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(paliaLefta < 2)
                    ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });
        dialog.show();
    }

    private void buttonDisabler(){
        SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);
        if(agorasmena.getBoolean("new1",false)) {
            new1.setEnabled(false);
        }
        if(agorasmena.getBoolean("new2",false)) {
            new2.setEnabled(false);
        }
        if(agorasmena.getBoolean("new3",false)) {
            new3.setEnabled(false);
        }

    }
}
