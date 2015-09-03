package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.regex.Pattern;

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
            String titulo = extras.getString("titulo");
            String contenido = extras.getString("desc");
            String fecha = extras.getString("fecha");

            fecha = obtNombreMes(fecha) + obtDia(fecha) + ", " + obtAnio(fecha);

            aQuery.id(R.id.noticia_foto).image(getString(R.string.base_img_carrusel) + "/" +
                    foto.replace("\\", ""));

            TextView tituloTV = (TextView) findViewById(R.id.noticia_titulo);
            TextView contenidoTV = (TextView) findViewById(R.id.noticia_contenido);
            TextView fechaTV = (TextView) findViewById(R.id.noticia_fecha);

            tituloTV.setText(titulo);
            contenidoTV.setText(contenido);
            fechaTV.setText(fecha);

        }

    }

    private String obtNombreMes(String fechaStr) {
        int mes = 1;
        // fechaYHORA = [{2015-09-01}, {14:16:01.0}]
        String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaYHora[0].split(Pattern.quote("-"));
        mes = Integer.parseInt(fecha[1]);
        switch(mes) {
            case 1:
                return "Enero ";
            case 2:
                return "Febrero ";
            case 3:
                return "Marzo ";
            case 4:
                return "Abril ";
            case 5:
                return "Mayo ";
            case 6:
                return "Junio ";
            case 7:
                return "Julio ";
            case 8:
                return "Agosto ";
            case 9:
                return "Septiembre ";
            case 10:
                return "Octubre ";
            case 11:
                return "Noviembre ";
            case 12:
                return "Diciembre ";
            default:
                return "Enero ";
        }
    }

    private int obtDia(String fechaStr) {
        int dia = 1;
        // fechaYHORA = [{2015-09-01}, {14:16:01.0}]
        String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaYHora[0].split(Pattern.quote("-"));
        dia = Integer.parseInt(fecha[2]);
        return dia;
    }

    private int obtAnio(String fechaStr) {
        int anio = 1;
        // fechaYHORA = [{2015-09-01}, {14:16:01.0}]
        String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaYHora[0].split(Pattern.quote("-"));
        anio = Integer.parseInt(fecha[0]);
        return anio;
    }


}
