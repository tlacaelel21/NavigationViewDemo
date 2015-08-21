package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 * Created by JoseRogelio on 07/08/2015.
 */
public class NoticiaActivity extends AppCompatActivity {

    AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {

            aQuery = new AQuery(this);

            String foto = extras.getString("pathFoto");
            String titulo = extras.getString("pathFoto");
            String contenido = extras.getString("pathFoto");
            String fecha = extras.getString("pathFoto");

            aQuery.id(R.id.noticia_foto).image(foto);

            TextView tituloTV = (TextView) findViewById(R.id.noticia_titulo);
            TextView contenidoTV = (TextView) findViewById(R.id.noticia_titulo);
            TextView fechaTV = (TextView) findViewById(R.id.noticia_titulo);

            tituloTV.setText(titulo);
            contenidoTV.setText(contenido);
            fechaTV.setText(fecha);

        }

    }


}
