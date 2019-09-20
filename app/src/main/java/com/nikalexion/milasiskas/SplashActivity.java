package com.nikalexion.milasiskas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ousiastika molis fortosei to splash activity fonazei to MainActivity
        //Afto to kanoume gia na fenete to background_splash pou exoume orisei os background tou
        //SplashActivity sto AndroidManifest oso i efarmogi fortonei (anti gia kapoio default keno screen)
        //kai gia na sigoureftoume oti i efarmogi tha exei fortisei prin ksekinisei na kanei to otidipote
        startActivity (new Intent(this, MainActivity.class));
        finish();
    }
}

/*TODO na vroume ola ta shared preferences ti exoun mesa tous
to "EPILOGES" exei:
scoreTime int
scorePasa int
protiFora bool (einai false an exei ksananoiksei tin efarmogi)
str[i] bool (enas pinakas apo strings pou ola einai boolean)

to "AGORES" exei:
str[i] bool (enas pinakas apo strings pou ola einai boolean)

*/