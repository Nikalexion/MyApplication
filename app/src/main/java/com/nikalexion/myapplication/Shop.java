package com.nikalexion.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
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


    private Button moneyPlus;
    private RewardedVideoAd mAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //TODO na kano tis diafimiseis ksana na doulevoun
        //mAd = MobileAds.getRewardedVideoAdInstance(this);
        //mAd.setRewardedVideoAdListener(this);


        SharedPreferences lefta = getApplicationContext().getSharedPreferences(PREF_LEFTA, 0);
        SharedPreferences agorasmena = getApplicationContext().getSharedPreferences(PREF_AGORES, 0);

        String[] onomataKatigorion = getResources().getStringArray(R.array.category_names);
        String[] katigories = getResources().getStringArray(R.array.categories);
        final int Array_Count = katigories.length;

        LinearLayout my_layout = (LinearLayout)findViewById(R.id.activity_shop);

        for (int i = 0; i < Array_Count-6; i++) {
            final String kat = katigories[i+6];
            TableRow row = new TableRow(this);
            row.setId(i);
            row.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,3));
            final Button buyButton = new Button(this);
            if(!agorasmena.getBoolean(katigories[i+6],false)&& (lefta.getInt("lefta",0) > 1)) {
                buyButton.setBackgroundResource(R.drawable.shop_prasino);
            }
            else{
                buyButton.setBackgroundResource(R.drawable.btn_bg);
            }
            buyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    alert(buyButton,kat,Array_Count-6);
                }
            });
            buyButton.setId(i);
            buyButton.setText(onomataKatigorion[i+6]);
            buyButton.setTextSize(26);
            buyButton.setEnabled(!agorasmena.getBoolean(katigories[i+6],false));
            row.addView(buyButton);
            my_layout.addView(row);
        }

        Button home = new Button(this);
        home.setBackgroundResource(R.drawable.btn_bg);
        home.setText("ΑΡΧΙΚΗ");
        home.setTextSize(30);
        home.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,0));
        my_layout.addView(home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });
        //TODO meros ton diafimiseon
        //loadRewardedVideoAd();

        /*
        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);


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

        //xromatismos se prasino otan exei ta lefta gia agora
        if(!agorasmena.getBoolean("new1",false)&& (lefta.getInt("lefta",0) > 1)) {
            new1.setBackgroundResource(R.drawable.shop_prasino);
        }
        if (!agorasmena.getBoolean("new2",false)&& (lefta.getInt("lefta",0) > 1)) {
            new2.setBackgroundResource(R.drawable.shop_prasino);
        }
        if (!agorasmena.getBoolean("new3",false)&& (lefta.getInt("lefta",0) > 1)) {
            new3.setBackgroundResource(R.drawable.shop_prasino);
        }

        */
    }

    public void alert(final Button koumbi, final String onomaAgoras, final int plithosKoumbion){

        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);

        final int paliaLefta = lefta.getInt("lefta", 0);

        AlertDialog alertDialog;
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyAlertDialogStyle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        //AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.testing));
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(200, 400);


        //TODO sovaro titlo kai genika olo afto allagi me sostes times ktlp
        builder.setTitle("Αγορά");
        //Start setting up the builder
        builder.setMessage("Είσαι σίγουρος ότι θες να αγοράσεις την κατηγορία "+koumbi.getText()+" ? Έχεις " +Integer.toString(paliaLefta)+ " και κοστίζει 2");

        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                koumbi.setEnabled(false);
                koumbi.setBackgroundResource(R.drawable.shop_btn);

                //TODO min ksexasoume na allaksoume times
                int neaLefta = paliaLefta - 2;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", neaLefta);
                lefta_editor.apply();

                SharedPreferences.Editor editor = agorasmena.edit();
                editor.putBoolean(onomaAgoras, true);
                editor.apply();

                //TODO edo einai gia dinamiko ksevapsimo ton koumpion apo prassino alla den vrisko pos na vrisko ta koumbia
                if(lefta.getInt("lefta",0) < 2) {
                    for (int i = 0; i < plithosKoumbion; i++) {

                    }
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
                //TODO kai edo allagi noumeron
                if(paliaLefta < 2)
                    ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });
        dialog.show();
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
