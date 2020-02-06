package com.example.demo01.activities.models;

import java.util.Date;

public class Familia {

    private int id;
    private String nombre, descripcion, clave, idCreador, idFamilia, uriFamilia;
    private Date fecha;
    //private Uri uriFamilia;

    public Familia() {
    }

    public Familia(String nombre, String descripcion, String clave, String idCreador, String idFamilia, String uriFamilia, Date fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.clave = clave;
        this.idCreador = idCreador;
        this.idFamilia = idFamilia;
        this.uriFamilia = uriFamilia;
        this.fecha = fecha;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(String idCreador) {
        this.idCreador = idCreador;
    }

    public String getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(String idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getUriFamilia() {
        return uriFamilia;
    }

    public void setUriFamilia(String uriFamilia) {
        this.uriFamilia = uriFamilia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
