package com.nikalexion.myapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


import static com.unity3d.ads.properties.ClientProperties.getActivity;


public class Shop extends AppCompatActivity implements RewardedVideoAdListener {

    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_AGORES = "AGORES";

    private Button new1;
    private Button new2;
    private Button new3;
    private Button moneyPlus;
    private RewardedVideoAd mAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);


        new1 = (Button) findViewById(R.id.new1);
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alert(new1,lefta,agorasmena);
            }
        });

        new2 = (Button) findViewById(R.id.new2);
        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alert(new2,lefta,agorasmena);
            }
        });

        new3 = (Button) findViewById(R.id.new3);
        new3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alert(new3,lefta,agorasmena);
            }
        });

        //to Koumbi gia epistrofi
        Button home = (Button) findViewById(R.id.arxiki);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });

        //koumbi gia ta lefta
        moneyPlus = (Button) findViewById(R.id.moneyPlus);
        moneyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (mAd.isLoaded()){
                    mAd.show();
                }
            }
        });

        if(!agorasmena.getBoolean("new1",false)&& (lefta.getInt("lefta",0) > 1)) {
            new1.setBackgroundResource(R.drawable.shop_prasino);
        }
        if (!agorasmena.getBoolean("new2",false)&& (lefta.getInt("lefta",0) > 1)) {
            new2.setBackgroundResource(R.drawable.shop_prasino);
        }
        if (!agorasmena.getBoolean("new3",false)&& (lefta.getInt("lefta",0) > 1)) {
            new3.setBackgroundResource(R.drawable.shop_prasino);
        }

        loadRewardedVideoAd();
        buttonDisabler();
    }

    public void alert(final Button koumbi,final SharedPreferences lefta,final SharedPreferences agorasmena){

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

        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(200, 400);



        builder.setTitle("Μιλάς ή Σκας");
        //Start setting up the builder
        builder.setMessage("Είσαι σίγουρος ότι θες να αγοράσεις την κατηγορία "+koumbi.getText()+" ? Έχεις " +Integer.toString(paliaLefta)+ " και κοστίζει 2");

        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                koumbi.setEnabled(false);

                koumbi.setBackgroundResource(R.drawable.shop_btn);

                int neaLefta = paliaLefta - 2;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", neaLefta);
                lefta_editor.apply();

                SharedPreferences.Editor editor = agorasmena.edit();
                editor.putBoolean(agora, true);
                editor.apply();

                if(lefta.getInt("lefta",0) < 2) {
                    new1.setBackgroundResource(R.drawable.shop_btn);
                    new2.setBackgroundResource(R.drawable.shop_btn);
                    new3.setBackgroundResource(R.drawable.shop_btn);
                }
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

    private void loadRewardedVideoAd() {
        if(!mAd.isLoaded()){
            mAd.loadAd("ca-app-pub-5861682469694178/2306813044", new AdRequest.Builder().build());
        }else{
            moneyPlus.setEnabled(true);
            moneyPlus.setText("Bonus Money");
        }
    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();

        SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        //etoimazo to editor
        SharedPreferences.Editor lefta_editor = lefta.edit();
        //ta getType einai "lefta" kai to getAmount einai int posou rithmismeno apo to admob
        lefta_editor.putInt(reward.getType(), lefta.getInt(reward.getType(), 0) + reward.getAmount());
        lefta_editor.apply();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
                Toast.LENGTH_SHORT).show();
        //den to ipostirizei to unity
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        moneyPlus.setEnabled(true);
        moneyPlus.setText("Bonus Money");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

}
