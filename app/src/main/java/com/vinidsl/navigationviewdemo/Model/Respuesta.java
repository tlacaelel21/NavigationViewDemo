package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 21/08/2015.
 */
public class Respuesta {

    long id;
    String respuesta;

    public Respuesta(long id, String respuesta) {
        this.id = id;
        this.respuesta = respuesta;
    }

    public long getId() {
        return id;
    }

    public String getRespuesta() {
        return respuesta;
    }
}
