package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by root on 10/08/15.
 */
public class FeriasNacModel {
    private String titulo;
    private String descripcion;
    private String urlExterno;

    public FeriasNacModel(String titulo, String descripcion,String url) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlExterno=url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrlExterno() {
        return urlExterno;
    }
}
