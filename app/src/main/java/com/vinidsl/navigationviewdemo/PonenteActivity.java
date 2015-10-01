package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vinidsl.navigationviewdemo.Tasks.DetallePonenteTask;
import com.vinidsl.navigationviewdemo.Tasks.PonentesTask;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class PonenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            //String idPonente = "" + extras.getLong("3");
            long id=extras.getLong("id");

            DetallePonenteTask task = new DetallePonenteTask(this);
            task.execute(""+id);
        }

    }

}
