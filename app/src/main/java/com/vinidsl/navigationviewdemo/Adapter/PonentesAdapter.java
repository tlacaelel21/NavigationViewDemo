package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.Model.Ponente;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class PonentesAdapter extends BaseAdapter {

    private Activity activityRef;
    private ArrayList<Ponente> items;
    private AQuery aquery;

    public PonentesAdapter(Activity activity, ArrayList<Ponente> items) {
        this.activityRef = activity;
        this.items = items;
        aquery = new AQuery(activity);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Ponente getItem(int position) {
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
            v = vi.inflate(R.layout.list_ponente_item, null);
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Ponente pon = getItem(position);

        if (pon != null) {

            holder.mNombreTV.setText(pon.getNombre());
            holder.mPuestoTV.setText(pon.getPuesto());

            switch(pon.getEstado()) {
                case Ponente.ESTADO_SIN:
                    break;
                case Ponente.ESTADO_ACTIVO:
                    holder.mAgrBoton.setImageResource(R.drawable.action_eventok_st);
                    holder.mAgrBoton.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
                case Ponente.ESTADO_INACTIVO:
                    holder.mAgrBoton.setImageResource(R.drawable.action_eventadd_clear);
                    holder.mAgrBoton.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
                case Ponente.ESTADO_CANCELADO:
                    holder.mAgrBoton.setImageResource(R.drawable.action_eventadd_st);
                    holder.mAgrBoton.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
            }

            String pathFoto = pon.getPathFoto();
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
        TextView mPuestoTV;
        ImageView mFotoIV;
        ImageButton mAgrBoton;

        public ViewHolder(View v, int tipo) {
            mNombreTV = (TextView) v.findViewById(R.id.list_ponentes_nombre);
            mPuestoTV = (TextView) v.findViewById(R.id.list_ponentes_puesto);
            mFotoIV = (ImageView) v.findViewById(R.id.list_ponentes_foto);
            mAgrBoton = (ImageButton) v.findViewById(R.id.list_ponentes_boton_agregar);
        }
    }

}

