package com.example.softandroid.convoituragev2.service;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.softandroid.convoituragev2.Listener.VolleyCallBack;
import com.example.softandroid.convoituragev2.application.Couvoiturage;
import com.example.softandroid.convoituragev2.entity.Personne;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by el-habib on 28/10/16.
 */

public class Services {

    public static final String ADRESSE_SERVICE_WEB = "http://10.42.0.1";
    public static final String URL_INSCRIRE_PERSONNE = ADRESSE_SERVICE_WEB+"/personne/";


    public Services(){
    }


    public void inscrire(Personne personne, final VolleyCallBack<Boolean> callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_INSCRIRE_PERSONNE+"/"+personne.getNom()+"/"+personne.getPrenom()+"/"+personne.getEmail()+"/"+personne.getMotdepasse(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.sameOpperation();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    callBack.onSuccess(jsonObject.getBoolean("resultat"));
                } catch (JSONException e) {
                   callBack.errorNetwork();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.errorNetwork();
            }
        });

        Couvoiturage.addRequest(stringRequest);
    }


}
