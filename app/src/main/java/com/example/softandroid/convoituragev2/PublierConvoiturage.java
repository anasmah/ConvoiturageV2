package com.example.softandroid.convoituragev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softandroid.convoituragev2.Listener.VolleyCallBack;
import com.example.softandroid.convoituragev2.application.Couvoiturage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublierConvoiturage extends AppCompatActivity {

    Couvoiturage couvoiturageApplication;

    EditText villeDepartEditText;
    EditText villeArriveEditText;
    EditText dateEditText;
    EditText heureEditText;
    EditText nbPlaceEditText;
    EditText messageEditText;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat heureFormat =new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        couvoiturageApplication = (Couvoiturage)getApplication();
        if(couvoiturageApplication.getPersonneInSession() == null){
            startActivity(new Intent(this,SeConnecter.class));
            return;
        }
        setContentView(R.layout.activity_publier_convoiturage);

        villeDepartEditText = (EditText)findViewById(R.id.villeDepartEditText);
        villeArriveEditText = (EditText)findViewById(R.id.villeArriveEditText);
        dateEditText = (EditText)findViewById(R.id.dateEditText);
        heureEditText = (EditText)findViewById(R.id.heureEditText);
        nbPlaceEditText = (EditText)findViewById(R.id.nbPlaceEditText);
        messageEditText = (EditText)findViewById(R.id.messageEditText);

    }


    public void publierCouvoiturage(View view){
        if(villeDepartEditText.length() ==0 || villeArriveEditText.length() == 0 ||
                dateEditText.length() ==0 || heureEditText.length() == 0 ||
                nbPlaceEditText.length() == 0 || messageEditText.length() == 0){
            Toast.makeText(getApplicationContext(),"Vous devez enseigner tous les champs",Toast.LENGTH_LONG).show();
            return;
        }
        String villeD = villeDepartEditText.getText().toString();
        String villeA = villeArriveEditText.getText().toString();
        try {
            Date dateD  = dateFormat.parse(dateEditText.getText().toString());
            Date dateH = heureFormat.parse(heureEditText.getText().toString());
            Date date = new Date(dateD.getTime()+dateH.getTime());
            int nbPlace = Integer.parseInt(nbPlaceEditText.getText().toString());
            String message = messageEditText.getText().toString();
            com.example.softandroid.convoituragev2.entity.Couvoiturage couvoiturage
                    = new com.example.softandroid.convoituragev2.entity.Couvoiturage(villeD,villeA,date," ",message,nbPlace);
            couvoiturageApplication.getServices().publierCouvoiturage(couvoiturageApplication.getPersonneInSession(), couvoiturage, new VolleyCallBack<Boolean>() {
                @Override
                public void sameOpperation() {

                }

                @Override
                public void onSuccess(Boolean result) {
                    if(result){
                        Toast.makeText(getApplicationContext(),"Votre couvoiturage a été bien sauvegarder",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Erreur lors de sauvegard de ce couvoiturage",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void errorNetwork() {
                    Toast.makeText(getApplicationContext(),"Erreur de connexion",Toast.LENGTH_LONG).show();
                }
            });
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(),"Essayer une autre fois",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
