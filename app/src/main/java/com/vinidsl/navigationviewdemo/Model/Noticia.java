package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 06/08/2015.
 */
public class Noticia {

    private long id;
    private String titulo;
    private String fecha;
    private String pathFoto;
    private String contenido;

    public Noticia(long id, String titulo, String fecha, String pathFoto, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.pathFoto = pathFoto;
        this.contenido = contenido;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public String getContenido() {
        return contenido;
    }
}
