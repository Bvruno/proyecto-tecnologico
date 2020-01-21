package com.example.demo01;

import android.media.Image;

public class Actividad {
    private String nombre, actividad, recompensa;
    private Image imgActividad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }

    public Image getImgActividad() {
        return imgActividad;
    }

    public void setImgActividad(Image imgActividad) {
        this.imgActividad = imgActividad;
    }
}
