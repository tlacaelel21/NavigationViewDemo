package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 26/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Registrado extends Fragment {
    Button aButton;
    View rootView;
    public Registrado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************7777777");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.registrado, container, false);

       // aButton = (Button)rootView.findViewById(R.id.button_registrado);

        ((Button) rootView.findViewById(R.id.button_registrado)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                PerfilFragment perfil= new PerfilFragment();
                //Eventos perfil= new Eventos();
                Fragment newFragment = perfil;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextInputLayout

    }


    public Fragment visualiza(){
        this.onDestroy();
        return this;
    }
}

