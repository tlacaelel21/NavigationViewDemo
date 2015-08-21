package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vinidsl.navigationviewdemo.Tasks.PonentesTask;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class PonentesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String idEvento = "1";

        PonentesTask task = new PonentesTask(this);
        task.execute(idEvento);

    }

}
