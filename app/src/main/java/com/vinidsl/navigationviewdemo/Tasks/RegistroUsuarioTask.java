package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.MainActivity;
import com.vinidsl.navigationviewdemo.R;
import com.vinidsl.navigationviewdemo.Registrado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tlacaelel21 on 4/09/15.
 */
public class RegistroUsuarioTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = RegistroUsuarioTask.class.getSimpleName();
    private final String SERVICE_ID = "306";

    private final Context mContext;
    private ProgressDialog mDialog;
    private int resultado;
    private String usr_id;

    public RegistroUsuarioTask(Context context) {
        mContext = context;
    }

    private void readResult(String JsonStr) throws JSONException {

        try {

            JSONObject mainArray = new JSONObject(JsonStr);
            JSONObject mainNode = mainArray.getJSONObject("registro");
            resultado=mainNode.getInt("valido");
            Log.i("REc", ""+mainNode);


        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            resultado = -1;
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

            String parametro = c.encriptar(SERVICE_ID + "|" + params[0]);

            //Log.i("ALTA", parametro);

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
                resultado = -2;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                resultado = -2;
                return null;
            }

            // conversion del JSON a una lista de objetos
            forecastJsonStr = buffer.toString();
            readResult(forecastJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            resultado = -2;
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            resultado = -1;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    resultado = -2;
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
        if(resultado == -2) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(resultado == -1) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            if(resultado == 0) {
                Toast.makeText(mContext, "Error al dar de alta" , Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(mContext, "Alta correctamente", Toast.LENGTH_SHORT).show();
                Activity activity = (Activity) mContext;
                SharedPreferences preferencias =
                        activity.getSharedPreferences(activity.getString(R.string.espacio_prefs) , Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString(activity.getString(R.string.pref_idusuario), usr_id);
                editor.commit();

                MainActivity mainActivity= (MainActivity) activity;
                mainActivity.buscaUsuario();
                mainActivity.cambiarMenu();

                Registrado registrado= new Registrado();
                mainActivity.MuestraFragment(registrado);


            }


        }

    }

}

