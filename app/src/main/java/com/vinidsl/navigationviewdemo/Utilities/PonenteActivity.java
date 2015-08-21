package com.vinidsl.navigationviewdemo.Utilities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vinidsl.navigationviewdemo.R;
import com.vinidsl.navigationviewdemo.Tasks.PonentesTask;

/**
 * Created by JoseRogelio on 17/08/2015.
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

        String idEvento = "1";

        PonentesTask task = new PonentesTask(this);
        task.execute(idEvento);

    }
}
