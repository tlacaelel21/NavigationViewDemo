package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vinidsl.navigationviewdemo.Adapter.HorarioAdapter;
import com.vinidsl.navigationviewdemo.Model.Horario;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class ProgramaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                 ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pagina_programa, container, false);

        ListView lista = (ListView) v.findViewById(R.id.programa_lista);
        ArrayList<Horario> horarios = getArguments().getParcelableArrayList("valores");
        HorarioAdapter adapter = new HorarioAdapter(getActivity(), horarios);
        lista.setAdapter(adapter);

        return v;
    }

    public static ProgramaFragment newInstance(ArrayList<Horario> lista) {

        ProgramaFragment f = new ProgramaFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("valores", lista);
        f.setArguments(b);

        return f;
    }
}
