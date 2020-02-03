package com.example.demo01.activities.models;

import android.net.Uri;
import android.widget.ImageView;

public class Familia {

    private int id;
    private String nombre, descripcion, clave;
    private Uri imagenFamilia;

    public Familia() {
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Familia(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Familia(int id, String nombre, String descripcion, String clave, Uri imagenFamilia) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.clave = clave;
        this.imagenFamilia = imagenFamilia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Uri getImagenFamilia() {
        return imagenFamilia;
    }

    public void setImagenFamilia(Uri imagenFamilia) {
        this.imagenFamilia = imagenFamilia;
    }
}
