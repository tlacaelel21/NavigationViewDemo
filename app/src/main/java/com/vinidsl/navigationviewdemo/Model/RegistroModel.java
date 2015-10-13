package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by tlacaelel21 on 13/10/15.
 */
public class RegistroModel {

    long id;
    String caption;
    String tipo;
    Respuesta[] respuestas;

    public RegistroModel(long id, String caption, String tipo, Respuesta[] respuestas) {
        this.id = id;
        this.caption = caption;
        this.tipo = tipo;
        this.respuestas = respuestas;
    }

    public long getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getTipo() {
        return tipo;
    }

    public Respuesta[] getRespuestas() {
        return respuestas;
    }
}