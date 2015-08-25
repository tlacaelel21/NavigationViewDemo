package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 24/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Adapter.FeriasIntAdapter;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;
import com.vinidsl.navigationviewdemo.Tasks.FeriaInterTask;

import java.util.ArrayList;
import java.util.List;

public class Pantalla_Princi extends Fragment {
    EditText edittext;
    public Pantalla_Princi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.vista_principal, container, false);
        /*String[][] ferias = {
                {"feria 1","Paris"},
                {"feria 2","Tokio"},
                {"feria 3","Austria"},
                {"feria 4","España"},
                {"feria 5","Argentina"},
        };

        List<FeriaIntModel> listaM = new ArrayList<FeriaIntModel>();

        for (int i=0; i< ferias.length; i++) {
            String nombre = "", ubicacion = "";
            nombre = ferias[i][0];
            ubicacion = ferias[i][1];
            long id=0;
            String pais_desc="", int_lugar="", int_titulo="", foto="", int_final="", int_inicio="";
            FeriaIntModel objeto = new FeriaIntModel(id, nombre,ubicacion,pais_desc,int_titulo,foto,int_final);
            listaM.add(objeto);
        }

        ListView lista = (ListView)rootView.findViewById(R.id.lista_eventos);

        FeriasIntAdapter adapter = new FeriasIntAdapter(getActivity(), R.layout.ferias_item,
                listaM);
        lista.setAdapter(adapter);

*/
        FeriaInterTask feriasTask= new FeriaInterTask(getActivity());
        feriasTask.execute("");
        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextInputLayout
    }

}
