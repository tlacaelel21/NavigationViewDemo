package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.vinidsl.navigationviewdemo.Adapter.NoticiasAdapter;
import com.vinidsl.navigationviewdemo.Adapter.PatrocinadoresAdapter;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.Tasks.PatrocinadoresTask;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class PatrocinadoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrocinadores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String idEvento = "1";

        PatrocinadoresTask task = new PatrocinadoresTask(this);
        task.execute(idEvento);

    }


}
