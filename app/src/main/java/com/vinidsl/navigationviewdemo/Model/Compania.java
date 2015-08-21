package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 17/08/2015.
 */
public class Compania {

    private long paisId;
    private long actId;
    private String nombre;
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private String codigoPostal;
    private String municipio;
    private String estado;
    private String paisDesc;
    private String telefono;
    private String urlSitio;
    private String mail;
    private String pathFoto;
    private String actDesc;

    public Compania(long paisId, long actId, String nombre, String calle, String numExt,
                    String numInt, String colonia, String codigo, String municipio, String estado,
                    String paisDesc, String telefono, String urlSitio, String mail,
                    String pathFoto, String actDesc) {
        this.paisId = paisId;
        this.actId = actId;
        this.nombre = nombre;
        this.calle = calle;
        this.numExt = numExt;
        this.numInt = numInt;
        this.colonia = colonia;
        this.codigoPostal = codigo;
        this.municipio = municipio;
        this.estado = estado;
        this.paisDesc = paisDesc;
        this.telefono = telefono;
        this.urlSitio = urlSitio;
        this.mail = mail;
        this.pathFoto = pathFoto;
        this.actDesc = actDesc;
    }

    public long getPaisId() {
        return paisId;
    }

    public long getActId() {
        return actId;
    }

    public String getNombre() {
        return nombre;
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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
    }

    public String getPaisDesc() {
        return paisDesc;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUrlSitio() {
        return urlSitio;
    }

    public String getMail() {
        return mail;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public String getActDesc() {
        return actDesc;
    }
}
