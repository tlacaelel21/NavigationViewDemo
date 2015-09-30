package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Expositores;
import com.vinidsl.navigationviewdemo.InscripcionFI;
import com.vinidsl.navigationviewdemo.Model.EventoModel;
import com.vinidsl.navigationviewdemo.Model.InscripcionModel;
import com.vinidsl.navigationviewdemo.Model.Ponencia;
import com.vinidsl.navigationviewdemo.NoticiasActivity;
import com.vinidsl.navigationviewdemo.PatrocinadoresActivity;
import com.vinidsl.navigationviewdemo.PonentesActivity;
import com.vinidsl.navigationviewdemo.ProgramaActivity;
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
 * Created by tlacaelel21 on 30/09/15.
 */
public class InscripcionTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = InscripcionTask.class.getSimpleName();
    private final String SERVICE_ID = "313";
    private final Context mContext;
    private ProgressDialog mDialog;
    private InscripcionModel inscripcionModel;
    private ArrayList<Ponencia> eventos;
    private int insertados;
    private AQuery aquery;
    Activity act;
    String sts_id="";
    long int_id;
    String idUsuario;
    long costo;

    public InscripcionTask(Context context) {
        act=(Activity)context;
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {
        try {
            JSONObject mainNode = new JSONObject(JsonStr);
            costo = mainNode.getInt("costo");

            inscripcionModel = new InscripcionModel(""+costo);

            insertados = 0;

            if ( inscripcionModel != null ) {
                insertados = 1;
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


            Activity activity = (Activity) mContext;
            /*SharedPreferences preferencias =
                    activity.getSharedPreferences(activity.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
            idUsuario = preferencias.getString(activity.getString(R.string.pref_idusuario), "0");*/

            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            String parametro = c.encriptar(SERVICE_ID + "|" + params[0]+"|"+params[1]);
            parametro=parametro.replaceAll("\\+", "%2B");
            parametro=parametro.replaceAll("\\/", "%2F");

            Log.i(LOG_TAG, parametro);

           /* Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, parametro).build();*/

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
        if(inscripcionModel == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            aquery = new AQuery(mContext);
            Activity a = (Activity) mContext;
            TextView costo_stands= (TextView) a.findViewById(R.id.costo_stands);
            costo_stands.setText(inscripcionModel.getCosto());
            //Log.i("FOTO",""+evento.getImageEvento());
            final Button inscribete=(Button) a.findViewById(R.id.registro_fi);

            inscribete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(aquery.getContext(), "*REGISTRADO* ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}


