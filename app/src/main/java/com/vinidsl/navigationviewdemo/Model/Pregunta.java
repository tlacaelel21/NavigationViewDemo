package com.vinidsl.navigationviewdemo.Model;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 21/08/2015.
 */
public class Pregunta {

    long id;
    String caption;
    String tipo;
    ArrayList<Respuesta> respuestas;

    public Pregunta(long id, String caption, String tipo, ArrayList<Respuesta> respuestas) {
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

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }
}
