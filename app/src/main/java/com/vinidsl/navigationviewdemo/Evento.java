package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 27/07/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vinidsl.navigationviewdemo.Tasks.TaskEvento;


public class Evento extends AppCompatActivity {
    Button aButton;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String eventoId=extras.getString("idEvento");
        //TextInputLayout
        TaskEvento taskEvento=new TaskEvento(this);
        taskEvento.execute(eventoId);

    }


}


