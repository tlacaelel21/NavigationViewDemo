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
                {"1", "Nombre", "55 1234 5678" , "https://lh3.ggpht.com/-YB6KnEchAo4/UPVTr9sLlbI/AAAAAAAAADg/3I6mHnx0XWM/s1600/icono-patrocinadores-y-colaboradores.png" , "0"},
                {"2", "Nombre", "55 1234 5678" , "https://lh3.ggpht.com/-YB6KnEchAo4/UPVTr9sLlbI/AAAAAAAAADg/3I6mHnx0XWM/s1600/icono-patrocinadores-y-colaboradores.png" , "0"},
                {"3", "Nombre", "55 1234 5678" , "https://lh3.ggpht.com/-YB6KnEchAo4/UPVTr9sLlbI/AAAAAAAAADg/3I6mHnx0XWM/s1600/icono-patrocinadores-y-colaboradores.png" , "0"},
                {"4", "Nombre", "55 1234 5678" , "https://lh3.ggpht.com/-YB6KnEchAo4/UPVTr9sLlbI/AAAAAAAAADg/3I6mHnx0XWM/s1600/icono-patrocinadores-y-colaboradores.png" , "1"},
                {"5", "Nombre", "55 1234 5678" , "https://lh3.ggpht.com/-YB6KnEchAo4/UPVTr9sLlbI/AAAAAAAAADg/3I6mHnx0XWM/s1600/icono-patrocinadores-y-colaboradores.png" , "2"},
                {"6", "Nombre", "55 1234 5678" , "https://lh3.ggpht.com/-YB6KnEchAo4/UPVTr9sLlbI/AAAAAAAAADg/3I6mHnx0XWM/s1600/icono-patrocinadores-y-colaboradores.png" , "3"},
        };

        ArrayList<Patrocinador> listaM = new ArrayList<Patrocinador>();

        for (int i=0; i< patrocinadores.length; i++) {
            int estado = Integer.parseInt(patrocinadores[i][4]);
            long id = Long.parseLong(patrocinadores[i][0]);
            String nombre = "", numero = "", path = "";
            nombre = patrocinadores[i][1];
            numero = patrocinadores[i][2];
            path = patrocinadores[i][3];
            Patrocinador objeto = new Patrocinador(id, estado, nombre, numero, path);
            listaM.add(objeto);
        }

        ListView lista = (ListView) findViewById(R.id.patrocinadores_lista);

        PatrocinadoresAdapter adapter = new PatrocinadoresAdapter(this, listaM);
        lista.setAdapter(adapter);


    }


}
