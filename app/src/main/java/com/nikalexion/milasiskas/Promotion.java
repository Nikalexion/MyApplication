package com.nikalexion.milasiskas;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Promotion extends AppCompatActivity {

    public static final String PREF_EPILOGES = "EPILOGES";
    public static final String PREF_LEFTA = "LEFTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        final SharedPreferences rater = getSharedPreferences(PREF_EPILOGES, 0);

        //To koumbi gia epanekinisi paixnidiou
        final Button rate = (Button) findViewById(R.id.rating);
        rate.setEnabled(!rater.getBoolean("rated",false));

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                SharedPreferences.Editor rate_editor = rater.edit();
                rate_editor.putBoolean("rated", true);
                rate_editor.apply();

                SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
                int counter = lefta.getInt("lefta", 0);
                counter = counter + 10;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", counter);
                lefta_editor.apply();

                rate.setEnabled(false);


                rateApp();
            }
        });

        //To koumbi gia epistrofi sto arxiko menou
        Button home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl(("market://details?id=com.nikalexion.milasiskas"));
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details?id=com.nikalexion.milasiskas");
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
