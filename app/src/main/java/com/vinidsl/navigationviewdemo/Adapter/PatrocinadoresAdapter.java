package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.Noticia;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class PatrocinadoresAdapter extends BaseAdapter {

    private Activity activityRef;
    private ArrayList<Patrocinador> items;
    private AQuery aquery;

    public PatrocinadoresAdapter(Activity activity, ArrayList<Patrocinador> items) {
        this.activityRef = activity;
        this.items = items;
        aquery = new AQuery(activity);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Patrocinador getItem(int position) {
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

        //Asociamos la vista de la lista que hemos creado, almacenamos dicha vista en cache
        // y si ya fue creada esa vista sólo se extrae de la cache
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activityRef);
            v = vi.inflate(R.layout.list_patrocinador_item, null);
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Patrocinador pat = getItem(position);

        if (pat != null) {

            holder.mNombreTV.setText(pat.getNombre());
            holder.mNumeroTV.setText(pat.getNumero());

            switch(pat.getEstado()) {
                case Patrocinador.ESTADO_SIN:
                    break;
                case Patrocinador.ESTADO_ACTIVO:
                    holder.mAgrBoton.setImageResource(R.drawable.action_eventok_st);
                    holder.mAgrBoton.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
                case Patrocinador.ESTADO_INACTIVO:
                    holder.mAgrBoton.setImageResource(R.drawable.action_eventadd_clear);
                    holder.mAgrBoton.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
                case Patrocinador.ESTADO_CANCELADO:
                    holder.mAgrBoton.setImageResource(R.drawable.action_eventadd_st);
                    holder.mAgrBoton.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
            }

            String pathFoto = pat.getPathFoto();
            if(!pathFoto.isEmpty()) {
                aquery.id(holder.mFotoIV).image(pathFoto);
            }

        }

        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView mNombreTV;
        TextView mNumeroTV;
        ImageView mFotoIV;
        ImageButton mAgrBoton;
        ImageButton mVerBoton;

        public ViewHolder(View v, int tipo) {
            mNombreTV = (TextView) v.findViewById(R.id.list_patrocinadores_nombre);
            mNumeroTV = (TextView) v.findViewById(R.id.list_patrocinadores_numero);
            mFotoIV = (ImageView) v.findViewById(R.id.list_patrocinadores_foto);
            mAgrBoton = (ImageButton) v.findViewById(R.id.list_patrocinador_boton_agregar);
            mVerBoton = (ImageButton) v.findViewById(R.id.list_patrocinadores_boton_ver);
        }
    }

}

