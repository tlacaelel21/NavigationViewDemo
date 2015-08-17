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

import com.vinidsl.navigationviewdemo.Adapter.MisEventosAdapter;
import com.vinidsl.navigationviewdemo.Model.MisEventosModel;

import java.util.ArrayList;
import java.util.List;

public class Eventos extends Fragment {
    View rootView;
    public Eventos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.mis_eventos, container, false);

        String[][] mis_eventos = {
                {"Nombre Evento I","Ubicación del evento, Paris","Solicitado"},
                {"Nombre Evento II","Ubicación del evento, China","Aceptado"},
                {"Nombre Evento III","Ubicación del evento, España","Pagado"}
        };
        List<MisEventosModel> listaM = new ArrayList<MisEventosModel>();

        for (int i=0; i< mis_eventos.length; i++) {
            String nombre_evento = "", ubicacion_evento = "", status_evento="";
            nombre_evento = mis_eventos[i][0];
            ubicacion_evento = mis_eventos[i][1];
            status_evento = mis_eventos[i][2];
            MisEventosModel objeto = new MisEventosModel(nombre_evento, ubicacion_evento,status_evento);
            listaM.add(objeto);
        }
        ListView lista = (ListView)rootView.findViewById(R.id.listado_mis_eventos);

        MisEventosAdapter adapter = new MisEventosAdapter(getActivity(), R.layout.mis_eventos_item,
                listaM);
        lista.setAdapter(adapter);



        return rootView;
    }

}
