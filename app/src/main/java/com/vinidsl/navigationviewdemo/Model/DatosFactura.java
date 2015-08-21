package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 17/08/2015.
 */
public class DatosFactura {

    private long id;
    private String rfc;
    private String rs;
    private String calle;
    private String numInt;
    private String numExt;
    private String colonia;
    private String codigoPostal;
    private String municipio;
    private String estado;

    public DatosFactura(long id, String rfc, String rs, String calle, String numInt, String numExt,
                        String colonia, String codigoPostal, String municipio, String estado) {
        this.id = id;
        this.rfc = rfc;
        this.rs = rs;
        this.calle = calle;
        this.numInt = numInt;
        this.numExt = numExt;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.municipio = municipio;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public String getRfc() {
        return rfc;
    }

    public String getRs() {
        return rs;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumInt() {
        return numInt;
    }

    public String getNumExt() {
        return numExt;
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
}
