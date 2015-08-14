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
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.Ponente;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class HorarioAdapter extends BaseAdapter {

    private Activity activityRef;
    private ArrayList<Horario> items;

    public HorarioAdapter(Activity activity, ArrayList<Horario> items) {
        this.activityRef = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Horario getItem(int position) {
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
            v = vi.inflate(R.layout.list_programa_item, null);
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Horario hor = getItem(position);

        if (hor != null) {

            holder.mHorarioTV.setText(hor.getHorario());
            holder.mNombreTV.setText(hor.getNombre());
            holder.mSalaTV.setText(hor.getSala());

        }

        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView mHorarioTV;
        TextView mNombreTV;
        TextView mSalaTV;

        public ViewHolder(View v, int tipo) {
            mHorarioTV = (TextView) v.findViewById(R.id.list_programa_horario);
            mNombreTV = (TextView) v.findViewById(R.id.list_programa_nombre);
            mSalaTV = (TextView) v.findViewById(R.id.list_programa_sala);
        }
    }
}
