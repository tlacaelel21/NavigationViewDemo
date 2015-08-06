package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Perfil extends Fragment {

    public Perfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_perfil, container, false);

        // aButton = (Button)rootView.findViewById(R.id.button_registrado);

        ((Button) rootView.findViewById(R.id.perfil_boton_actualizar)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Evento evento = new Evento();
                Fragment newFragment = evento;
                Fragment actual= visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(),newFragment);
                //container is the ViewGroup of current fragment
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return rootView;
    }

    public Fragment visualiza(){
        this.onDestroy();
        return this;
    }
}
