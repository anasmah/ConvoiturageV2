package com.example.softandroid.convoituragev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softandroid.convoituragev2.Listener.VolleyCallBack;
import com.example.softandroid.convoituragev2.application.Couvoiturage;
import com.example.softandroid.convoituragev2.entity.Personne;

public class SeConnecter extends AppCompatActivity {

    Couvoiturage couvoiturage;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_connecter);
        couvoiturage = (Couvoiturage) getApplication();
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
    }

    public void inscrire(View view){

        Intent myIntent= new Intent(this,Inscription.class);

        startActivity(myIntent);

    }

    public void connect(View view){
        if(emailEditText.length() == 0 || passwordEditText.length() == 0){
            Toast.makeText(getApplication(),"Vous devez renseigner tous les champs",Toast.LENGTH_LONG).show();
            return;
        }
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        couvoiturage.getServices().authentificationPersonne(email, password, new VolleyCallBack<Personne>() {
            @Override
            public void sameOpperation() {

            }

            @Override
            public void onSuccess(Personne result) {
                couvoiturage.setPersonneInSession(result);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

            @Override
            public void errorNetwork() {
                Toast.makeText(getApplicationContext(),"Erreur de connexion",Toast.LENGTH_LONG).show();
            }
        });
    }
}
