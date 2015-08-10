package com.vinidsl.navigationviewdemo.Adapter;

/**
 * Created by root on 10/08/15.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.FeriasNacModel;
import com.vinidsl.navigationviewdemo.R;

import java.util.List;


public class AdapterFeriasNac extends ArrayAdapter<FeriasNacModel> {

    AQuery aquery;

    public AdapterFeriasNac(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        aquery = new AQuery(context);
    }

    public AdapterFeriasNac(Context context, int resource, List<FeriasNacModel> items) {
        super(context, resource, items);
        aquery = new AQuery(context);
    }

    public static class ViewHolder {
        TextView tituloFerNac;
        TextView descripFerNac;
        ImageView foto;
        ImageButton urlExt;

        public ViewHolder(View v) {
            tituloFerNac = (TextView) v.findViewById(R.id.titulo_tianguis);
            descripFerNac = (TextView) v.findViewById(R.id.descripcion_tianguis);
            foto = (ImageView) v.findViewById(R.id.img_tianguis);
            urlExt = (ImageButton) v.findViewById(R.id.ferias_nac_item_link);
        }
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.ferias_nac_item, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        FeriasNacModel p = getItem(position);

        if (p != null) {
            final String urlLink=p.getUrlExterno();
            holder.tituloFerNac.setText(p.getTitulo());
            holder.descripFerNac.setText(p.getDescripcion());
            final TextView button1=holder.tituloFerNac;
            holder.urlExt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(urlLink); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    aquery.getContext().startActivity(intent);

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
            aquery.id(holder.foto).image("http://desarrollo.smartthinking.com.mx:8080/Cptm/images/logoTianguis.jpg");

        }

        return v;
    }

}