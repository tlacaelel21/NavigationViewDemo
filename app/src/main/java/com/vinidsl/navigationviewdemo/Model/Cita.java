package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 01/10/2015.
 */
public class Cita {

    String id;
    String fecha;
    String usuario;
    String sala;
    String confirmado;

    public Cita(String id, String fecha, String usuario, String sala, String confirmado) {
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.sala = sala;
        this.confirmado = confirmado;
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSala() {
        return sala;
    }

    public String getConfirmado() {
        return confirmado;
    }
}
