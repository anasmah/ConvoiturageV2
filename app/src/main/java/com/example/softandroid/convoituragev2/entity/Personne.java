package com.example.softandroid.convoituragev2.entity;

/**
 * Created by el-habib on 07/12/16.
 */

public class Personne {


    private int id;

    private String nom;

    private String prenom;

    private String email;

    private String motdepasse;

    public Personne() {
    }

    public Personne(String nom, String prenom, String email, String motdepasse, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
        this.id = id;
    }

    public Personne(String nom, String prenom, String email, String motdepasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
