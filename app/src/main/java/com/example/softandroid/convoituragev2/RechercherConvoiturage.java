package com.example.softandroid.convoituragev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softandroid.convoituragev2.application.Couvoiturage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RechercherConvoiturage extends AppCompatActivity {

    Couvoiturage couvoiturage;
    EditText villeDepartEditText;
    EditText villeArriveEditText;
    EditText dateEditText;
    EditText heureEditText;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat heureFormat =new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_convoiturage);
        couvoiturage = (Couvoiturage)getApplication();
        villeDepartEditText = (EditText)findViewById(R.id.villeDEditText);
        villeArriveEditText = (EditText)findViewById(R.id.villeAEditText);
        dateEditText = (EditText)findViewById(R.id.dateEditText);
        heureEditText = (EditText)findViewById(R.id.heureEditText);
    }

    public void rechercheCouvoiturage(View view){
        if(villeArriveEditText.length() == 0 || villeDepartEditText.length() == 0 ||
                dateEditText.length() == 0 || heureEditText.length() == 0){
            Toast.makeText(getApplicationContext(),"Vous devez enseigner tous les champs",Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Date dateD  = dateFormat.parse(dateEditText.getText().toString());
            Date dateH = heureFormat.parse(heureEditText.getText().toString());
            Date date = new Date(dateD.getTime()+dateH.getTime());
            String villeD = villeDepartEditText.getText().toString();
            String villeA = villeArriveEditText.getText().toString();
            Intent intent  = new Intent(getApplicationContext(),ResultatRecherche.class);
            intent.putExtra("villeD",villeD);
            intent.putExtra("villeA",villeA);
            intent.putExtra("date",date.getTime());
            startActivity(intent);
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Vous devez enseigner tous les champs", Toast.LENGTH_LONG).show();
        }
    }
}
