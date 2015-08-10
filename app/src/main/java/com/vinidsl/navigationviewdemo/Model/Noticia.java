package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 06/08/2015.
 */
public class Noticia {

    private long mId;
    private String mTitulo;
    private String mFecha;
    private String mPathFoto;
    private String mContenido;

    public Noticia(long id, String titulo, String fecha, String pathFoto, String contenido) {
        this.mId = id;
        this.mTitulo = titulo;
        this.mFecha = fecha;
        this.mPathFoto = pathFoto;
        this.mContenido = contenido;
    }

    public long getId() {
        return mId;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public String getFecha() {
        return mFecha;
    }

    public String getPathFoto() {
        return mPathFoto;
    }

    public String getContenido() {
        return mContenido;
    }
}
