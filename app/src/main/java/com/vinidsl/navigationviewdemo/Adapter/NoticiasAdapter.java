package com.vinidsl.navigationviewdemo.Adapter;

/**
 * Created by root on 24/07/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.Noticia;
import com.vinidsl.navigationviewdemo.NoticiaActivity;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;

public class NoticiasAdapter extends BaseAdapter {

    // constante para saber si el item es cabecera
    private static final int TIPO_ITEM_CABECERA = 0;
    // constante para saber si el item es un elemento con contenido
    private static final int TIPO_ITEM_CONTENIDO = 1;

    private Activity activityRef;
    private ArrayList<Noticia> items;
    private AQuery aquery;

    public NoticiasAdapter(Activity activity, ArrayList<Noticia> items) {
        this.activityRef = activity;
        this.items = items;
        aquery = new AQuery(activity);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    // Método para definir la cantidad de items diferentes
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // Método para diferenciar si los diferentes tipos de elementos
    @Override
    public int getItemViewType(int position) {
        return (items.get(position).getId() == -1)?
                TIPO_ITEM_CABECERA : TIPO_ITEM_CONTENIDO;
    }

    @Override
    public Noticia getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Generamos una convertView por motivos de eficiencia
        // y un ViewHolder para generar cache de la vista
        View v = convertView;
        ViewHolder holder;

        int tipoItem = getItemViewType(position);
        //Asociamos la vista de la lista que hemos creado, almacenamos dicha vista en cache
        // y si ya fue creada esa vista sólo se extrae de la cache
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activityRef);
            if(tipoItem == TIPO_ITEM_CABECERA) {
                v = vi.inflate(R.layout.list_separador_item, null);
            } else if(tipoItem == TIPO_ITEM_CONTENIDO) {
                v = vi.inflate(R.layout.list_noticia_item, null);
            }
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Noticia not = getItem(position);

        if (not != null) {

            if(tipoItem == TIPO_ITEM_CONTENIDO) {
                holder.tituloTV.setText(not.getTitulo());
                holder.fechaTV.setText(not.getFecha());

                holder.boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*AppCompatActivity activity = (AppCompatActivity) activityRef;
                        FragmentManager manager = activity.getSupportFragmentManager();
                        FragmentTransaction ft = manager.beginTransaction();
                        com.vinidsl.navigationviewdemo.Noticia fragment =
                                new com.vinidsl.navigationviewdemo.Noticia();
                        ft.addToBackStack("noticias");
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();*/

                        Intent noticia = new Intent(activityRef, NoticiaActivity.class);
                        activityRef.startActivity(noticia);

                    }
                });

                String pathFoto = not.getPathFoto();
                if(!pathFoto.isEmpty()) {
                    aquery.id(holder.fotoIV).image(pathFoto);
                }
            } else if(tipoItem == TIPO_ITEM_CABECERA) {
                holder.tituloTV.setText(not.getTitulo());
                holder.fechaTV.setText(not.getFecha());
                holder.separadorV.setBackgroundColor(
                        activityRef.getResources().getColor(R.color.cab_noticia));
            }
        }

        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView tituloTV;
        TextView fechaTV;
        ImageView fotoIV;
        ImageButton boton;
        View separadorV;

        public ViewHolder(View v, int tipo) {
            if(tipo == TIPO_ITEM_CABECERA) {
                tituloTV = (TextView) v.findViewById(R.id.list_titulo);
                fechaTV = (TextView) v.findViewById(R.id.list_subtitulo);
                separadorV = v.findViewById(R.id.list_separador);
                fotoIV = null;
                boton = null;
            } else if(tipo == TIPO_ITEM_CONTENIDO) {
                tituloTV = (TextView) v.findViewById(R.id.list_noticias_titulo);
                fechaTV = (TextView) v.findViewById(R.id.list_noticias_subtitulo);
                fotoIV = (ImageView) v.findViewById(R.id.list_noticias_foto);
                boton = (ImageButton) v.findViewById(R.id.list_noticias_boton);
                separadorV = null;
            }
        }
    }

}

