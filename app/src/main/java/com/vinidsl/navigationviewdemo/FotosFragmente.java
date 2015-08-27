package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vinidsl.navigationviewdemo.Adapter.CarruselAdapter;
import com.vinidsl.navigationviewdemo.Adapter.HorarioAdapter;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;
import com.vinidsl.navigationviewdemo.Model.Horario;

import java.util.ArrayList;

/**
 * Created by root on 27/08/15.
 */
public class FotosFragmente extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pagina_foto, container, false);

        ListView lista = (ListView) v.findViewById(R.id.fotos_lista);
        ArrayList<CarruselModel> horarios = getArguments().getParcelableArrayList("valores");
        //CarruselAdapter adapter = new CarruselAdapter(getActivity(), horarios);
        //lista.setAdapter(adapter);
        return v;
    }



    public static FotosFragmente newInstance(ArrayList<CarruselModel> lista) {

        FotosFragmente f = new FotosFragmente();
        Bundle b = new Bundle();
        b.putParcelableArrayList("valores", lista);
        f.setArguments(b);

        return f;
    }
}

