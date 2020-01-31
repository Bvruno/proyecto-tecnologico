package com.example.demo01.activities.models;

import android.net.Uri;
import android.widget.ImageView;

public class Familia {

    private int id;
    private String nombreFamilia, descripcionFamilia;
    private Uri imagenFamilia;

    public Familia() {
    }

    public Familia(int id, String nombreFamilia, String descripcionFamilia, Uri imagenFamilia) {
        this.id = id;
        this.nombreFamilia = nombreFamilia;
        this.descripcionFamilia = descripcionFamilia;
        this.imagenFamilia = imagenFamilia;
    }

    public Familia(String nombreFamilia, String descripcionFamilia) {
        this.nombreFamilia = nombreFamilia;
        this.descripcionFamilia = descripcionFamilia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getDescripcionFamilia() {
        return descripcionFamilia;
    }

    public void setDescripcionFamilia(String descripcionFamilia) {
        this.descripcionFamilia = descripcionFamilia;
    }

    public Uri getImagenFamilia() {
        return imagenFamilia;
    }

    public void setImagenFamilia(Uri imagenFamilia) {
        this.imagenFamilia = imagenFamilia;
    }
}
