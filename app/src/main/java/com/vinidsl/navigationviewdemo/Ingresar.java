package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by root on 31/07/15.
 */
public class Ingresar extends Activity {
    public void Ingresar(){
        Intent i = new Intent(Ingresar.this, MainActivity.class);  //your class
        startActivity(i);
        finish();
    }
}
