package com.nikalexion.milasiskas;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;


public class Ending extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    AlertDialog alertBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);



        //prosthiki ton poson game epaikse o sigekrimenos xristis
        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        SharedPreferences.Editor lefta_editor = lefta.edit();

        //Shared Pref gia ta ratings
        int rateCounter = lefta.getInt("rater", 1);
        lefta_editor.putInt("rater", rateCounter);
        lefta_editor.apply();

        //An exei paiksei panw apo x paixnidia toy emfanizei alert gia rate
        if (lefta.getInt("games", 0) >= (lefta.getInt("rater", 30))){
            alertBox = alert();
        }


        //To koumbi gia epanekinisi paixnidiou
        Button neoGame = (Button) findViewById(R.id.neoGame);
        neoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainGame.class));
                finish();
            }
        });

        //To koumbi gia epistrofi sto arxiko menou
        Button home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    public AlertDialog alert(){

        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        SharedPreferences.Editor lefta_editor = lefta.edit();
        AlertDialog alertDialog;
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyAlertDialogStyle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(200, 400);



        builder.setTitle("Rate Us!");
        //Start setting up the builder
        builder.setMessage("Οι κριτικές σας μας βοηθάνε να βελτιωθούμε! Θα ήθελες να μας βαθμολογήσεις;");

        // Add the buttons
        builder.setPositiveButton("Ναι!", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                lefta_editor.putInt("rater", 9999999);
                lefta_editor.apply();
                rateApp();
            }
        });

        builder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                lefta_editor.putInt("rater", 9999999);
                lefta_editor.apply();
            }
        });

        builder.setNeutralButton("Ίσως Αργότερα", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                lefta_editor.putInt("rater", lefta.getInt("games", 0) + 30);
                lefta_editor.apply();
            }
        });     // Set other dialog properties

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //An i diafimisi den fortose kanei disable to "ok"

            }

        });
        dialog.show();

        return dialog;
    }

    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl(("market://details"));
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}
