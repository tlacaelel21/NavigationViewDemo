package com.vinidsl.navigationviewdemo.Tasks;

/**
 * Created by root on 24/08/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.FeriasIntAdapter;
import com.vinidsl.navigationviewdemo.Adapter.NoticiasAdapter;
import com.vinidsl.navigationviewdemo.Adapter.ProgramasAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Ferias_Int;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.Noticia;
import com.vinidsl.navigationviewdemo.NoticiaActivity;
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


public class FeriaInterTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FeriaInterTask.class.getSimpleName();
    private final String SERVICE_ID = "301";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<FeriaIntModel> feriaListado;
    private int insertados;

    public FeriaInterTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {
        try {
            feriaListado = new ArrayList<FeriaIntModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("abajo"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

            for(int i = 0; i < mainArray.length(); i++) {

                    JSONObject node = mainArray.getJSONObject(i);
                    long id = node.getLong("int_id");
                   // String int_foto = node.getString("int_foto");
                    String pais_desc = node.getString("pais_desc");
                    String int_lugar = node.getString("int_lugar");
                    String int_titulo = node.getString("int_titulo");
                    String foto = node.getString("int_foto");
                    String int_final = node.getString("int_final");
                    String int_inicio = node.getString("int_inicio");

                    FeriaIntModel feriasIntM =
                            new FeriaIntModel(id,int_titulo,int_lugar,pais_desc,foto,int_inicio,int_final);
                feriaListado.add(feriasIntM);
            }

            insertados = 0;

            if ( feriaListado.size() > 0 ) {
                insertados = feriaListado.size();
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
            String parametro = c.encriptar(SERVICE_ID);

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
        if(feriaListado == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            ListView lista = (ListView)
                    ((Activity) mContext).findViewById(R.id.listadoFerias); // id del ListView
            final FeriasIntAdapter adapter =
                    new FeriasIntAdapter((Activity) mContext, feriaListado);
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FeriaIntModel n = adapter.getItem(position);

                    Intent activity = new Intent(mContext, Ferias_Int.class);
                    activity.putExtra("id", n.getId());
                    activity.putExtra("titulo", n.getNombre());
                    activity.putExtra("desc", n.getPais_desc());
                    activity.putExtra("fecha_ini", n.getFechaInicio());
                    activity.putExtra("fecha_fin", n.getFechaFin());
                    activity.putExtra("pathFoto", n.getFotoInt());

                    mContext.startActivity(activity);
                }
            });
            /*Activity acitividad= (Activity)mContext;

            ListView lista = (ListView)acitividad.findViewById(R.id.listadoFerias);

            FeriasIntAdapter adapter = new FeriasIntAdapter(acitividad, R.layout.ferias_item,
                    feriaListado);
            lista.setAdapter(adapter);*/
            /*FeriasIntAdapter adapter =
                    new FeriasIntAdapter(mContext, );*/
            //paginador.setAdapter(adapter);
            /*ViewPager paginador = (ViewPager)
                    ((Activity) mContext).findViewById(R.id.feriaListado_contenedor); // id del ViewPager
            ProgramasAdapter adapter =
                    new ProgramasAdapter((
                            (AppCompatActivity) mContext).getSupportFragmentManager(), feriaListado);
            paginador.setAdapter(adapter);


            ProgramaActivity activity = (ProgramaActivity) mContext;
            activity.dibujarPaginas(feriaListado.size());*/

        }

    }

}
