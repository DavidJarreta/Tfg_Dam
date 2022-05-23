package com.example.expending;

import java.io.Serializable;

public class Albaran implements Serializable
{
    private int id, id_usuario, id_maquina, contador;
    private String estado_albaran, fecha, nombre_empresa, nombre_usuario;
    private double dinero;

    public Albaran() {
    }

    public Albaran(int id, int id_usuario, int id_maquina, int contador, String estado_albaran,
                   String fecha, String nombre_empresa, String nombre_usuario, double dinero) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_maquina = id_maquina;
        this.contador = contador;
        this.estado_albaran = estado_albaran;
        this.fecha = fecha;
        this.dinero = dinero;
        this.nombre_empresa = nombre_empresa;
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getEstado_albaran() {
        return estado_albaran;
    }

    public void setEstado_albaran(String estado_albaran) {
        this.estado_albaran = estado_albaran;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }
}
