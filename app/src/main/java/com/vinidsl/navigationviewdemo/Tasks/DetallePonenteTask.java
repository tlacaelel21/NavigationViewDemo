package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Adapter.PonenciaAdapter;
import com.vinidsl.navigationviewdemo.Adapter.PonentesAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.Ponencia;
import com.vinidsl.navigationviewdemo.Model.Ponente;
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
 * Created by JoseRogelio on 15/08/2015.
 */
public class DetallePonenteTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = DetallePonenteTask.class.getSimpleName();
    private final String SERVICE_ID = "320";

    private final Context mContext;
    private ProgressDialog mDialog;
    private Ponente ponente;
    private ArrayList<Ponencia> ponencias;
    private int insertados;
    private AQuery aquery;

    public DetallePonenteTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {

        try {

            JSONObject mainNode = new JSONObject(JsonStr);
           // JSONObject mainNode = new JSONObject(JsonStr);

            // detalle_pon:{pon_nombre, pon_foto, pon_empresa, pon_correo, pon_puesto, pon_calif},
            // ponencias:[{pro_id, pro_nombre, pro_fecha_ini, pro_lugar}, {}, {}]

            JSONObject mainArray = mainNode.getJSONObject("detalle_pon");


            String nombre = mainArray.getString("pon_nombre");
            String foto = mainArray.getString("pon_empresa");
            String empresa = mainArray.getString("pon_empresa");
            String correo = mainArray.getString("pon_correo");
            String calificacion = mainArray.getString("pon_calif");

            JSONArray mainArrayPonencias = mainNode.getJSONArray("ponencias");

            for(int i = 0; i < mainArrayPonencias.length(); i++) {
                JSONObject node = mainArrayPonencias.getJSONObject(i);

                long id = node.getLong("pro_id");
                String nombreArr = node.getString("pro_nombre");
                String fechaIniArr = node.getString("pro_fecha_ini");
                String lugarArr = node.getString("pro_lugar");

                Log.i("PONEN","* "+id+" "+nombreArr+" "+fechaIniArr+" "+lugarArr+" *");
                /*Ponencia p =
                        new Ponencia(id, nombreArr, fechaIniArr, lugarArr);

                ponencias.add(p);*/

            }

            ponente = new Ponente(0, 0, nombre, empresa, foto, correo, calificacion);

            insertados = 0;

            if ( ponente != null ) {
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
            parametro=parametro.replaceAll("\\+", "%2B");
            parametro=parametro.replaceAll("\\/", "%2F");

           /* Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, parametro).build();*/

            Uri builtUri=Uri.parse(BASE_URL + "cod=" + parametro);
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
        if(ponente == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            aquery = new AQuery(mContext);
            Activity a = (Activity) mContext;
            TextView nombreTV = (TextView) a.findViewById(R.id.ponente_nombre);
            TextView puestoTV = (TextView) a.findViewById(R.id.ponente_puesto);
            TextView empresaTV = (TextView) a.findViewById(R.id.ponente_empresa);
            TextView correoTV = (TextView) a.findViewById(R.id.ponente_correo);
            RatingBar califRB = (RatingBar) a.findViewById(R.id.ponente_calificacion);
            ListView lista = (ListView) a.findViewById(R.id.ponente_lista_ponencias);

            String pathFoto = ponente.getPathFoto();

            float calif;
            try {
                calif = Float.parseFloat(ponente.getCalificacion());
            } catch (NumberFormatException e) {
                calif = 0f;
            }

            if(pathFoto != null && !pathFoto.isEmpty()) {
                aquery.id(R.id.ponente_foto).image(ponente.getPathFoto());
            }

            nombreTV.setText(ponente.getNombre());
            puestoTV.setText(ponente.getPuesto());
            empresaTV.setText("");
            correoTV.setText(ponente.getCorreo());

            califRB.setMax(5);
            califRB.setStepSize(0.5f);
            califRB.setRating(calif);

            PonenciaAdapter adapter = new PonenciaAdapter((Activity) mContext, ponencias);
            lista.setAdapter(adapter);

        }

    }

}
