package com.example.projet;

import androidx.annotation.NonNull;

public class Mot {
    private String mot, traduction, categorie, imgWeb, imgLocal;
    private int id;

    public Mot(int id_mot, String m, String tr, String cat, String Web, String Local ){
        id = id_mot;
        mot = m;
        traduction = tr;
        categorie = cat;
        imgWeb = Web;
        imgLocal = Local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public String getTraduction() {
        return traduction;
    }

    public void setTraduction(String traduction) {
        this.traduction = traduction;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImgWeb() {
        return imgWeb;
    }

    public void setImgWeb(String imgWeb) {
        this.imgWeb = imgWeb;
    }

    public String getImgLocal() {
        return imgLocal;
    }

    public void setImgLocal(String imgLocal) {
        this.imgLocal = imgLocal;
    }



    @Override
    public String toString() {
        return this.getMot();
    }
}
