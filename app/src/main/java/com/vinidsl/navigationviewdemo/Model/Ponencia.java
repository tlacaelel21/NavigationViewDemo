package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 17/08/2015.
 */
public class Ponencia {

    private long mId;
    private String mNombre;
    private String mFechaInicio;
    private String mLugar;

    public Ponencia(long id, String nombre, String fechaInicio, String lugar) {
        this.mId = id;
        this.mNombre = nombre;
        this.mFechaInicio = fechaInicio;
        this.mLugar = lugar;
    }

    public long getId() {
        return mId;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getFechaInicio() {
        return mFechaInicio;
    }

    public String getLugar() {
        return mLugar;
    }
}
