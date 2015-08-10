package com.vinidsl.navigationviewdemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView nombreTV;
        TextView ubicacionTV;
        ImageView foto;

        public ViewHolder(View v) {
            nombreTV = (TextView) v.findViewById(R.id.nombre);
            ubicacionTV = (TextView) v.findViewById(R.id.ubicacion);
            foto = (ImageView) v.findViewById(R.id.foto);
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


        FeriaIntModel p = getItem(position);

        if (p != null) {

            holder.nombreTV.setText(p.getNombre());
            holder.ubicacionTV.setText(p.getUbicacion());
            final TextView button1=holder.nombreTV;
            holder.nombreTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Log.i("msg","hola");
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(aquery.getContext(), button1);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(aquery.getContext(), " + item.getTitle()", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    popup.show();//showing popup menu*/
                }
            });
            aquery.id(holder.foto).image("http://www.idg.es/BBDD_IMAGEN/logo-android.gif");

        }

        return v;
    }

}