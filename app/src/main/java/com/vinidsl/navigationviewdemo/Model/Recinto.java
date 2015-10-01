package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 30/09/2015.
 */
public class Recinto {

    String titulo;
    String descripcion;
    String calle;
    String numExt;
    String numInt;
    String colonia;
    String delegacion;
    String ciudad;
    String estado;
    String cp;
    String pais;
    String facebook;
    String twitter;
    String telefono;
    String email;

    public Recinto(String titulo, String descripcion, String calle, String numExt, String numInt,
                   String colonia, String delegacion, String ciudad, String estado, String cp,
                   String pais, String facebook, String twitter, String telefono, String email) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.calle = calle;
        this.numExt = numExt;
        this.numInt = numInt;
        this.colonia = colonia;
        this.delegacion = delegacion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.cp = cp;
        this.pais = pais;
        this.facebook = facebook;
        this.twitter = twitter;
        this.telefono = telefono;
        this.email = email;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumExt() {
        return numExt;
    }

    public String getNumInt() {
        return numInt;
    }

    public String getColonia() {
        return colonia;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public String getCp() {
        return cp;
    }

    public String getPais() {
        return pais;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

}
