package com.vinidsl.navigationviewdemo.Adapter;

/**
 * Created by root on 10/08/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.FeriasNacModel;
import com.vinidsl.navigationviewdemo.Model.FeriasNacModel;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterFeriasNac extends BaseAdapter {

    // constante para saber si el item es cabecera
    private static final int TIPO_ITEM_CABECERA = 0;
    // constante para saber si el item es un elemento con contenido
    private static final int TIPO_ITEM_CONTENIDO = 1;

    private Activity activityRef;
    private ArrayList<FeriasNacModel> items;
    private AQuery aquery;

    public AdapterFeriasNac(Activity activity, ArrayList<FeriasNacModel> items) {
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
    public FeriasNacModel getItem(int position) {
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
                v = vi.inflate(R.layout.list_separador_fi_item, null);
            } else if(tipoItem == TIPO_ITEM_CONTENIDO) {
                v = vi.inflate(R.layout.ferias_nac_item, null);
            }
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        FeriasNacModel feriaNacModel = getItem(position);

        if (feriaNacModel != null) {

            if(tipoItem == TIPO_ITEM_CONTENIDO) {
                holder.nac_titulo.setText(feriaNacModel.getTitulo());
                holder.nac_url.setText(feriaNacModel.getUrlExterno());
                final String urlLink=feriaNacModel.getUrlExterno();
                String pathFoto = feriaNacModel.getNac_foto();
                if(!pathFoto.isEmpty()) {
                    aquery.id(holder.nac_foto).image( activityRef.getApplicationContext().getString(R.string.base_img) + feriaNacModel.getNac_foto());
                }
                holder.boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(urlLink); // missing 'http://' will cause crashed
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + urlLink));
                            activityRef.startActivity(browserIntent);
                    }
                });
            } else if(tipoItem == TIPO_ITEM_CABECERA) {
                holder.nac_titulo.setText(feriaNacModel.getTitulo());
                holder.separadorV.setBackgroundColor(
                activityRef.getResources().getColor(R.color.cab_ferias_int));
            }
        }
        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView nac_titulo;
        TextView nac_url;
        ImageView nac_foto;
        ImageButton boton;
        View separadorV;

        public ViewHolder(View v, int tipo) {
            if(tipo == TIPO_ITEM_CABECERA) {
                nac_titulo = (TextView) v.findViewById(R.id.nombre);
                separadorV = v.findViewById(R.id.list_separador);
                nac_foto = null;
            } else if(tipo == TIPO_ITEM_CONTENIDO) {
                nac_titulo = (TextView) v.findViewById(R.id.titulo_tianguis);
                nac_url = (TextView) v.findViewById(R.id.titulo_tianguis);
                nac_foto = (ImageView) v.findViewById(R.id.img_tianguis);
                boton = (ImageButton) v.findViewById(R.id.ferias_nac_item_link);
                separadorV = null;
            }
        }
    }

}

