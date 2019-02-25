package com.nikalexion.milasiskas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;


public class Shop extends AppCompatActivity implements RewardedVideoAdListener {

    public static final String PREF_AGORES = "AGORES";
    public static final String PREF_EPILOGES = "EPILOGES";
    private int plithosKatigorion;
    //pfk = plithos free katigorion
    private int pfk = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    private RewardedVideoAd myRewardAd;
    //global metavlites gia kseklidoma katigorion
    String unlockName;
    ToggleButton unlockButton;
    AlertDialog alertBox;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences epiloges = getApplicationContext().getSharedPreferences(PREF_EPILOGES, 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SharedPreferences agorasmena = getApplicationContext().getSharedPreferences(PREF_AGORES, 0);

        String[] onomataKatigorion = getResources().getStringArray(R.array.category_names);
        String[] katigories = getResources().getStringArray(R.array.categories);
        plithosKatigorion = katigories.length;
        LinearLayout my_layout = (LinearLayout)findViewById(R.id.activity_shop);

        // Use an activity context to get the rewarded video instance.
        myRewardAd = MobileAds.getRewardedVideoAdInstance(this);
        myRewardAd.setRewardedVideoAdListener(this);

        AdRequest adRq = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C29EE19EA0561C4586ADCA4FBE4BFC9E")
                .build();
        myRewardAd.loadAd("ca-app-pub-3940256099942544/5224354917",adRq);


        for (int i = 0; i < plithosKatigorion-pfk; i++) {
            final String kat = katigories[i+pfk];
            TableRow row = new TableRow(this);
            row.setId(i+200);
            row.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,3));
            final ToggleButton buyButton = new ToggleButton(this);
            buyButton.setTextOff(onomataKatigorion[i]);
            buyButton.setTextOn(onomataKatigorion[i]);

            if(!agorasmena.getBoolean(katigories[i+ pfk],false)) {
                buyButton.setBackgroundResource(R.drawable.locked);
                buyButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alertBox = alert(buyButton,kat);
                    }
                });
            }
            else{
                if (epiloges.getBoolean(katigories[i],false)) {

                    buyButton.setBackgroundResource(R.drawable.active);
                }
                else{
                    buyButton.setBackgroundResource(R.drawable.epilogi);
                }
                buyButton.setChecked(epiloges.getBoolean(katigories[i],false));
                buyButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        saveInSp(getResources().getStringArray(R.array.categories)[buyButton.getId()],buyButton.isChecked());
                        if (buyButton.isChecked()) {

                            buyButton.setBackgroundResource(R.drawable.active);
                        }
                        else{
                            buyButton.setBackgroundResource(R.drawable.epilogi);
                        }
                    }
                });
            }
            buyButton.setId(i);
            buyButton.setText(onomataKatigorion[i+pfk]);
            buyButton.setTextSize(21);
            //buyButton.setEnabled(!agorasmena.getBoolean(katigories[i+pfk],false));
            ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.FILL_PARENT, ScrollView.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,25);
            row.setLayoutParams(params);
            row.addView(buyButton);
            my_layout.addView(row);
        }


        Button home = new Button(this);
        home.setBackgroundResource(R.drawable.button);
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

    }

    @Override
    public void onRewarded(RewardItem reward) {
        unlockReward();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
       
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadNewAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        loadNewAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        try {
            alertBox.getButton(AlertDialog.BUTTON_POSITIVE).setText("Ok");
            alertBox.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    private void saveInSp(String key, boolean value) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF_EPILOGES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public AlertDialog alert(final ToggleButton koumbi, final String onomaAgoras){

        //protetimasia metavliton gia kseklidoma katigorias kai disable koubiou
        unlockName = onomaAgoras;
        unlockButton = koumbi;

        AlertDialog alertDialog;
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyAlertDialogStyle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(200, 400);



        builder.setTitle("Αγορά νέας κατηγορίας");
        //Start setting up the builder
        builder.setMessage("Είσαι σίγουρος ότι θες να αποκτήσεις την κατηγορία "+koumbi.getText()+"?\n\nΘα χρειαστεί να δεις μια διαφήμιση ");

        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                // User clicked OK button
                if(myRewardAd.isLoaded()) {
                    myRewardAd.show();
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
                //An i diafimisi den fortose kanei disable to "ok"
                if(!myRewardAd.isLoaded()) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setText("Ξανα προσπάθησε σε λίγο");
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

        });
        dialog.show();

        return dialog;
    }
    
    public void unlockReward(){
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);
        final SharedPreferences epiloges = getSharedPreferences(PREF_EPILOGES, 0);

        SharedPreferences.Editor editor = agorasmena.edit();
        editor.putBoolean(unlockName, true);
        editor.apply();

        SharedPreferences.Editor epiloges_editor = epiloges.edit();
        epiloges_editor.putBoolean(unlockName, true);
        epiloges_editor.apply();

        unlockButton.setEnabled(true);
        unlockButton.setBackgroundResource(R.drawable.active);
        unlockButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveInSp(getResources().getStringArray(R.array.categories)[unlockButton.getId()],unlockButton.isChecked());
                if (unlockButton.isChecked()) {

                    unlockButton.setBackgroundResource(R.drawable.active);
                }
                else{
                    unlockButton.setBackgroundResource(R.drawable.epilogi);
                }
            }
        });

        /* FIREBASE STUFF
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, onomaUnlock);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "katigoria");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
       */


    }

    public void loadNewAd(){
        AdRequest adRq = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("C29EE19EA0561C4586ADCA4FBE4BFC9E")
                .build();
        myRewardAd.loadAd("ca-app-pub-3940256099942544/5224354917",adRq);
    }


}
