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




public class Shop extends AppCompatActivity {

    public static final String PREF_LEFTA = "LEFTA";
    public static final String PREF_AGORES = "AGORES";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //TODO na xrisimopoiountai ta "lefta" gia na vafei prassino/gri ta koumbia
        SharedPreferences lefta = getApplicationContext().getSharedPreferences(PREF_LEFTA, 0);
        SharedPreferences agorasmena = getApplicationContext().getSharedPreferences(PREF_AGORES, 0);

        String[] kostiKatigorion = getResources().getStringArray(R.array.costs);
        String[] onomataKatigorion = getResources().getStringArray(R.array.category_names);
        String[] katigories = getResources().getStringArray(R.array.categories);
        final int Array_Count = katigories.length;

        LinearLayout my_layout = (LinearLayout)findViewById(R.id.activity_shop);

        for (int i = 0; i < Array_Count-6; i++) {
            final String kat = katigories[i+6];
            final String cost = kostiKatigorion[i+6];
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
                    alert(buyButton,kat,Array_Count-6,cost);
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


        /*
        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);




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

    public void alert(final Button koumbi, final String onomaAgoras, final int plithosKoumbion, final String cost){
        final int costInt = Integer.parseInt(cost);
        final SharedPreferences lefta = getSharedPreferences(PREF_LEFTA, 0);
        final SharedPreferences agorasmena = getSharedPreferences(PREF_AGORES, 0);

        final int paliaLefta = lefta.getInt("lefta", 0);

        AlertDialog alertDialog;
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyAlertDialogStyle);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(200, 400);


        //TODO sovaro titlo kai genika olo afto allagi me sostes times ktlp
        builder.setTitle("Αγορά");
        //Start setting up the builder
        builder.setMessage("Είσαι σίγουρος ότι θες να αγοράσεις την κατηγορία "+koumbi.getText()+" ? Έχεις " +Integer.toString(paliaLefta)+ " και κοστίζει "+cost);

        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                koumbi.setEnabled(false);
                koumbi.setBackgroundResource(R.drawable.shop_btn);

                //TODO min ksexasoume na allaksoume times sto arrays
                int neaLefta = paliaLefta - costInt;
                SharedPreferences.Editor lefta_editor = lefta.edit();
                lefta_editor.putInt("lefta", neaLefta);
                lefta_editor.apply();

                SharedPreferences.Editor editor = agorasmena.edit();
                editor.putBoolean(onomaAgoras, true);
                editor.apply();

                //TODO edo einai gia dinamiko ksevapsimo ton koumpion apo prassino alla den vrisko pos na vrisko ta koumbia
                //ase pou me dinamikes times thelei ligi douleia vsk
                if(lefta.getInt("lefta",0) < 8) {
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

}
