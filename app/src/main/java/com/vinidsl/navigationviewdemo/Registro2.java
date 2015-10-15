package com.vinidsl.navigationviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Tasks.RegistroUsuarioMailTask;
import com.vinidsl.navigationviewdemo.Tasks.TaskRegistro;
import com.vinidsl.navigationviewdemo.Tasks.TaskRegistroActividades;

import java.util.ArrayList;
import java.util.List;

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
    Spinner spinner2;
    long idPais;
    TaskRegistro taskRegistro;

    String parametro="";

    long valoresPaises[];
    long valoresActividades[];

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

        final Spinner spinner=(Spinner)rootView.findViewById(R.id.spinner2);
        final Spinner spinnerAct=(Spinner)rootView.findViewById(R.id.spinnerActi);
        //addItemsOnSpinner2(spinner,rootView);
        //addItemsOnSpinnerAct(spinnerAct, rootView);
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
                pais = String.valueOf(spinner.getSelectedItemPosition());
                actividad = String.valueOf(spinnerAct.getSelectedItemPosition());
                idPais = valoresPaises[Integer.parseInt(pais)];
                long idAct = valoresActividades[Integer.parseInt(actividad)];
                Log.i("VALOR", ""+idPais);
                Log.i("VALOR", ""+idAct);
                String parametro = myValues + "|" + calle + "|" + num_ext + "|" + num_int + "|" + colonia + "|" + codigo_postal +
                        "|" + municipio + "|" + estado +
                        "|" + idPais + "|" + tel_oficina + "|" + pagina + "|" + email_contacto + "|" + idAct;
                //Log.i("VALOR", parametro);
            }
        });
        taskRegistro=new TaskRegistro(rootView.getContext());
        taskRegistro.setFragment(this);
        taskRegistro.execute();

        TaskRegistroActividades taskRegistroActividades=new TaskRegistroActividades(rootView.getContext());
        taskRegistroActividades.setFragment(this);
        taskRegistroActividades.execute();



        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //idPais=valoresPaises[arg2];
                if(valoresPaises!=null){
                    Log.i("VALOR", ""+valoresPaises.length);
                    Log.i("VALOR", ""+arg2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });*/

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

    public void addItemsOnSpinner2(Spinner spinner2,Context rootView,ArrayList<String> paises ) {
        List<String> list = new ArrayList<String>();
        for(int i=0; i<paises.size();i++){
            list.add(paises.get(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerAct(Spinner spinnerAct,Context rootView,ArrayList<String> actividades) {
        List<String> list = new ArrayList<String>();
        for(int i=0; i<actividades.size();i++){
            list.add(actividades.get(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(rootView, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAct.setAdapter(dataAdapter);
    }

    public void setValores(long valsPaises[]){
        this.valoresPaises=valsPaises;
    }
    public void setValoresAct(long valsActi[]){
        this.valoresActividades=valsActi;
    }
}

