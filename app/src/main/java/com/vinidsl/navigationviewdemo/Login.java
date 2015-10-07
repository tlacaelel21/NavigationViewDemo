package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 24/07/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Tasks.TaskIngreso;

public class Login extends Fragment {
    EditText edittext;
    Button aButton;
    View rootView;
    int valor=0;
    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.login, container, false);
        TextView username=(TextView)rootView.findViewById(R.id.textViewLogin);
        username.setText(Html.fromHtml(getString(R.string.login_text)));
        TextView username2=(TextView)rootView.findViewById(R.id.textViewLoginCuenta);
        username2.setText(Html.fromHtml(getString(R.string.login_text2)));

        aButton = (Button)rootView.findViewById(R.id.buttonRegistrate);

        //return rootView;
        ((TextView) rootView.findViewById(R.id.textViewLogin)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Toast.makeText(rootView.getContext(), "Rec Contraseña", Toast.LENGTH_LONG).show();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Recuperar recuperar= new Recuperar();
                Fragment newFragment = recuperar;
                Fragment actual= visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(),newFragment);
                //container is the ViewGroup of current fragment
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        ((Button) rootView.findViewById(R.id.buttonRegistrate)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Registro regis= new Registro();
                Fragment newFragment = regis;
                Fragment actual= visualiza();
                actual.onDestroy();
                ft.remove(actual);
                ft.replace(container.getId(),newFragment);
                //container is the ViewGroup of current fragment
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        ((Button) rootView.findViewById(R.id.boton_ingresar)).setOnClickListener(new View.OnClickListener() {
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
                    Activity activity=(Activity) rootView.getContext();
                    hideSoftKeyboard(activity);
                    TaskIngreso taskIngreso= new TaskIngreso(rootView.getContext());
                    taskIngreso.execute(email,pass);

                   /*SharedPreferences preferencias =
                            getActivity().getSharedPreferences(getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString(getString(R.string.pref_idusuario), "1");
                    editor.commit();
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.buscaUsuario();
                    mainActivity.cambiarMenu();*/

                   /* FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    Eventos mis_eventos= new Eventos();
                    Fragment newFragment = mis_eventos;
                    Fragment actual= visualiza();
                    //actual.onDestroy();
                    ft.remove(actual);
                    ft.replace(container.getId(),newFragment);
                    //container is the ViewGroup of current fragment
                    ft.addToBackStack(null);
                    ft.commit();*/

                }
            }
        });


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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}

