package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Tasks.ActualizarPerfilTask;
import com.vinidsl.navigationviewdemo.Tasks.ConsultaPerfilTask;
import com.vinidsl.navigationviewdemo.Tasks.GuardarDatosFacturaTask;

public class PerfilFragment extends Fragment  {

    public PerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        // aButton = (Button)rootView.findViewById(R.id.button_registrado);

        ((Button) rootView.findViewById(R.id.perfil_boton_actualizar)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Evento evento = new Evento();
                Fragment newFragment = evento;
                Fragment actual= visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(),newFragment);
                //container is the ViewGroup of current fragment
                ft.addToBackStack(null);
                ft.commit();*/
                actualizaPerfil();
            }
        });

        ImageButton agregarBoton =
                (ImageButton) rootView.findViewById(R.id.perfil_boton_agregar_datos);
        //final LinearLayout contenedor = (LinearLayout) rootView.findViewById(R.id.perfil_df_contenedor);

        agregarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(!contenedor.isShown()) {
                    contenedor.setVisibility(View.VISIBLE);
                }*/

                showDatosFacturaDialog();

            }
        });
        Activity a=getActivity();
        //lee el idUsuario de la session
        SharedPreferences preferencias =
                a.getSharedPreferences(a.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idPerfil = preferencias.getString(a.getString(R.string.pref_idusuario), "0");


        ConsultaPerfilTask task = new ConsultaPerfilTask(getActivity());
        task.execute(idPerfil);

        return rootView;
    }

    public Fragment visualiza(){
        this.onDestroy();
        return this;
    }

    public void actualizaPerfil() {

        Activity a = getActivity();
        TextView correoTV = (TextView) a.findViewById(R.id.perfil_correo);
        TextView contraseniaTV = (TextView) a.findViewById(R.id.perfil_contrasenia);
        TextView confContraseniaTV = (TextView) a.findViewById(R.id.perfil_conf_contrasenia);
        TextView nombreTV = (TextView) a.findViewById(R.id.perfil_nombre);
        TextView cargoTV = (TextView) a.findViewById(R.id.perfil_cargo);
        TextView telefonoTV = (TextView) a.findViewById(R.id.perfil_telefono);
        TextView nombreComTV = (TextView) a.findViewById(R.id.perfil_nombre_comp);
        TextView calleComTV = (TextView) a.findViewById(R.id.perfil_calle_comp);
        TextView numeroExtComTV = (TextView) a.findViewById(R.id.perfil_num_ext);
        TextView numeroIntComTV = (TextView) a.findViewById(R.id.perfil_num_int);
        TextView cpComTV = (TextView) a.findViewById(R.id.perfil_codigo_comp);
        TextView coloniaComTV = (TextView) a.findViewById(R.id.perfil_colonia_comp);
        TextView municipioComTV = (TextView) a.findViewById(R.id.perfil_municipio);
        TextView estadoComTV = (TextView) a.findViewById(R.id.perfil_estado);
        TextView paisComTV = (TextView) a.findViewById(R.id.perfil_pais);
        TextView sitioComTV = (TextView) a.findViewById(R.id.perfil_pagina);
        TextView actComTV = (TextView) a.findViewById(R.id.perfil_actividad);

        //lee el idUsuario de la session
        SharedPreferences preferencias =
                a.getSharedPreferences(a.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idPerfil = preferencias.getString(a.getString(R.string.pref_idusuario), "0");

        //String idPerfil = "17";
        String idPais = "1";
        String idActividad = "1";
        String parametro;
        String telCom = "00000000";

        String correo = correoTV.getText().toString();
        String contrasenia = contraseniaTV.getText().toString();
        String nombre = nombreTV.getText().toString();
        String cargo = cargoTV.getText().toString();
        String telefono = telefonoTV.getText().toString();
        String nombreCom = nombreComTV.getText().toString();
        String calleCom = calleComTV.getText().toString();
        String numExt = numeroExtComTV.getText().toString();
        String numInt = numeroIntComTV.getText().toString();
        String cpCom = cpComTV.getText().toString();
        String coloniaCom = coloniaComTV.getText().toString();
        String munCom = municipioComTV.getText().toString();
        String estadoCom = estadoComTV.getText().toString();
        String paisCom = paisComTV.getText().toString();
        String sitioCom = sitioComTV.getText().toString();
        String actCom = actComTV.getText().toString();

        /*id_usuario|contraseña|nombre|cargo|tel_usu|compañía|

                calle|noext|noint|colonia|cp|municipio|estado|id_pais|

            tel_oficina|pagina_web|email_contacto|id_actividad*/

        /*
            id_usuario|contraseña|nombre|
            cargo|tel_usu|compañía|
            calle|noext|noint|
            colonia|cp|municipio|
            estado|id_pais|tel_oficina|
            pagina_web|email_contacto|id_actividad
        */

        parametro = idPerfil + "|" + contrasenia + "|" + nombre + "|"
                + cargo + "|" + telefono + "|" + nombreCom + "|"
                + calleCom + "|" + numExt + "|" + numInt + "|"
                + coloniaCom + "|" + cpCom + "|" + munCom + "|"
                + estadoCom  + "|" + idPais + "|" + telCom + "|"
                + sitioCom + "|" + correo + "|" + idActividad;


        ActualizarPerfilTask task = new ActualizarPerfilTask(getActivity());
        task.execute(parametro);
    }

    public void showDatosFacturaDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DatosFacturaDialog();
        dialog.show(getFragmentManager(), "DatosFacturaDialog");
    }

    public void validaDatos() {
        // numerico ^[0-9]+$

        //^(([\w]+)[\s]*)+$
    }


}
