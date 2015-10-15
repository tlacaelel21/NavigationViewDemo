package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.AdapterCalendario;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.CalendarioModel;
import com.vinidsl.navigationviewdemo.R;
import com.vinidsl.navigationviewdemo.Registro;
import com.vinidsl.navigationviewdemo.Registro2;

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
 * Created by tlacaelel21 on 15/10/15.
 */
public class TaskTermCondi extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = TaskTermCondi.class.getSimpleName();
    private final String SERVICE_ID = "327";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<CalendarioModel> calendarioListado;
    private int insertados;
    Registro miFrag;

    String st_url_cond;
    String st_url_term;

    public TaskTermCondi(Context context) {
        mContext = context;
    }
    int mes=0;
    private void populateList(String JsonStr)
            throws JSONException {
        try {
            calendarioListado = new ArrayList<CalendarioModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("settings"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

            for(int i = 0; i < mainArray.length(); i++) {
                JSONObject node = mainArray.getJSONObject(i);;
                String st_titulo = node.getString("st_titulo");
                String st_url = node.getString("st_url");
                CalendarioModel calendarioM;
                if(null!=st_titulo){
                    if(st_titulo.equals("condiciones")){
                        st_url_cond=st_url;
                    }
                    if(st_titulo.equals("privacidad")){
                        st_url_term=st_url;
                    }

                }
            }
            insertados = 0;

            if ( calendarioListado.size() > 0 ) {
                insertados = calendarioListado.size();
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

        try {

            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            //String parametro = c.encriptar(SERVICE_ID + "|" + params[0]);
            String parametro = c.encriptar(SERVICE_ID );
            parametro=parametro.replaceAll("\\+", "%2B");
            parametro=parametro.replaceAll("\\/", "%2F");
            Log.i("SERV",parametro);

            /*Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, "Lk%2B7p%2FCuWuhh5u58dn6nUQ==").build();*/

            Uri builtUri=Uri.parse(BASE_URL+"cod="+parametro);
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
        if(st_url_cond == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } /*else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso*/
         else {
            Activity a = (Activity) mContext;
            TextView textCondi=(TextView)a.findViewById(R.id.textCondi);
            TextView textPriv=(TextView)a.findViewById(R.id.textPriv);

            final String BASE_URL =
                    mContext.getString(R.string.base_img);
            miFrag.colocaLigas(BASE_URL+st_url_cond,textCondi,BASE_URL+st_url_term,textPriv,a,mContext);

        }

    }

    public void setFragment(Registro frag) {
        miFrag=frag;
    }

}

