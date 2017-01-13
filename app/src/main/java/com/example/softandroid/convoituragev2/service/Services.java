package com.example.softandroid.convoituragev2.service;


import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by el-habib on 28/10/16.
 */

public class Services {

    public static final String ADRESSE_SERVICE_WEB = "http://10.42.0.1";
    public static final String URL_INSCRIRE_PERSONNE = ADRESSE_SERVICE_WEB+"/personnes";
    public static final String URL_AUTHENTIFICATION = ADRESSE_SERVICE_WEB+"/personnes";
    public static final String URL_PUBLIER_COUVOITURAGE = ADRESSE_SERVICE_WEB+"/personnes";
    public static final String URL_RECHERCHE_COUVOITURAGE = ADRESSE_SERVICE_WEB+"/couvoiturages";
    public static final String URL_INSRIRE_COUVOITURAGE = ADRESSE_SERVICE_WEB+"/personnes";


    public Services(){
    }


    public void inscrirePersonne(final Personne personne, final VolleyCallBack<Boolean> callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSCRIRE_PERSONNE,
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nom",personne.getNom());
                params.put("prenom",personne.getPrenom());
                params.put("email",personne.getEmail());
                params.put("motdepasse",personne.getMotdepasse());
                return params;
            }
        };

        Couvoiturage.addRequest(stringRequest);
    }

    public void authentificationPersonne(String email,String motdepasse,final VolleyCallBack<Personne> callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_AUTHENTIFICATION + "?email=" + email + "&motdepasse=" + motdepasse, new Response.Listener<String>() {
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

    public void publierCouvoiturage(final Personne personne,final com.example.softandroid.convoituragev2.entity.Couvoiturage c,final VolleyCallBack<Boolean> callBack){
        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final String date = dfDate.format(c.getDate());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PUBLIER_COUVOITURAGE + "/" +personne.getId()+"/couvoiturages", new Response.Listener<String>() {
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("villeD",c.getVilleD());
                params.put("villeA",c.getVilleA());
                params.put("marque",c.getMarque());
                params.put("message",c.getMessage());
                params.put("place",String.valueOf(c.getPlace()));
                params.put("date",date);
                return params;
             }
        };

        Couvoiturage.addRequest(stringRequest);
    }

    public void rechercheCouvoiturage(String villeD, String villeA, Date date, final VolleyCallBack<ArrayList<com.example.softandroid.convoituragev2.entity.Couvoiturage>> callBack){
        final DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateS = dfDate.format(date);


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_RECHERCHE_COUVOITURAGE + "?villeD=" + villeD + "&villeA" + villeA + "&date" + dateS, new Response.Listener<String>() {
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
                        String marque = jsonObject.getString("marque");
                        String message =jsonObject.getString("message");
                        int place = jsonObject.getInt("place");
                        com.example.softandroid.convoituragev2.entity.Couvoiturage a =
                                new com.example.softandroid.convoituragev2.entity.Couvoiturage(id,villeD,villeA,date1,marque,message,place);
                        jsonObject = jsonObject.getJSONObject("personne");
                        int idP = jsonObject.getInt("id");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String email = jsonObject.getString("email");
                        String motdepasse = jsonObject.getString("motdepasse");
                        Personne publier = new Personne(nom,prenom,email,motdepasse,idP);
                        a.setPublier(publier);
                        couvoiturages.add(a);
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


    public void insrireCouvoiturage(final Personne p, final com.example.softandroid.convoituragev2.entity.Couvoiturage c,final VolleyCallBack<Boolean> callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_INSRIRE_COUVOITURAGE  + "/" + p.getId() + "/" +c.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.sameOpperation();
                try{
                    JSONObject jsonObject= new JSONObject(response);
                    Boolean b = jsonObject.getBoolean("resultat");
                    callBack.onSuccess(b);
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

}
