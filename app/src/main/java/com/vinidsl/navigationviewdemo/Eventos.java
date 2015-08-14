package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 23/07/15.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

public class Eventos extends Fragment {
    View rootView;
    public Eventos() {
        // Required empty public constructor
    }

    public void onCreate(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //this.onCreateView()
        //MainActivity actividad=new MainActivity();

        //startActivity(actividad.getIntent());
        // Inflate the layout for this fragment
        SharedPreferences varGlobal= getActivity().getSharedPreferences("cptm", Context.MODE_PRIVATE);
        String usuario=varGlobal.getString("usr", "0");
        //if(!usuario.equals("0")){
           rootView = inflater.inflate(R.layout.mis_eventos, container, false);
        //}
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        //(rootView.getContext())..inflate(R.menu.menu_ctx_etiqueta, menu);
    }
}
