package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.CalendarioModel;
import com.vinidsl.navigationviewdemo.R;
import com.vinidsl.navigationviewdemo.WebActivity;

import java.util.ArrayList;

/**
 * Created by tlacaelel21 on 6/10/15.
 */
public class AdapterCalendario extends BaseAdapter {

    // constante para saber si el item es cabecera
    private static final int TIPO_ITEM_CABECERA = 0;
    // constante para saber si el item es un elemento con contenido
    private static final int TIPO_ITEM_CONTENIDO = 1;

    private Activity activityRef;
    private ArrayList<CalendarioModel> items;
    private AQuery aquery;

    public AdapterCalendario(Activity activity, ArrayList<CalendarioModel> items) {
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
    public CalendarioModel getItem(int position) {
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
                v = vi.inflate(R.layout.calendario_item, null);
            }
            holder = new ViewHolder(v, getItemViewType(position));
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        CalendarioModel calendarioModel = getItem(position);

        if (calendarioModel != null) {

            if(tipoItem == TIPO_ITEM_CONTENIDO) {

                final String urlLink=calendarioModel.getUrlExterno();
                holder.nac_titulo.setText(calendarioModel.getTitulo());
                holder.nac_url.setText(calendarioModel.getUrlExterno());

                /*holder.boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/
                    if(null!=urlLink) {
                        //if(urlLink.indexOf("http")>0){
                            Uri uri = Uri.parse(urlLink); // missing 'http://' will cause crashed
                            Intent intent = new Intent(v.getContext(), WebActivity.class);
                            intent.putExtra("urlOpen", ""+uri );
                            intent.putExtra("urlTipo", "1" );
                            activityRef.startActivity(intent);
                            /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + urlLink));
                            activityRef.startActivity(browserIntent);*/
                        //}
                    }
                    /*}
                });*/
            }
        }
        // Retornamos la vista
        return v;
    }

    // Clase que sirve para almacenar en cache una instancia del item
    public static class ViewHolder {

        TextView nac_titulo;
        TextView nac_url;
        WebView myWebView;
        View separadorV;

        public ViewHolder(View v, int tipo) {
            if(tipo == TIPO_ITEM_CABECERA) {
                nac_titulo = (TextView) v.findViewById(R.id.nombre);
                separadorV = v.findViewById(R.id.list_separador);
            } else if(tipo == TIPO_ITEM_CONTENIDO) {
                nac_titulo = (TextView) v.findViewById(R.id.titulo_tianguis);
                nac_url = (TextView) v.findViewById(R.id.titulo_tianguis);
                myWebView= (WebView) v.findViewById(R.id.webview);
                separadorV = null;
            }
        }
    }

}


