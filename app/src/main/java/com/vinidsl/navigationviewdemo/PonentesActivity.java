package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.vinidsl.navigationviewdemo.Adapter.PatrocinadoresAdapter;
import com.vinidsl.navigationviewdemo.Adapter.PonentesAdapter;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.Model.Ponente;

import java.util.ArrayList;

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


        String[][] ponentes = {
                {"1", "Nombre", "Empresa, Puesto" , "" , "0"},
                {"2", "Nombre", "Empresa, Puesto" , "" , "0"},
                {"3", "Nombre", "Empresa, Puesto" , "" , "0"},
                {"4", "Nombre", "Empresa, Puesto" , "http://www.youtube.com/yt/brand/media/image/YouTube-icon-full_color.png" , "1"},
                {"5", "Nombre", "Empresa, Puesto" , "" , "2"},
                {"6", "Nombre", "Empresa, Puesto" , "" , "3"},
        };

        ArrayList<Ponente> listaM = new ArrayList<Ponente>();

        for (int i=0; i< ponentes.length; i++) {
            int estado = Integer.parseInt(ponentes[i][4]);
            long id = Long.parseLong(ponentes[i][0]);
            String nombre = "", numero = "", path = "";
            nombre = ponentes[i][1];
            numero = ponentes[i][2];
            path = ponentes[i][3];
            Ponente objeto = new Ponente(id, estado, nombre, numero, path);
            listaM.add(objeto);
        }

        ListView lista = (ListView) findViewById(R.id.ponentes_lista);

        PonentesAdapter adapter = new PonentesAdapter(this, listaM);
        lista.setAdapter(adapter);


    }

}
