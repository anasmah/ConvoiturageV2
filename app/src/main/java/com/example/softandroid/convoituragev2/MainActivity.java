package com.example.softandroid.convoituragev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void publierCovoiturage(View view){

        Intent myIntent= new Intent(this,PublierConvoiturage.class);

        startActivity(myIntent);

    }

    public void seConnecter(View view){
        Intent myIntent= new Intent(this,SeConnecter.class);
        startActivity(myIntent);
    }

    public void rechercherCovoiturage(View view){
        Intent myIntent= new Intent(this,RechercherConvoiturage.class);
        startActivity(myIntent);
    }

}
