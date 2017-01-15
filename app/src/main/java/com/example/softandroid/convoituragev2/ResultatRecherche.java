package com.example.softandroid.convoituragev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.softandroid.convoituragev2.Listener.VolleyCallBack;
import com.example.softandroid.convoituragev2.application.Couvoiturage;

import java.util.ArrayList;
import java.util.Date;

public class ResultatRecherche extends AppCompatActivity {

    Couvoiturage couvoiturage;
    ListView listCouvoiturage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_recherche);
        couvoiturage = (Couvoiturage)getApplication();
        listCouvoiturage = (ListView)findViewById(R.id.listCouvoiturage);
        Intent intent = getIntent();
        String villeA = intent.getStringExtra("villeA");
        String villeD = intent.getStringExtra("villeD");
        Date date = new Date(intent.getLongExtra("date",0));
        couvoiturage.getServices().rechercheCouvoiturage(villeD, villeA, date, new VolleyCallBack<ArrayList<com.example.softandroid.convoituragev2.entity.Couvoiturage>>() {
            @Override
            public void sameOpperation() {

            }

            @Override
            public void onSuccess(ArrayList<com.example.softandroid.convoituragev2.entity.Couvoiturage> result) {
                CouvoiturageArrayAdapter couvoiturageArrayAdapter = new CouvoiturageArrayAdapter(getApplicationContext(),result,couvoiturage.getServices(),couvoiturage.getPersonneInSession());
                listCouvoiturage.setAdapter(couvoiturageArrayAdapter);
            }

            @Override
            public void errorNetwork() {
                Toast.makeText(getApplicationContext(),"erreur de connexion",Toast.LENGTH_LONG).show();
            }
        });
    }
}
