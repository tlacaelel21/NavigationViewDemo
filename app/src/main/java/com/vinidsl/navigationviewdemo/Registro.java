package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 23/07/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Tasks.RegistroUsuarioMailTask;
import com.vinidsl.navigationviewdemo.Tasks.RegistroUsuarioTask;
import com.vinidsl.navigationviewdemo.Tasks.TaskRegistro;
import com.vinidsl.navigationviewdemo.Tasks.TaskTermCondi;
import com.vinidsl.navigationviewdemo.Tasks.ValidaCorreo;

public class Registro extends Fragment {
    Button aButton;
    View rootView;
    TextView nombre_usr;
    TextView compania_usr_reg;
    TextView puesto_usr_reg;
    TextView telefono_usr_reg;
    TextView correo_usr_reg;
    TextView pass1;
    TextView pass2;

    public Registro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("ENTRO", "ENTRAAAAAA***************************************************7777777");
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.registro, container, false);
        TextView condic=(TextView)rootView.findViewById(R.id.textCondi);
        TextView priv=(TextView)rootView.findViewById(R.id.textPriv);
        condic.setText(Html.fromHtml(getString(R.string.cond)));
        priv.setText(Html.fromHtml(getString(R.string.priv)));

        nombre_usr=(TextView)rootView.findViewById(R.id.nombre_usr_reg);
        compania_usr_reg=(TextView)rootView.findViewById(R.id.compania_usr_reg);
        puesto_usr_reg=(TextView)rootView.findViewById(R.id.puesto_usr_reg);
        telefono_usr_reg=(TextView)rootView.findViewById(R.id.telefono_usr_reg);
        correo_usr_reg=(TextView)rootView.findViewById(R.id.correo_usr_reg);
        pass1=(TextView)rootView.findViewById(R.id.password1);
        pass2=(TextView)rootView.findViewById(R.id.password2);

        correo_usr_reg.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final String email = correo_usr_reg.getText().toString();
                    ValidaCorreo validaCorreo = new ValidaCorreo(rootView.getContext());
                    validaCorreo.execute(email);
                }
            }
        });


        ((TextView) rootView.findViewById(R.id.textPriv)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Toast.makeText(rootView.getContext(), "Rec Contraseña", Toast.LENGTH_LONG).show();
                Activity activityRef = (Activity) rootView.getContext();
                String urlLink = "http://desarrollo.smartthinking.com.mx:8080/Cptm/media/Avisos/2_20150922143542.pdf";
                Intent intent = new Intent(rootView.getContext(), WebActivity.class);
                intent.putExtra("urlOpen", "" + urlLink);
                intent.putExtra("urlTipo", "1");
                activityRef.startActivity(intent);
            }
        });

        //aButton = (Button)rootView.findViewById(R.id.button);

                ((Button) rootView.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //Log.d("ENTRO", "ENTRAAAAAA***************************************************888888888");
                        String usr_nombre = nombre_usr.getText().toString();
                        String usr_comp = compania_usr_reg.getText().toString();
                        String usr_puesto = puesto_usr_reg.getText().toString();
                        String usr_tel = telefono_usr_reg.getText().toString();
                        String usr_correo = correo_usr_reg.getText().toString();
                        String usr_psw1 = pass1.getText().toString();
                        String usr_psw2 = pass2.getText().toString();

                        if (usr_psw1.equals(usr_psw2) && !usr_psw1.equals("") && !usr_psw2.equals("")) {

                            //nombre|cargo|telefono|email|password|compañía|calle|noext|noint|colonia|cp|municipio|estado|
                            //id_pais|tel_oficina|pagina_web|email_contacto|id_actividad
                            String parametro = usr_nombre + "|" + usr_puesto + "|" + usr_tel + "|" + usr_correo + "|" + usr_correo + "|" + pass1 +
                                    "|" + usr_comp;


                        /*RegistroUsuarioTask registroUsuarioTask=new RegistroUsuarioTask(getActivity());
                        registroUsuarioTask.execute(parametro);*/
                        /*RegistroUsuarioMailTask registroUsuarioMailTask=new RegistroUsuarioMailTask(getActivity());
                        registroUsuarioMailTask.execute(parametro,usr_correo);*/
                          /*  Activity activity = (Activity) rootView.getContext();
                            SharedPreferences preferencias =
                                    activity.getSharedPreferences(activity.getString(R.string.espacio_prefs) , Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = preferencias.edit();
                            editor.putString(activity.getString(R.string.pref_idusuario), usr_id);
                            editor.commit();*/
                            Bundle bundle = new Bundle();
                            bundle.putString("message", parametro);
                            bundle.putString("usr_correo", usr_correo);

                            Registro2 fragInfo = new Registro2();
                            fragInfo.setArguments(bundle);
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = manager.beginTransaction();
                            Fragment newFragment = fragInfo;
                            Fragment actual = visualiza();
                            actual.onDestroy();
                            ft.remove(actual);
                            ft.replace(container.getId(), newFragment);
                            //container is the ViewGroup of current fragment
                            ft.addToBackStack(null);
                            ft.commit();

                        } else {
                            Context mContext = rootView.getContext();
                            Toast.makeText(mContext, "Las contreñas no coinciden", Toast.LENGTH_LONG).show();
                        }


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

        TaskTermCondi taskTermCondi= new TaskTermCondi(rootView.getContext());
        taskTermCondi.setFragment(this);
        taskTermCondi.execute();

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

    public void colocaLigas(String ligaCondi,TextView textCondi,String ligaTerm,TextView textPriv,Activity a, Context mContext){
        final String ligaTer=ligaTerm;
        final String ligaCon=ligaCondi;
        final Activity activity=a;
        final Context context=mContext;

        Log.i("LIGA",""+ligaCon);
        Log.i("LIGA",""+ligaTer);

        textCondi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String urlLink = ligaCon;
                Uri uri = Uri.parse(urlLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("urlOpen", "" + uri);
                intent.putExtra("urlTipo", "1");
                activity.startActivity(intent);
            }
        });

        textPriv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String urlLink = ligaTer;
                Uri uri = Uri.parse(urlLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("urlOpen", "" + uri);
                intent.putExtra("urlTipo", "1");
                activity.startActivity(intent);
            }
        });
    }
}
