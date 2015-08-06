package com.vinidsl.navigationviewdemo;

import android.app.Application;

/**
 * Created by root on 30/07/15.
 */
public class GlobalClass extends Application {

    private String someVariable;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }
}