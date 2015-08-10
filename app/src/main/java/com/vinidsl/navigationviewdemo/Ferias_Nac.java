package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 23/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Adapter.AdapterFeriasNac;
import com.vinidsl.navigationviewdemo.Model.FeriasNacModel;

import java.util.ArrayList;
import java.util.List;

public class Ferias_Nac extends Fragment {
    View rootView;
    public Ferias_Nac() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.ferias_nac, container, false);

        String[][] ferias_nac = {
                {"Tianguis Turistico","Proin gravida nibh vel velit auctor aliquet","http://desarrollo.smartthinking.com.mx:8080/Cptm/ferias_nacionales.jsp"},
                {"Feria del turismo","Aenean sollicitudin, lorem quis bibendum auctor","http://desarrollo.smartthinking.com.mx:8080/Cptm/ferias_nacionales.jsp"},

        };
        List<FeriasNacModel> listaM = new ArrayList<FeriasNacModel>();

        for (int i=0; i< ferias_nac.length; i++) {
            String titulo_tianguis = "", descripcion = "", urlExterno="";
            titulo_tianguis = ferias_nac[i][0];
            descripcion = ferias_nac[i][1];
            urlExterno = ferias_nac[i][2];
            FeriasNacModel objeto = new FeriasNacModel(titulo_tianguis, descripcion,urlExterno);
            listaM.add(objeto);
        }
        ListView lista = (ListView)rootView.findViewById(R.id.listadoTianguis);

        AdapterFeriasNac adapter = new AdapterFeriasNac(getActivity(), R.layout.ferias_nac_item,
                listaM);
        lista.setAdapter(adapter);



        return rootView;
    }

}

