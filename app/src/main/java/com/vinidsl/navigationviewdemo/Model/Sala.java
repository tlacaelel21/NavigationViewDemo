package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by JoseRogelio on 01/10/2015.
 */
public class Sala {

    String id;
    String desc;

    public Sala(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
