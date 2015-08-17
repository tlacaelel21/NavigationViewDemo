package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.vinidsl.navigationviewdemo.Adapter.NoticiasAdapter;
import com.vinidsl.navigationviewdemo.Adapter.PatrocinadoresAdapter;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;

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


        String[][] patrocinadores = {
                {"1", "Nombre", "55 1234 5678" , "" , "0"},
                {"2", "Nombre", "55 1234 5678" , "" , "0"},
                {"3", "Nombre", "55 1234 5678" , "" , "0"},
                {"4", "Nombre", "55 1234 5678" , "http://www.youtube.com/yt/brand/media/image/YouTube-icon-full_color.png" , "1"},
                {"5", "Nombre", "55 1234 5678" , "" , "2"},
                {"6", "Nombre", "55 1234 5678" , "" , "3"},
        };

        ArrayList<Patrocinador> listaM = new ArrayList<Patrocinador>();

        for (int i=0; i< patrocinadores.length; i++) {
            int estado = Integer.parseInt(patrocinadores[i][4]);
            long id = Long.parseLong(patrocinadores[i][0]);
            String nombre = "", numero = "", path = "", url = "";
            nombre = patrocinadores[i][1];
            numero = patrocinadores[i][2];
            path = patrocinadores[i][3];
            url = patrocinadores[i][4];
            Patrocinador objeto = new Patrocinador(id, estado, nombre, numero, path, url);
            listaM.add(objeto);
        }

        ListView lista = (ListView) findViewById(R.id.patrocinadores_lista);

        PatrocinadoresAdapter adapter = new PatrocinadoresAdapter(this, listaM);
        lista.setAdapter(adapter);


    }


}
