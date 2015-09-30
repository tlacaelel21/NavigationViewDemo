package com.vinidsl.navigationviewdemo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Tasks.RecuperaTask;
import com.vinidsl.navigationviewdemo.Tasks.TaskIngreso;

/**
 * Created by tlacaelel21 on 29/09/15.
 */
public class Recuperar extends Fragment {
    EditText edittext;
    Button aButton;
    View rootView;
    int valor=0;
    public Recuperar() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.recuperar_psw, container, false);



        //aButton = (Button)rootView.findViewById(R.id.registro_completo);

        ((Button) rootView.findViewById(R.id.registro_completo)).setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               TextView e_mail=(TextView)rootView.findViewById(R.id.correo_electronico_rec);
               final String email=e_mail.getText().toString();
                RecuperaTask recuperaTask= new RecuperaTask(rootView.getContext());
                recuperaTask.execute(email);
            }
        });

       /* ((Button) rootView.findViewById(R.id.boton_ingresar)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                //new AlertDialog.Builder(rootView.getContext()).setTitle("Argh").setMessage("Watch out!").setNeutralButton("Close", null).show();
                TextView e_mail=(TextView)rootView.findViewById(R.id.correo_electronico);
                TextView psw=(TextView)rootView.findViewById(R.id.psw);
                String email=e_mail.getText().toString();
                String pass=psw.getText().toString();
                boolean paso=false;

                if(email.length()>0&&pass.length()>0){
                    paso=true;

                    //((GlobalClass)(rootView.getContext())).setSomeVariable("foo");
                }else{

                    new AlertDialog.Builder(rootView.getContext()).setTitle("Cptm").
                            setMessage("Ingrese datos").setNeutralButton("Cerrar", null).show();
                }
                if(paso){

                    TaskIngreso taskIngreso= new TaskIngreso(rootView.getContext());
                    taskIngreso.execute(email,pass);

                   *//*SharedPreferences preferencias =
                            getActivity().getSharedPreferences(getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(getString(R.string.pref_idusuario), "1");
                    editor.commit();
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.buscaUsuario();
                    mainActivity.cambiarMenu();*//*

                   *//* FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    Eventos mis_eventos= new Eventos();
                    Fragment newFragment = mis_eventos;
                    Fragment actual= visualiza();
                    //actual.onDestroy();
                    ft.remove(actual);
                    ft.replace(container.getId(),newFragment);
                    //container is the ViewGroup of current fragment
                    ft.addToBackStack(null);
                    ft.commit();*//*

                }
            }
        });*/


        /*aButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 //Log.d("ENTRO","ENTRAAAAAA");
                 rootView= inflater.inflate(R.layout.registro, container, false);

                 // User clicked my button, do something here!
             }
         });*/
        //
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextInputLayout
        //rootView= inflater.inflate(R.layout.registro, container, false);
    }

    public Fragment visualiza(){
        this.onDestroy();
        return this;
    }
}

