package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by root on 14/08/15.
 */
public class MisEventosModel {
    private String nombre_evento;
    private String ubicacion_evento;
    private String status_evento;
    private long reg_id;

    public MisEventosModel(String nombre_evento, String ubicacion_evento,String status_evento,long reg_id) {
        this.nombre_evento = nombre_evento;
        this.ubicacion_evento = ubicacion_evento;
        this.status_evento=status_evento;
        this.reg_id=reg_id;
    }

    public String getUbicacion_evento() {
        return ubicacion_evento;
    }

    public String getNombre_evento() {
        return nombre_evento;
    }

    public String getStatus_evento() {
        return status_evento;
    }

    public long getReg_id() {
        return reg_id;
    }
}

