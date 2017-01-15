package com.example.softandroid.convoituragev2.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;
import com.example.softandroid.convoituragev2.entity.Personne;
import com.example.softandroid.convoituragev2.service.Services;

/**
 * Created by el-habib on 28/10/16.
 */

public class Couvoiturage extends Application{

    private static RequestQueue mRequestQueue = null;
    public static Services services = null;

    private Personne personneInSession = null;

    {

        Cache cache = new NoCache();
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        // Don't forget to start the volley request queue
        mRequestQueue.start();
    }

    public Services getServices(){
        if(services == null){
            services = new Services();
        }
        return services;
    }

    public static void addRequest(Request request){
        mRequestQueue.add(request);
    }

    public Personne getPersonneInSession() {
        return personneInSession;
    }

    public void setPersonneInSession(Personne personneInSession) {
        this.personneInSession = personneInSession;
    }
}
