package com.vinidsl.navigationviewdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.ExpositoresModel;
import com.vinidsl.navigationviewdemo.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 29/09/15.
 */
public class ExpositoresAdapter extends ArrayAdapter<ExpositoresModel> {

    AQuery aquery;
    Activity act;
    Intent prog;
    AppCompatActivity appComp;

    public ExpositoresAdapter(Context context, int resource, List<ExpositoresModel> items) {
        super(context, resource, items);
        aquery = new AQuery(context);
        act=(Activity)context;
        appComp=(AppCompatActivity)context;
    }

    public static class ViewHolder {
        TextView nombre_expositor;
        TextView actividad_expositor;
        ImageView foto_expositor;

        public ViewHolder(View v) {
            nombre_expositor = (TextView) v.findViewById(R.id.nombre_expositor);
            actividad_expositor = (TextView) v.findViewById(R.id.actividad_expositor);
            foto_expositor = (ImageView) v.findViewById(R.id.foto_expositor);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.expositores_item, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        ExpositoresModel p = getItem(position);
        /*//act
        SharedPreferences preferencias =
                act.getSharedPreferences(act.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idUsuario = preferencias.getString(act.getString(R.string.pref_idusuario), "0");*/
        //Log.i("USR", "" + idUsuario);
        if (p != null) {
            holder.nombre_expositor.setText(p.getCom_nom());
            holder.actividad_expositor.setText(p.getAct_desc());
            aquery.id(holder.foto_expositor).image(act.getApplicationContext().getString(R.string.base_img)+p.getCom_foto());
            final String urlLink=p.getCom_www();

            holder.foto_expositor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("http://"+urlLink); // missing 'http://' will cause crashed
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
