package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by tlacaelel21 on 13/10/15.
 */
public class RegistroModel {

    long id;
    String desc;

    public RegistroModel(long id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public String getdesc() {
        return desc;
    }
}