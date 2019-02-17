package com.nikalexion.milasiskas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity (new Intent(this, MainActivity.class));
        finish();
    }
}

/*TODO na vroume ola ta shared preferences ti exoun mesa tous
to "EPILOGES" exei:
scoreTime int
scorePasa int
protiFora bool
str[i] bool (enas pinakas apo strings pou ola einai boolean)

to "AGORES" exei:
str[i] bool (enas pinakas apo strings pou ola einai boolean)

*/