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
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.EncuestaActivity;
import com.vinidsl.navigationviewdemo.MainActivity;
import com.vinidsl.navigationviewdemo.Model.MisEventosModel;
import com.vinidsl.navigationviewdemo.NoticiaActivity;
import com.vinidsl.navigationviewdemo.NoticiasActivity;
import com.vinidsl.navigationviewdemo.NoticiasFragment;
import com.vinidsl.navigationviewdemo.PatrocinadoresActivity;
import com.vinidsl.navigationviewdemo.PonentesActivity;
import com.vinidsl.navigationviewdemo.ProgramaActivity;
import com.vinidsl.navigationviewdemo.R;

import java.util.List;

/**
 * Created by root on 14/08/15.
 */
public class MisEventosAdapter extends ArrayAdapter<MisEventosModel> {

    AQuery aquery;
    Activity act;
    Intent prog;
    AppCompatActivity appComp;

    public MisEventosAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        aquery = new AQuery(context);
        act=(Activity)context;
        appComp=(AppCompatActivity)context;

    }

    public MisEventosAdapter(Context context, int resource, List<MisEventosModel> items) {
        super(context, resource, items);
        aquery = new AQuery(context);
        act=(Activity)context;
        appComp=(AppCompatActivity)context;
    }



    public static class ViewHolder {
        TextView nombre_evento;
        TextView ubicacion_evento;
        TextView status_evento;
        ImageButton menu_mi_evento;

        public ViewHolder(View v) {
            nombre_evento = (TextView) v.findViewById(R.id.nombre_evento);
            ubicacion_evento = (TextView) v.findViewById(R.id.ubicacion_evento);
            status_evento = (TextView) v.findViewById(R.id.status_evento);
            menu_mi_evento = (ImageButton) v.findViewById(R.id.menu_mi_evento);
        }
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.mis_eventos_item, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        MisEventosModel p = getItem(position);

        //act
        SharedPreferences preferencias =
                act.getSharedPreferences(act.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idUsuario = preferencias.getString(act.getString(R.string.pref_idusuario), "0");
        //Log.i("USR", "" + idUsuario);

        if (p != null) {
            //final String urlLink=p.getUrlExterno();
            holder.nombre_evento.setText(p.getNombre_evento());
            holder.ubicacion_evento.setText(p.getUbicacion_evento());
            holder.status_evento.setText(p.getStatus_evento());
            final ImageButton button1=holder.menu_mi_evento;
            holder.menu_mi_evento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Uri uri = Uri.parse(urlLink); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    aquery.getContext().startActivity(intent);*/

                    //Log.i("msg","hola");
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(aquery.getContext(), button1);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu_ok, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            String tituloItem= (String) item.getTitle();
                            if (item.getItemId() == R.id.item_subir_doc){
                                Toast.makeText(aquery.getContext(), "*** Demo Subir Documento *** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_patrocinador){
                                Intent prog = new Intent(act, PatrocinadoresActivity.class);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_programa){
                                Intent prog = new Intent(act, ProgramaActivity.class);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_ponentes){
                                Intent prog = new Intent(act, PonentesActivity.class);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_noticias){
                                //Intent prog = new Intent(act, NoticiaActivity.class);
                                //act.startActivity(prog);
                                Intent prog = new Intent(act, NoticiasActivity.class);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "** "+tituloItem, Toast.LENGTH_SHORT).show();

                            }
                            if (item.getItemId() == R.id.item_encuesta){
                                Intent prog = new Intent(act, EncuestaActivity.class);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_escanear_qr){
                                Toast.makeText(aquery.getContext(), "*** Demo Escaner QR *** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
            });
            //aquery.id(holder.foto).image("http://desarrollo.smartthinking.com.mx:8080/Cptm/images/logoTianguis.jpg");

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
