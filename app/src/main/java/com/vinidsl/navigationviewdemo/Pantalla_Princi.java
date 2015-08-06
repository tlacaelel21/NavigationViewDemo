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
import android.widget.TextView;

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
        Principal prin= new Principal();
        //prin.lista();
        //TextView username=(TextView)rootView.findViewById(R.id.textView2);
        //username.setText(Html.fromHtml(getString(R.string.term_cond)));
        return rootView;



    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextInputLayout
    }

}
