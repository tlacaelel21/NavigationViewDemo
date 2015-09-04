package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.vinidsl.navigationviewdemo.Ferias_Int;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class FeriasIntAdapter extends BaseAdapter {

    // constante para saber si el item es cabecera
    private static final int TIPO_ITEM_CABECERA = 0;
    // constante para saber si el item es un elemento con contenido
    private static final int TIPO_ITEM_CONTENIDO = 1;

    private Activity activityRef;
    private ArrayList<FeriaIntModel> items;
    private AQuery aquery;

    public FeriasIntAdapter(Activity activity, ArrayList<FeriaIntModel> items) {
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
    public FeriaIntModel getItem(int position) {
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
                v = vi.inflate(R.layout.ferias_item, null);
            }
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        FeriaIntModel feriaIntModel = getItem(position);

        if (feriaIntModel != null) {

            if(tipoItem == TIPO_ITEM_CONTENIDO) {
                holder.nombreFI.setText(feriaIntModel.getNombre());
                holder.ubicacionFI.setText(feriaIntModel.getInt_lugar() + ", " + feriaIntModel.getPais_desc());
                holder.fecha_inicio_fi.setText(feriaIntModel.getFechaInicio());
                holder.fecha_fin_fi.setText(feriaIntModel.getFechaFin());
                String pathFoto = feriaIntModel.getFotoInt();
                //if(!pathFoto.isEmpty()) {
                    //aquery.id(holder.fotoIV).image(pathFoto);
                    //Log.i("FOTO",pathFoto);
                    aquery.id(holder.fotoIV).image(activityRef.getApplicationContext().getString(R.string.base_img)+feriaIntModel.getFotoInt());
                //}
            } else if(tipoItem == TIPO_ITEM_CABECERA) {
                holder.nombreFI.setText(feriaIntModel.getNombre());
                holder.fecha_inicio_fi.setText(feriaIntModel.getFechaInicio());
                holder.separadorV.setBackgroundColor(
                        activityRef.getResources().getColor(R.color.cab_ferias_int));
            }
        }
        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView nombreFI;
        TextView ubicacionFI;
        TextView fecha_inicio_fi;
        TextView fecha_fin_fi;
        ImageView fotoIV;
        View separadorV;

         public ViewHolder(View v, int tipo) {
            if(tipo == TIPO_ITEM_CABECERA) {
                nombreFI = (TextView) v.findViewById(R.id.nombre);
                fecha_inicio_fi = (TextView) v.findViewById(R.id.ubicacion);
                separadorV = v.findViewById(R.id.list_separador);
                fotoIV = null;
            } else if(tipo == TIPO_ITEM_CONTENIDO) {
                nombreFI = (TextView) v.findViewById(R.id.nombre);
                ubicacionFI = (TextView) v.findViewById(R.id.ubicacion);
                fecha_inicio_fi = (TextView) v.findViewById(R.id.fecha_inicio_fi);
                fecha_fin_fi = (TextView) v.findViewById(R.id.fecha_fin_fi);
                fotoIV = (ImageView) v.findViewById(R.id.foto_unica);
                //boton = (ImageButton) v.findViewById(R.id.list_noticias_boton);
                separadorV = null;
            }
        }
    }

}

