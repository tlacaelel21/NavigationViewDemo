package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 23/07/15.
 */
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
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Tasks.RegistroUsuarioMailTask;
import com.vinidsl.navigationviewdemo.Tasks.RegistroUsuarioTask;

public class Registro extends Fragment {
    Button aButton;
    View rootView;
    TextView nombre_usr;
    TextView compania_usr_reg;
    TextView puesto_usr_reg;
    TextView telefono_usr_reg;
    TextView correo_usr_reg;
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

        nombre_usr=(TextView)rootView.findViewById(R.id.nombre_usr_reg);
        compania_usr_reg=(TextView)rootView.findViewById(R.id.compania_usr_reg);
        puesto_usr_reg=(TextView)rootView.findViewById(R.id.puesto_usr_reg);
        telefono_usr_reg=(TextView)rootView.findViewById(R.id.telefono_usr_reg);
        correo_usr_reg=(TextView)rootView.findViewById(R.id.correo_usr_reg);


        //aButton = (Button)rootView.findViewById(R.id.button);

                ((Button) rootView.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                        String usr_nombre = nombre_usr.getText().toString();
                        String usr_comp = compania_usr_reg.getText().toString();
                        String usr_puesto = puesto_usr_reg.getText().toString();
                        String usr_tel = telefono_usr_reg.getText().toString();
                        String usr_correo = correo_usr_reg.getText().toString();

                        //nombre|cargo|telefono|email|password|compañía|calle|noext|noint|colonia|cp|municipio|estado|
                        //id_pais|tel_oficina|pagina_web|email_contacto|id_actividad
                        String parametro = usr_nombre + "|"+usr_puesto+"|"+usr_tel+"|"+usr_correo+"|"+"mail@mail.com"+"|"+"1234"+
                                "|"+usr_comp+"|"+"calle"+"|"+"7"+"|"+" "+"|"+"colonia"+"|"+"cp"+"|"+"municipio"+"|"+"edo"+
                                "|"+"pais"+"|"+"tel_of"+"|"+"pag_web"+"|"+"email_contacto"+"|"+"1";


                        /*RegistroUsuarioTask registroUsuarioTask=new RegistroUsuarioTask(getActivity());
                        registroUsuarioTask.execute(parametro);*/
                        RegistroUsuarioMailTask registroUsuarioMailTask=new RegistroUsuarioMailTask(getActivity());
                        registroUsuarioMailTask.execute(parametro,usr_correo);


                        //Log.i("USRN", usr_nombre);

                        /*FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = manager.beginTransaction();
                        Registrado registrado = new Registrado();
                        Fragment newFragment = registrado;
                        Fragment actual = visualiza();
                        actual.onDestroy();
                        ft.remove(actual);
                        ft.replace(container.getId(), newFragment);
                        //container is the ViewGroup of current fragment
                        ft.addToBackStack(null);
                        ft.commit();*/
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
