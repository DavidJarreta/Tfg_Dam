package com.example.expending;

import java.io.Serializable;

public class Maquina implements Serializable
{
    private int id;
    private String nombre_empresa, latitud, longitud;

    public Maquina() {

    }

    public Maquina(int id, String nombre_empresa, String latitud, String longitud) {
        this.id = id;
        this.nombre_empresa = nombre_empresa;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return id + ", " + nombre_empresa;
    }
}
