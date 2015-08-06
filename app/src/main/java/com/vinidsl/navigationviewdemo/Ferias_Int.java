package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 23/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Ferias_Int extends Fragment {
    View rootView;
    public Ferias_Int() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.ferias_int, container, false);

        String[][] ferias = {
                {"feria 1","mexico"},
                {"feria 2","guadalajara"},
                {"feria 3","df"},
                {"feria 4","sonora"},
                {"feria 5","mexico"}
        };

        List<FeriaIntModel> listaM = new ArrayList<FeriaIntModel>();

        for (int i=0; i< ferias.length; i++) {
            String nombre = "", ubicacion = "";
            nombre = ferias[i][0];
            ubicacion = ferias[i][1];
            FeriaIntModel objeto = new FeriaIntModel(nombre, ubicacion);
            listaM.add(objeto);
        }

        ListView lista = (ListView)rootView.findViewById(R.id.listadoFerias);

        FeriasIntAdapter adapter = new FeriasIntAdapter(getActivity(), R.layout.ferias_item,
                listaM);
        lista.setAdapter(adapter);
        return rootView;
    }

    public void onCreate(){

    }
}


