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
import com.example.softandroid.convoituragev2.service.Services;

public class Inscription extends AppCompatActivity {

    private EditText nomEditText;
    private EditText prenomEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText password2EditText;
    Couvoiturage couvoiturageApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        nomEditText = (EditText)findViewById(R.id.nomEditText);
        prenomEditText = (EditText)findViewById(R.id.prenomEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        password2EditText = (EditText)findViewById(R.id.password2EditText);
        couvoiturageApplication = (Couvoiturage)getApplication();
    }

    public void inscrire(View view){
        if(nomEditText.getText().length() == 0 || prenomEditText.getText().length() ==0 ||
                emailEditText.getText().length() == 0 || passwordEditText.getText().length() ==0 ||
                password2EditText.getText().length() == 0){
            Toast.makeText(getApplicationContext(),"Les champs doivent être tous renseignés",Toast.LENGTH_LONG).show();
            return;
        }
        if(!passwordEditText.getText().equals(password2EditText.getText())){
            Toast.makeText(getApplicationContext(),"Les mots de passe ne sont pas identiques",Toast.LENGTH_LONG).show();
            return;
        }
        final Personne personne = new Personne(nomEditText.getText().toString(),prenomEditText.getText().toString(),
                emailEditText.getText().toString(),passwordEditText.getText().toString());

        couvoiturageApplication.getServices().inscrirePersonne(personne, new VolleyCallBack<Boolean>() {
            @Override
            public void sameOpperation() {

            }

            @Override
            public void onSuccess(Boolean result) {
                if(!result){
                    Toast.makeText(getApplicationContext(),"Email "+personne.getEmail()+"a été déja utilisé",Toast.LENGTH_LONG).show();
                }else{
                    couvoiturageApplication.getServices().authentificationPersonne(personne.getEmail(), personne.getMotdepasse(), new VolleyCallBack<Personne>() {
                        @Override
                        public void sameOpperation() {

                        }

                        @Override
                        public void onSuccess(Personne result) {
                            couvoiturageApplication.setPersonneInSession(result);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void errorNetwork() {
                            Toast.makeText(getApplicationContext(),"erreur de connection vérifier votre connexion",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void errorNetwork() {
                Toast.makeText(getApplicationContext(),"erreur de connection vérifier votre connexion",Toast.LENGTH_LONG).show();
            }
        });

    }
}
