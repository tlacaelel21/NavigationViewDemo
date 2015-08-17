package com.vinidsl.navigationviewdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Model.Ponente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by root on 14/08/15.
 */
public class PonentesTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = PonentesTask.class.getSimpleName();

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<Ponente> ponentes;
    private int insertados;

    public PonentesTask(Context context) {
        mContext = context;
    }

	/*
		Método válido para hacer la conversión
		Suponiendo que el documento JSON tiene la siguiente estructura

		{
			"ponentes" : [
				{"id" : "1023940", "nombre" : "Ponente 1", "puesto" : "Desarrollador", "foto" : "www.../imagen.jgp", "url": "www...", "tipo": "1" },
				{"id" : "1023940", "nombre" : "Ponente 1", "puesto" : "Desarrollador", "foto" : "www.../imagen.jgp", "url": "www...", "tipo": "1" },
				{"id" : "1023940", "nombre" : "Ponente 1", "puesto" : "Desarrollador", "foto" : "www.../imagen.jgp", "url": "www...", "tipo": "1" },
				{"id" : "1023940", "nombre" : "Ponente 1", "puesto" : "Desarrollador", "foto" : "www.../imagen.jgp", "url": "www...", "tipo": "1" },
				{"id" : "1023940", "nombre" : "Ponente 1", "puesto" : "Desarrollador", "foto" : "www.../imagen.jgp", "url": "www...", "tipo": "1" }
			]
		}

	*/


    private void populateList(String JsonStr)
            throws JSONException {

        try {
            ponentes = new ArrayList<Ponente>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray forecastJson=null;
            // este método extrae un arreglo de JSON con el nombre de llave_arreglo
            JSONArray mainArray = forecastJson.getJSONArray(Integer.parseInt("abajo"));

            for(int i = 0; i < mainArray.length(); i++) {

                JSONObject node = mainArray.getJSONObject(i);

                long id = node.getLong("id");
                String nombre = node.getString("nombre");
                String puesto = node.getString("puesto");
                String pathFoto = node.getString("foto");
                String url = node.getString("url");
                int tipo = node.getInt("tipo");

                //Ponente p =
                  //      new Ponente(id, nombre, puesto, pathFoto, url, tipo);

               // ponentes.add(p);

            }

            insertados = 0;

            if ( ponentes.size() > 0 ) {
                insertados = ponentes.size();
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
    protected Void doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;

        try {

            // Configurando parametros de conexión
            final String BASE_URL =
                    "http://desarrollo.smartthinking.com.mx:8080/Cptm/DataSMobile?";
            final String QUERY_PARAM = "cod=";
            String parametro = "BuPsEksTJeHGwt2ldR9pYw==";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM, parametro).build();

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
        if(ponentes == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            /*ListView lista = (ListView) mContext.findViewById(R.id.id_lista); // id del ListView
            PonenteAdapter adapter = new PonenteAdapter((Activity) mContext, ponentes);
            lista.setAdapter(adapter);*/

        }

    }

}