package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 31/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Noticias extends Fragment {
    Button aButton;
    View rootView;
    public Noticias() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************7777777");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.noticias, container, false);

        // aButton = (Button)rootView.findViewById(R.id.button_registrado);

        ((ImageButton) rootView.findViewById(R.id.ver_noticia)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Noticia noticia = new Noticia();
                Fragment newFragment = noticia;
                Fragment actual = visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(), newFragment);
                //container is the ViewGroup of current fragment
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        ((ImageButton) rootView.findViewById(R.id.ver_noticia2)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Noticia noticia = new Noticia();
                Fragment newFragment = noticia;
                Fragment actual = visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(), newFragment);
                //container is the ViewGroup of current fragment
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        ((ImageButton) rootView.findViewById(R.id.ver_noticia3)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Noticia noticia = new Noticia();
                Fragment newFragment = noticia;
                Fragment actual = visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(), newFragment);
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

