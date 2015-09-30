package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.vinidsl.navigationviewdemo.Tasks.DocumentosTask;


/**
 * Created by tlacaelel21 on 28/09/15.
 */
public class Documentos extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documentos);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String reg_id=extras.getString("reg_id");

        DocumentosTask task = new DocumentosTask(this);
        task.execute(reg_id);

    }


}

