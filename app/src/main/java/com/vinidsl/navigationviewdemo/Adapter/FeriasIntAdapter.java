package com.vinidsl.navigationviewdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;
import com.vinidsl.navigationviewdemo.R;

import java.util.List;

public class FeriasIntAdapter extends ArrayAdapter<FeriaIntModel> {

    AQuery aquery;

    public FeriasIntAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        aquery = new AQuery(context);
    }

    public FeriasIntAdapter(Context context, int resource, List<FeriaIntModel> items) {
        super(context, resource, items);
        aquery = new AQuery(context);
    }

    public static class ViewHolder {
        TextView nombreFI;
        TextView ubicacionFI;
        ImageView foto;
        TextView fecha_inicio_fi;
        TextView fecha_fin_fi;

        public ViewHolder(View v) {
            nombreFI = (TextView) v.findViewById(R.id.nombre);
            ubicacionFI = (TextView) v.findViewById(R.id.ubicacion);
            foto = (ImageView) v.findViewById(R.id.foto);
            fecha_inicio_fi = (TextView) v.findViewById(R.id.fecha_inicio_fi);
            fecha_fin_fi = (TextView) v.findViewById(R.id.fecha_fin_fi);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.ferias_item, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        FeriaIntModel feriaIntModel = getItem(position);
        if (feriaIntModel != null) {
            holder.nombreFI.setText(feriaIntModel.getNombre());
            holder.ubicacionFI.setText(feriaIntModel.getInt_lugar() + ", " + feriaIntModel.getPais_desc());
            holder.fecha_inicio_fi.setText(feriaIntModel.getFechaInicio());
            holder.fecha_fin_fi.setText(feriaIntModel.getFechaFin());
            aquery.id(holder.foto).image("http://desarrollo.smartthinking.com.mx:8080/Cptm/" +feriaIntModel.getFotoInt());
        }
        return v;
    }

}