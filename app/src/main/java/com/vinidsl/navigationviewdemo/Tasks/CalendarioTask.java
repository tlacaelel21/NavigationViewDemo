package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.AdapterCalendario;
import com.vinidsl.navigationviewdemo.Adapter.AdapterFeriasNac;
import com.vinidsl.navigationviewdemo.CalendarioFragment;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Ferias_Nac;
import com.vinidsl.navigationviewdemo.Model.CalendarioModel;
import com.vinidsl.navigationviewdemo.Model.CalendarioModel;
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
 * Created by tlacaelel21 on 6/10/15.
 */
public class CalendarioTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = CalendarioTask.class.getSimpleName();
    private final String SERVICE_ID = "327";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<CalendarioModel> calendarioListado;
    private int insertados;

    public CalendarioTask(Context context) {
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
                    if(st_titulo.equals("liga calendario")){
                        calendarioM =new CalendarioModel(st_titulo,st_url);
                        calendarioListado.add(calendarioM);
                        i=mainArray.length();
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
        if(calendarioListado == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            ListView lista = (ListView)
                    ((Activity) mContext).findViewById(R.id.listadoCalendario); // id del ListView
            final AdapterCalendario adapter =
                    new AdapterCalendario((Activity) mContext, calendarioListado);
            lista.setAdapter(adapter);

            /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CalendarioModel n = adapter.getItem(position);

                    Intent activity = new Intent(mContext, CalendarioFragment.class);
                    activity.putExtra("id", n.getId());
                    activity.putExtra("titulo", n.getTitulo());
                    activity.putExtra("desc", n.getUrlExterno());
                    mContext.startActivity(activity);
                }
            });*/
            /*Activity acitividad= (Activity)mContext;

            ListView lista = (ListView)acitividad.findViewById(R.id.listadoFerias);

            FeriasIntAdapter adapter = new FeriasIntAdapter(acitividad, R.layout.ferias_item,
                    calendarioListado);
            lista.setAdapter(adapter);*/
            /*FeriasIntAdapter adapter =
                    new FeriasIntAdapter(mContext, );*/
            //paginador.setAdapter(adapter);
            /*ViewPager paginador = (ViewPager)
                    ((Activity) mContext).findViewById(R.id.calendarioListado_contenedor); // id del ViewPager
            ProgramasAdapter adapter =
                    new ProgramasAdapter((
                            (AppCompatActivity) mContext).getSupportFragmentManager(), calendarioListado);
            paginador.setAdapter(adapter);


            ProgramaActivity activity = (ProgramaActivity) mContext;
            activity.dibujarPaginas(calendarioListado.size());*/

        }

    }

}
