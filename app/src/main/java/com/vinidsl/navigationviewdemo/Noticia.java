package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 31/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Noticia extends Fragment {
    Button aButton;
    View rootView;
    public Noticia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************7777777");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.noticia, container, false);

        // aButton = (Button)rootView.findViewById(R.id.button_registrado);



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

