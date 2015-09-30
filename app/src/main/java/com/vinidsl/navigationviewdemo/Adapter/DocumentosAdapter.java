package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.EncuestaActivity;
import com.vinidsl.navigationviewdemo.Model.DocumentosModel;
import com.vinidsl.navigationviewdemo.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 28/09/15.
 */
public class DocumentosAdapter extends ArrayAdapter<DocumentosModel> {

    AQuery aquery;
    Activity act;
    Intent prog;
    AppCompatActivity appComp;

    /*public DocumentosAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        aquery = new AQuery(context);
        act=(Activity)context;
        appComp=(AppCompatActivity)context;

    }*/

    public DocumentosAdapter(Context context, int resource, List<DocumentosModel> items) {
        super(context, resource, items);
        aquery = new AQuery(context);
        act=(Activity)context;
        appComp=(AppCompatActivity)context;
    }

    public static class ViewHolder {
        TextView nombre_documento;
        ImageButton doc_visto;

        public ViewHolder(View v) {
            nombre_documento = (TextView) v.findViewById(R.id.nombre_documento);
            doc_visto = (ImageButton) v.findViewById(R.id.imagen_doc);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.documentos_item, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        DocumentosModel p = getItem(position);
        //act
        SharedPreferences preferencias =
                act.getSharedPreferences(act.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idUsuario = preferencias.getString(act.getString(R.string.pref_idusuario), "0");
        //Log.i("USR", "" + idUsuario);
        if (p != null) {
            //final String urlLink=p.getUrlExterno();
            holder.nombre_documento.setText(p.getnombre_documento());
            final String urlLink=p.geturl_documento();
            final ImageButton button1=holder.doc_visto;


            holder.doc_visto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urlPrinici=act.getApplicationContext().getString(R.string.base_img);
                    Uri uri = Uri.parse(urlPrinici+urlLink); // missing 'http://' will cause crashed
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""+uri));
                    act.startActivity(browserIntent);
                    //Log.i("msg", "hola");
                }
            });
        }

        return v;
    }
    public void MuestraFragment(Fragment fragment){
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = appComp.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
    }
}
