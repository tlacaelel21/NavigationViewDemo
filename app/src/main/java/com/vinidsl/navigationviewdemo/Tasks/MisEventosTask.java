package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.FeriasIntAdapter;
import com.vinidsl.navigationviewdemo.Adapter.MisEventosAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Evento;
import com.vinidsl.navigationviewdemo.Model.MisEventosModel;
import com.vinidsl.navigationviewdemo.R;

import org.json.JSONArray;
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
 * Created by tlacaelel21 on 4/09/15.
 */
public class MisEventosTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = MisEventosTask.class.getSimpleName();
    private final String SERVICE_ID = "315";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<MisEventosModel> misEventosListado;
    private int insertados;
    long reg_id;

    //String valorEnc=obj.encriptar("315|17");
    public MisEventosTask(Context context) {
        mContext = context;
    }
    int mes=0;
    private void populateList(String JsonStr)
            throws JSONException {
        try {
            misEventosListado = new ArrayList<MisEventosModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("mis_ferias"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

            for(int i = 0; i < mainArray.length(); i++) {

                JSONObject node = mainArray.getJSONObject(i);
                reg_id = node.getLong("reg_id");
                // String int_foto = node.getString("int_foto");
                String ubicacion_evento = node.getString("int_titulo");
                String status_evento = node.getString("sr_desc");
                String nombre_evento = node.getString("int_titulo");
               
                MisEventosModel misEventosModel =
                        new MisEventosModel(nombre_evento,"",status_evento,reg_id);
                misEventosListado.add(misEventosModel);
            }

            insertados = 0;

            if ( misEventosListado.size() > 0 ) {
                insertados = misEventosListado.size();
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
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

        Activity act= (Activity)mContext;
        //lee el idUsuario de la session
        SharedPreferences preferencias =
                act.getSharedPreferences(act.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idUsuario = preferencias.getString(act.getString(R.string.pref_idusuario), "0");
        //String sts_id = preferencias.getString(act.getString(R.string.sts_id), "0");
        //Log.i("USR", "" + idUsuario);

        try {

            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            String parametro = c.encriptar(SERVICE_ID + "|" + idUsuario);
            parametro=parametro.replaceAll("\\+", "%2B");
            //Log.i("SERV",parametro);
            //String parametro = c.encriptar(SERVICE_ID);

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
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            // conversion del JSON a una lista de objetos
            forecastJsonStr = buffer.toString();
            populateList(forecastJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
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
        if(misEventosListado == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {
            ListView lista = (ListView)
                    ((Activity) mContext).findViewById(R.id.listado_mis_eventos); // id del ListView
            Activity activity= (Activity) mContext;
            final MisEventosAdapter adapter =
                    new MisEventosAdapter(activity,1,misEventosListado);
            lista.setAdapter(adapter);

        }

    }

}
