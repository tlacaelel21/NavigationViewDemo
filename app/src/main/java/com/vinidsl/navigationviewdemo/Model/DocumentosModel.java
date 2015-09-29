package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by tlacaelel21 on 28/09/15.
 */
public class DocumentosModel {
    private String nombre_documento;
    private String url_documento;
    private String doc_visto;

    public DocumentosModel(String nombre_documento, String url_documento,String doc_visto) {
        this.nombre_documento = nombre_documento;
        this.url_documento = url_documento;
        this.doc_visto=doc_visto;
    }

    public String geturl_documento() {
        return url_documento;
    }

    public String getnombre_documento() {
        return nombre_documento;
    }

    public String getdoc_visto() {
        return doc_visto;
    }
}
