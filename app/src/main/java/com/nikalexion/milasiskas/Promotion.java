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

import com.google.firebase.analytics.FirebaseAnalytics;

public class Promotion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        final Button rate = (Button) findViewById(R.id.rating);


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                rateApp();
            }
        });

        //To koumbi poy se metaferei sta privacy
        Button privacy = (Button) findViewById(R.id.privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                goPrivacy();
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

        //To koumbi gia Instagram
        Button insta = (Button) findViewById(R.id.instagram);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                instagram();
            }
        });
    }

    public void goPrivacy(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/milashskasprivacy"));
        startActivity(browserIntent);
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

    public void instagram(){
        Intent goInsta = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/milashskas/"));
        startActivity(goInsta);
    }
}
