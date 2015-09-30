package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vinidsl.navigationviewdemo.Tasks.ExpositoresTask;
import com.vinidsl.navigationviewdemo.Tasks.InscripcionTask;

/**
 * Created by tlacaelel21 on 30/09/15.
 */
public class InscripcionFI extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscripcion_evento);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id_usr=extras.getString("id_usr");
        String id_evento=extras.getString("id_evento");

        InscripcionTask task = new InscripcionTask(this);
        task.execute(id_usr,id_evento);

    }


}
