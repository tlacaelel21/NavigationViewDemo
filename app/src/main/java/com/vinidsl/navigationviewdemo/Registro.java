package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 23/07/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Registro extends Fragment {
    Button aButton;
    View rootView;
    public Registro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************7777777");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.registro, container, false);
        TextView username=(TextView)rootView.findViewById(R.id.textView2);
        username.setText(Html.fromHtml(getString(R.string.term_cond)));

        //aButton = (Button)rootView.findViewById(R.id.button);

        ((Button) rootView.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Registrado registrado= new Registrado();
                Fragment newFragment = registrado;
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
