package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by tlacaelel21 on 29/09/15.
 */
public class ExpositoresModel {
    private String com_nom;
    private String com_www;
    private String act_desc;
    private String com_foto;
    //expositores:[{com_nom, com_www, act_desc, com_foto}, {}, {}]
    public ExpositoresModel(String com_nom, String com_www,String act_desc,String com_foto) {
        this.com_nom = com_nom;
        this.com_www = com_www;
        this.act_desc=act_desc;
        this.com_foto=com_foto;
    }

    public String getCom_www() {
        return com_www;
    }

    public String getCom_nom() {
        return com_nom;
    }

    public String getAct_desc() {
        return act_desc;
    }

    public String getCom_foto() {
        return com_foto;
    }
}
