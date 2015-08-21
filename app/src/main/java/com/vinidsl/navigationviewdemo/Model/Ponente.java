package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class Ponente {

    public final static int ESTADO_SIN = 0;
    public final static int ESTADO_ACTIVO = 1;
    public final static int ESTADO_INACTIVO = 2;
    public final static int ESTADO_CANCELADO = 3;

    private long mId;
    private int mEstado;
    private String mNombre;
    private String mPuesto;
    private String mPathFoto;
    private String mCorreo;
    private String mCalificacion;

    public Ponente(long id, int estado, String nombre, String puesto, String pathFoto,
                   String correo, String calificacion) {
        this.mId = id;
        this.mEstado = estado;
        this.mNombre = nombre;
        this.mPuesto = puesto;
        this.mPathFoto = pathFoto;
        this.mCorreo = correo;
        this.mCalificacion = calificacion;
    }

    public long getId() {
        return mId;
    }

    public int getEstado() {
        return mEstado;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getPuesto() {
        return mPuesto;
    }

    public String getPathFoto() {
        return mPathFoto;
    }

    public String getCorreo() {
        return mCorreo;
    }

    public String getCalificacion() {
        return mCalificacion;
    }
}
