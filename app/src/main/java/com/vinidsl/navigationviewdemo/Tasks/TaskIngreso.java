package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.DatosFacturaAdapter;
import com.vinidsl.navigationviewdemo.Adapter.MisEventosAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Eventos;
import com.vinidsl.navigationviewdemo.MainActivity;
import com.vinidsl.navigationviewdemo.Model.DatosFactura;
import com.vinidsl.navigationviewdemo.Model.MisEventosModel;
import com.vinidsl.navigationviewdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by tlacaelel21 on 2/09/15.
 */
public class TaskIngreso extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = TaskIngreso.class.getSimpleName();
    private final String SERVICE_ID = "305";

    private final Context mContext;
    private ProgressDialog mDialog;
    private DatosFactura df;
    private String resultado;
    private String valido;
    private  String id_usuario;
    private String usu_nom;
    private String sts_id;

    public TaskIngreso(Context context) {
        mContext = context;
    }

    public void setDatoFactura(DatosFactura valores) {
        this.df = valores;
    }

    private void readResult(String JsonStr) throws JSONException {

        try {

            JSONObject mainArray = new JSONObject(JsonStr);
            JSONObject mainNode = mainArray.getJSONObject("login");

            // String valorEnc=obj.encriptar("305|prueba@cptm.gob|1234|(null)");

           // Log.i(LOG_TAG, JsonStr);

            //resultado = mainNode.getString("login");
           valido=mainNode.getString("valido");
                if(null!=valido) {
                    if (Integer.parseInt(valido) == 1) {
                        id_usuario = mainNode.getString("id_usuario");
                        usu_nom = mainNode.getString("usu_nom");
                        sts_id = mainNode.getString("sts_id");
                    }
                }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            resultado = "-1";
        }
    }

    protected void onPreExecute() {
        mDialog = ProgressDialog
                .show(mContext, "Espera", "Cargando...");
    }

    @Override
    protected Void doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;

        try {

            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            // String valorEnc=obj.encriptar("305|prueba@cptm.gob|1234|(null)");
            String parametro = c.encriptar(SERVICE_ID + "|"+params[0]+"|"+params[1]+"|(null)");

            //Log.i(LOG_TAG, parametro);

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, parametro).build();

            // Inicializando conexión
            URL url = new URL(builtUri.toString());
            // Estableciendo parametros de petición
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            // Conectando al servicio
            urlConnection.connect();

            // Leer respuesta de servidor
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.i("ERROR","INPUT NULL");
                resultado = "-2";
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                Log.i("ERROR","EMPTY BUFFER");
                resultado = "-2";
                return null;
            }

            // conversion del JSON a una lista de objetos
            forecastJsonStr = buffer.toString();
            readResult(forecastJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            resultado =  "-2";
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            resultado =  "-1";
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    resultado =  "-2";
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // quitar dialogo de carga
        if ((mDialog != null) && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        // validaciones correspondientes
        /*if(resultado == "-2") {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else*/
        if(null!=valido){
            if(Integer.parseInt(valido)!=1){
                Toast.makeText(mContext, "Datos incorrectos", Toast.LENGTH_LONG).show();
                Log.i("DESCARGA", "SIN DATOS");

                // ejecución para un caso ideal donde todo resulto exitoso
            } else {
                //Log.i("USR", "" + id_usuario);
                if(Integer.parseInt(sts_id)==2){
                    Toast.makeText(mContext, "Aún no ha sido validado por el sistema de CPTM", Toast.LENGTH_LONG).show();
                }
                Activity activity = (Activity) mContext;
                SharedPreferences preferencias =
                        activity.getSharedPreferences(activity.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
                        //activity.getSharedPreferences("CPTM_PrefSpace", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(activity.getString(R.string.pref_idusuario), id_usuario);
                editor.putString(activity.getString(R.string.sts_id), sts_id);

                //editor.putString("i_u_act", id_usuario);
                editor.commit();
                MainActivity mainActivity= (MainActivity) activity;
                mainActivity.buscaUsuario();
                mainActivity.cambiarMenu();

                Eventos eventos=new Eventos();
                mainActivity.MuestraFragment(eventos);
                //MuestraFragment(eventos);

               /*if(resultado == "") {
                    Toast.makeText(mContext, "Error al guardar datos" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                    Activity a = (Activity) mContext;
                    ViewPager pager = (ViewPager) a.findViewById(R.id.perfil_df_contenedor);

                    if(!pager.isShown()) {
                        pager.setVisibility(View.VISIBLE);
                    }

                    DatosFacturaAdapter adapter = (DatosFacturaAdapter) pager.getAdapter();
                    ArrayList<DatosFactura> lista = adapter.getValues();
                    lista.add(df);
                    adapter.setValues(lista);
                    adapter.notifyDataSetChanged();

                }
    */
            }
        }

    }


}
