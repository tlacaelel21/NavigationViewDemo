package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.DatosFacturaAdapter;
import com.vinidsl.navigationviewdemo.Adapter.ProgramasAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.DatosFactura;
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
 * Created by JoseRogelio on 19/08/2015.
 */
public class GuardarDatosFacturaTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = ConsultaPerfilTask.class.getSimpleName();
    private final String SERVICE_ID = "310";

    private final Context mContext;
    private ProgressDialog mDialog;
    private DatosFactura df;
    private int resultado;

    public GuardarDatosFacturaTask(Context context) {
        mContext = context;
    }

    public void setDatoFactura(DatosFactura valores) {
        this.df = valores;
    }

    private void readResult(String JsonStr) throws JSONException {

        try {

            JSONObject mainNode = new JSONObject(JsonStr);

            // insert_df:{0/1}

            Log.i(LOG_TAG, JsonStr);

            resultado = mainNode.getInt("insert_df");

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
                Log.i("ERROR","INPUT NULL");
                resultado = -2;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                Log.i("ERROR","EMPTY BUFFER");
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
                Toast.makeText(mContext, "Error al guardar datos" , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                Activity a = (Activity) mContext;
                ViewPager pager = (ViewPager) a.findViewById(R.id.perfil_df_contenedor);

                if(!pager.isShown()) {
                    pager.setVisibility(View.VISIBLE);
                }

                DatosFacturaAdapter adapter = (DatosFacturaAdapter) pager.getAdapter();
                ArrayList<DatosFactura> lista = adapter.getValues();
                lista.add(df);
                adapter.setValues(lista);
                adapter.notifyDataSetChanged();

            }

        }

    }

}
