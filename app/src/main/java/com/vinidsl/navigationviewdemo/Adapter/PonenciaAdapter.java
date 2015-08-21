package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.Ponencia;
import com.vinidsl.navigationviewdemo.R;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class PonenciaAdapter extends BaseAdapter {

    private Activity activityRef;
    private ArrayList<Ponencia> items;

    public PonenciaAdapter(Activity activity, ArrayList<Ponencia> items) {
        this.activityRef = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Ponencia getItem(int position) {
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
            v = vi.inflate(R.layout.list_ponencia_item, null);
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Ponencia pon = getItem(position);

        if (pon != null) {

            holder.mNombreTV.setText(pon.getNombre());
            holder.mSalaTV.setText(pon.getLugar());
            holder.mFechaIniTV.setText(pon.getFechaInicio());

        }

        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView mNombreTV;
        TextView mSalaTV;
        TextView mFechaIniTV;
        TextView mFechaFinTV;

        public ViewHolder(View v, int tipo) {
            mNombreTV = (TextView) v.findViewById(R.id.list_ponencia_nombre);
            mSalaTV = (TextView) v.findViewById(R.id.list_ponencia_lugar);
            mFechaIniTV = (TextView) v.findViewById(R.id.list_ponencia_fecha_ini);
            mFechaFinTV = (TextView) v.findViewById(R.id.list_ponencia_fecha_fin);
        }
    }
}
