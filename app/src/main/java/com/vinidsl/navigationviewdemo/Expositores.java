package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vinidsl.navigationviewdemo.Tasks.DocumentosTask;
import com.vinidsl.navigationviewdemo.Tasks.ExpositoresTask;

/**
 * Created by tlacaelel21 on 29/09/15.
 */
public class Expositores extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expositores);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id_evento=extras.getString("id_evento");

        ExpositoresTask task = new ExpositoresTask(this);
        task.execute(id_evento);

    }


}