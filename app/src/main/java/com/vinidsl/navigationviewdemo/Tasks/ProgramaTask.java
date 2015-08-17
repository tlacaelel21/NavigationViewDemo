package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.PatrocinadoresAdapter;
import com.vinidsl.navigationviewdemo.Adapter.ProgramasAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.ProgramaActivity;
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
public class ProgramaTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = ProgramaTask.class.getSimpleName();
    private final String SERVICE_ID = "321";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<ArrayList<Horario>> programa;
    private int insertados;

    public ProgramaTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {

        try {
            programa = new ArrayList<ArrayList<Horario>>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("programa"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

            for(int i = 0; i < mainArray.length(); i++) {

                // programa:[[{pro_id, pro_nombre, pro_fecha_ini, pro_fecha_fin, pro_fecha_ini, pro_fecha_fin, pro_lugar, pro_foto, pon_nombre,
                //pon_empresa, pon_puesto}, {}, {}], [dia2], [dia3]]

                JSONArray innerArray = mainArray.getJSONArray(i);

                ArrayList<Horario> horario = new ArrayList<Horario>();

                for(int j = 0; j < innerArray.length(); j++) {
                    JSONObject node = mainArray.getJSONObject(j);

                    long id = node.getLong("pro_id");
                    String nombre = node.getString("pro_nombre");
                    String fechaIni = node.getString("pro_fecha_ini");
                    String fechaFin = node.getString("pro_fecha_fin");
                    String lugar = node.getString("pro_lugar");
                    String foto = node.getString("pro_foto");
                    String ponenteNom = node.getString("pon_nombre");
                    String ponenteEmp = node.getString("pon_empresa");
                    String ponentePues = node.getString("pon_puesto");

                    Horario h =
                            new Horario(id, fechaIni, fechaFin, lugar, foto,
                            ponenteNom, ponenteEmp, ponentePues);

                    horario.add(h);
                }

                programa.add(horario);

            }

            insertados = 0;

            if ( programa.size() > 0 ) {
                insertados = programa.size();
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
        if(programa == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            ViewPager paginador = (ViewPager)
                    ((Activity) mContext).findViewById(R.id.programa_contenedor); // id del ViewPager
            ProgramasAdapter adapter =
                    new ProgramasAdapter((
                            (AppCompatActivity) mContext).getSupportFragmentManager(), programa);
            paginador.setAdapter(adapter);


            ProgramaActivity activity = (ProgramaActivity) mContext;
            activity.dibujarPaginas(programa.size());

        }

    }

}
