package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;

import java.util.ArrayList;

/**
 * Created by root on 27/08/15.
 */
public class FotosFragment extends Fragment {
    AQuery aquery = new AQuery(getActivity());
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       final View v = inflater.inflate(R.layout.fragment_pagina_foto, container, false);


        CarruselModel horarios = getArguments().getParcelable("valor");

        final long idEv=horarios.getId();
        ImageView fotoIV = (ImageView) v.findViewById(R.id.pagina_foto_imagen);
        TextView pagina_foto_nombre = (TextView) v.findViewById(R.id.pagina_foto_nombre);
        TextView pagina_foto_lugar = (TextView) v.findViewById(R.id.pagina_foto_lugar);

        pagina_foto_nombre.setText(horarios.getPonNombre());
        pagina_foto_lugar.setText(horarios.getPonEmpresa());
        String path_píc=v.getContext().getString(R.string.base_img) +horarios.getmFoto();
        aquery.id(fotoIV).image(path_píc);

        Log.i("ID_EVE", "" + idEv);

        fotoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CarruselModel n = adapter.getItem(position);
                //Activity activity=(Activity) v.getContext();
                Intent activity = new Intent(v.getContext(), Evento.class);
                activity.putExtra("idEvento", ""+idEv);
                v.getContext().startActivity(activity);

            }
        });
        return v;
    }

    public static FotosFragment newInstance(CarruselModel objeto) {

        FotosFragment f = new FotosFragment();
        Bundle b = new Bundle();
        b.putParcelable("valor", objeto);
        f.setArguments(b);

        return f;
    }
}

