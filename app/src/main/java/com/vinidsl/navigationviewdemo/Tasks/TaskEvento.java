package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.EventoModel;
import com.vinidsl.navigationviewdemo.Model.Ponencia;
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
 * Created by tlacaelel21 on 1/09/15.
 */
public class TaskEvento extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = TaskEvento.class.getSimpleName();
    private final String SERVICE_ID = "303";

    private final Context mContext;
    private ProgressDialog mDialog;
    private EventoModel evento;
    private ArrayList<Ponencia> eventos;
    private int insertados;
    private AQuery aquery;

    public TaskEvento(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {

        try {

            JSONObject mainArray = new JSONObject(JsonStr);
            JSONObject mainNode = mainArray.getJSONObject("detalle");
            long int_id=Long.parseLong(mainNode.getString("int_id"));
            String rec_id=mainNode.getString("rec_id");
            String imageEvento = mainNode.getString("int_foto");
            String int_titulo=mainNode.getString("int_titulo");
            String ubicacion_evento=mainNode.getString("int_lugar")+" , "+mainNode.getString("pais_desc");
            String cat_desc=mainNode.getString("cat_desc");
            String fecha_inicio_evento = mainNode.getString("int_inicio");
            String fecha_fin_evento= mainNode.getString("int_final");
            String int_desc=mainNode.getString("int_desc");

            evento = new EventoModel( int_id,rec_id,imageEvento,ubicacion_evento,cat_desc,fecha_inicio_evento,fecha_fin_evento,int_titulo,int_desc);

            insertados = 0;

            if ( evento != null ) {
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

            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            String parametro = c.encriptar(SERVICE_ID + "|" + params[0]);

            Log.i(LOG_TAG, parametro);

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
        if(evento == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            aquery = new AQuery(mContext);
            Activity a = (Activity) mContext;
            ImageView imageEvento=(ImageView) a.findViewById(R.id.imageEvento);
            //TextView rec_id= (TextView) a.findViewById(R.id.evento_nombre);
            TextView nombre_evento= (TextView) a.findViewById(R.id.nombre_evento);
            TextView ubicacion_evento= (TextView) a.findViewById(R.id.ubicacion_evento);
            TextView cat_desc= (TextView) a.findViewById(R.id.cat_desc);
            TextView fecha_inicio_evento= (TextView) a.findViewById(R.id.fecha_inicio_evento);
            TextView fecha_fin_evento= (TextView) a.findViewById(R.id.fecha_fin_evento);
            TextView titulo_evento= (TextView) a.findViewById(R.id.titulo_evento);
            TextView desc_evento= (TextView) a.findViewById(R.id.desc_evento);
            String pathFoto = evento.getImageEvento();


            if(pathFoto != null && !pathFoto.isEmpty()) {
                aquery.id(R.id.imageEvento).image(evento.getImageEvento());
            }

            nombre_evento.setText(evento.getNombre_evento());
            ubicacion_evento.setText(evento.getUbicacion_evento());
            cat_desc.setText(evento.getCat_desc());
            fecha_inicio_evento.setText(evento.getFecha_inicio_evento());
            fecha_fin_evento.setText(evento.getFecha_fin_evento());
            titulo_evento.setText(evento.getTitulo_evento());
            desc_evento.setText(evento.getDesc_evento());

        }

    }

}

