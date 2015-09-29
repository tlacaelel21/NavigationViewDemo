package com.vinidsl.navigationviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Tasks.RegistroUsuarioMailTask;

/**
 * Created by tlacaelel21 on 28/09/15.
 */
public class Registro2 extends Fragment {
    Button aButton;
    View rootView;
    TextView calle_usr;
    TextView num_ext_usr;
    TextView num_int_usr;
    TextView codigo_postal_usr;
    TextView colonia_usr;
    TextView municipio_usr;
    TextView estado_usr;
    TextView pais_usr;
    TextView pagina_usr;
    TextView actividad_usr;
    TextView tel_oficina_usr;
    TextView email_contacto_usr;

    String parametro="";

    public Registro2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************7777777");

        final String myValues = this.getArguments().getString("message");
        final String usr_correo= this.getArguments().getString("usr_correo");
        //Log.i("VALOR", myValues);

        final View rootView = inflater.inflate(R.layout.registro_2, container,false);

        calle_usr=(TextView)rootView.findViewById(R.id.calle);
        num_ext_usr=(TextView)rootView.findViewById(R.id.num_ext);
        num_int_usr=(TextView)rootView.findViewById(R.id.num_int);
        codigo_postal_usr=(TextView)rootView.findViewById(R.id.codigo_postal);
        colonia_usr=(TextView)rootView.findViewById(R.id.colonia);
        municipio_usr=(TextView)rootView.findViewById(R.id.municipio);
        estado_usr=(TextView)rootView.findViewById(R.id.estado);
        pais_usr=(TextView)rootView.findViewById(R.id.pais);
        pagina_usr=(TextView)rootView.findViewById(R.id.pagina);
        actividad_usr=(TextView)rootView.findViewById(R.id.actividad);
        tel_oficina_usr=(TextView)rootView.findViewById(R.id.tel_oficina);
        email_contacto_usr=(TextView)rootView.findViewById(R.id.email_contacto);


        ((Button) rootView.findViewById(R.id.registro_completo)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String calle = calle_usr.getText().toString();
                String num_ext = num_ext_usr.getText().toString();
                String num_int = num_int_usr.getText().toString();
                String codigo_postal = codigo_postal_usr.getText().toString();
                String colonia = colonia_usr.getText().toString();
                String municipio = municipio_usr.getText().toString();
                String estado = estado_usr.getText().toString();
                String pais = pais_usr.getText().toString();
                String pagina = pagina_usr.getText().toString();
                String actividad = actividad_usr.getText().toString();
                String tel_oficina = tel_oficina_usr.getText().toString();
                String email_contacto = email_contacto_usr.getText().toString();
                pais = "146";
                actividad = "1";

                String parametro = myValues + "|" + calle + "|" + num_ext + "|" + num_int + "|" + colonia + "|" + codigo_postal +
                        "|" + municipio + "|" + estado +
                        "|" + pais + "|" + tel_oficina + "|" + pagina + "|" + email_contacto + "|" + actividad;

                Log.i("VALOR", parametro);
                RegistroUsuarioMailTask registroUsuarioMailTask = new RegistroUsuarioMailTask(getActivity());
                registroUsuarioMailTask.execute(parametro, usr_correo);
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

