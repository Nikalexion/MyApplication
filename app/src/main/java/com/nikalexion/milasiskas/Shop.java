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


public class Shop extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_AGORES = "AGORES";
    public static final String PREF_EPILOGES = "EPILOGES";
    private int plithosKatigorion;
    //pfk = plithos free katigorion
    private int pfk = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        SharedPreferences lefta = getApplicationContext().getSharedPreferences(PREF_LEFTA, 0);
        SharedPreferences agorasmena = getApplicationContext().getSharedPreferences(PREF_AGORES, 0);

        String[] kostiKatigorion = getResources().getStringArray(R.array.costs);
        String[] onomataKatigorion = getResources().getStringArray(R.array.category_names);
        String[] katigories = getResources().getStringArray(R.array.categories);
        plithosKatigorion = katigories.length;
        LinearLayout my_layout = (LinearLayout)findViewById(R.id.activity_shop);

        for (int i = 0; i < plithosKatigorion-pfk; i++) {
            final String kat = katigories[i+pfk];
            final String cost = kostiKatigorion[i+pfk];
            TableRow row = new TableRow(this);
            row.setId(i+200);
            row.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT,3));
            final Button buyButton = new Button(this);
            if(!agorasmena.getBoolean(katigories[i+ pfk],false)&& (lefta.getInt("lefta",0) >= Integer.parseInt(cost))) {
                buyButton.setBackgroundResource(R.drawable.shop_prasino);
            }
            else{
                buyButton.setBackgroundResource(R.drawable.shop_btn);
            }
            buyButton.setId(i);
            buyButton.setText(onomataKatigorion[i+pfk]);
            buyButton.setTextSize(26);
            buyButton.setEnabled(!agorasmena.getBoolean(katigories[i+pfk],false));
            ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.FILL_PARENT, ScrollView.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,25);
            row.setLayoutParams(params);
            row.addView(buyButton);
            my_layout.addView(row);
            buyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    alert(buyButton,kat,cost);
                }
            });
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

    }

    public void alert(final Button koumbi, final String onomaAgoras, final String cost){
        final int costInt = Integer.parseInt(cost);
        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);
        final SharedPreferences epiloges = getSharedPreferences(PREF_EPILOGES, 0);

        final int paliaLefta = lefta.getInt("lefta", 0);

        AlertDialog alertDialog;
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyAlertDialogStyle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(200, 400);


        builder.setTitle("Αγορά νέας κατηγορίας");
        //Start setting up the builder
        builder.setMessage("Είσαι σίγουρος ότι θες να αγοράσεις την κατηγορία "+koumbi.getText()+"?\n\nΈχεις " +Integer.toString(paliaLefta)+ " νομίσματα και η κατηγορία κοστίζει "+cost);

        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                koumbi.setEnabled(false);
                koumbi.setBackgroundResource(R.drawable.shop_btn);

                int neaLefta = paliaLefta - costInt;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", neaLefta);
                lefta_editor.apply();

                SharedPreferences.Editor editor = agorasmena.edit();
                editor.putBoolean(onomaAgoras, true);
                editor.apply();

                SharedPreferences.Editor epiloges_editor = epiloges.edit();
                epiloges_editor.putBoolean(onomaAgoras, true);
                epiloges_editor.apply();

                String[] kostiKatigorion = getResources().getStringArray(R.array.costs);
                for (int i = 0; i < plithosKatigorion-pfk; i++) {
                    if (Integer.parseInt(kostiKatigorion[i+pfk]) > neaLefta) {
                        Button tstbtn;
                        tstbtn = (Button) findViewById(i);
                        tstbtn.setBackgroundResource(R.drawable.shop_btn);
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

                if(paliaLefta < costInt)
                    ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });
        dialog.show();
    }

}
