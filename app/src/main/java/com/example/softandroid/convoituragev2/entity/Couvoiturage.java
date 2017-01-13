package com.example.softandroid.convoituragev2.entity;

import java.util.Date;

/**
 * Created by el-habib on 07/12/16.
 */

public class Couvoiturage {

    private int id;

    private String villeD;

    private String villeA;

    private Date date;

    private String marque;

    private String message;

    private int place;

    private Personne publier;

    public Couvoiturage() {
    }

    public Couvoiturage(int id, String villeD, String villeA, Date date, String marque, String message, int place) {
        this.id = id;
        this.villeD = villeD;
        this.villeA = villeA;
        this.date = date;
        this.marque = marque;
        this.message = message;
        this.place = place;
    }

    public Couvoiturage(String villeD, String villeA, Date date, String marque, String message, int place) {
        this.villeD = villeD;
        this.villeA = villeA;
        this.date = date;
        this.marque = marque;
        this.message = message;
        this.place = place;
    }

    public Couvoiturage(String villeD, String villeA, Date date) {
        this.villeD = villeD;
        this.villeA = villeA;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVilleA() {
        return villeA;
    }

    public void setVilleA(String villeA) {
        this.villeA = villeA;
    }

    public String getVilleD() {
        return villeD;
    }

    public void setVilleD(String villeD) {
        this.villeD = villeD;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Personne getPublier() {
        return publier;
    }

    public void setPublier(Personne publier) {
        this.publier = publier;
    }
}

