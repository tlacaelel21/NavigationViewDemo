package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 27/07/15.
 */
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
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.perfil, container, false);
        View rootView = inflater.inflate(R.layout.perfil, container, false);

        // aButton = (Button)rootView.findViewById(R.id.button_registrado);

        ((Button) rootView.findViewById(R.id.boton_actualiza_perfil)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Evento evento= new Evento();
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
