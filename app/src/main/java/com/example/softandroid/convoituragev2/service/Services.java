package com.example.softandroid.convoituragev2.service;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.softandroid.convoituragev2.Listener.VolleyCallBack;
import com.example.softandroid.convoituragev2.application.Couvoiturage;
import com.example.softandroid.convoituragev2.entity.Personne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by el-habib on 28/10/16.
 */

public class Services {

    public static final String ADRESSE_SERVICE_WEB = "http://10.42.0.1";
    public static final String URL_INSCRIRE_PERSONNE = ADRESSE_SERVICE_WEB+"/personne";
    public static final String URL_AUTHENTIFICATION = ADRESSE_SERVICE_WEB+"/personne";
    public static final String URL_PUBLIER_COUVOITURAGE = ADRESSE_SERVICE_WEB+"/couvoiturage";
    public static final String URL_RECHERCHE_COUVOITURAGE = ADRESSE_SERVICE_WEB+"/couvoiturage";


    public Services(){
    }


    public void inscrirePersonne(Personne personne, final VolleyCallBack<Boolean> callBack){

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
                callBack.sameOpperation();
                callBack.errorNetwork();
            }
        });

        Couvoiturage.addRequest(stringRequest);
    }

    public void authentificationPersonne(String email,String motdepasse,final VolleyCallBack<Personne> callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_AUTHENTIFICATION + "/" + email + "/" + motdepasse, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.sameOpperation();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("resultat")){
                        jsonObject = jsonObject.getJSONObject("personne");
                        int id = jsonObject.getInt("id");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String email = jsonObject.getString("email");
                        String motdepasse = jsonObject.getString("motdepasse");
                        callBack.onSuccess(new Personne(nom,prenom,email,motdepasse,id));
                    }else{
                        callBack.onSuccess(null);
                    }
                }catch (JSONException e){
                    callBack.errorNetwork();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.sameOpperation();
                callBack.errorNetwork();
            }
        });
        Couvoiturage.addRequest(stringRequest);
    }

    public void publierCouvoiturage(com.example.softandroid.convoituragev2.entity.Couvoiturage c,final VolleyCallBack<Boolean> callBack){
        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfHeure = new SimpleDateFormat("HH:mm");
        String date = dfDate.format(c.getDate());
        String heure = dfHeure.format(c.getDate());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PUBLIER_COUVOITURAGE + "/" + c.getVilleD() + "/" + c.getVilleA() + "/" +
                        date + "/" + heure + "/" + c.getMarque() + "/" + c.getMessage() + "/" + c.getPlace(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.sameOpperation();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    callBack.onSuccess(jsonObject.getBoolean("resultat"));
                }catch (JSONException e){
                    callBack.errorNetwork();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.sameOpperation();
                callBack.errorNetwork();
            }
        });

        Couvoiturage.addRequest(stringRequest);
    }

    public void rechercheCouvoiturage(String villeD, String villeA, Date date, final VolleyCallBack<ArrayList<com.example.softandroid.convoituragev2.entity.Couvoiturage>> callBack){
        final DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfHeure = new SimpleDateFormat("HH:mm");
        String dateS = dfDate.format(date);
        String heure = dfHeure.format(date);


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_RECHERCHE_COUVOITURAGE + "/" + villeD + "/" + villeA + "/" + dateS + "/" + heure, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.sameOpperation();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<com.example.softandroid.convoituragev2.entity.Couvoiturage> couvoiturages = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String villeD = jsonObject.getString("villeD");
                        String villeA = jsonObject.getString("villeA");
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(jsonObject.getString("date"));
                        String heure = jsonObject.getString("heure");
                        String marque = jsonObject.getString("marque");
                        String message =jsonObject.getString("message");
                        int place = jsonObject.getInt("place");
                        couvoiturages.add(new com.example.softandroid.convoituragev2.entity.Couvoiturage(id,villeD,villeA,date1,marque,message,place));
                    }
                    callBack.onSuccess(couvoiturages);
                }catch (JSONException e){
                    callBack.errorNetwork();
                } catch (ParseException e) {
                    callBack.errorNetwork();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.sameOpperation();
                callBack.errorNetwork();
            }
        });

        Couvoiturage.addRequest(stringRequest);
    }


}
