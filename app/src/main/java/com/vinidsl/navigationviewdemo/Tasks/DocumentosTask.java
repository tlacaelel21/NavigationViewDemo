package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.DocumentosAdapter;
import com.vinidsl.navigationviewdemo.Adapter.MisEventosAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.DocumentosModel;
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
 * Created by tlacaelel21 on 28/09/15.
 */
public class DocumentosTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = DocumentosTask.class.getSimpleName();
    private final String SERVICE_ID = "317";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<DocumentosModel> documentosListado;
    private int insertados;

    //String valorEnc=obj.encriptar("315|17");
    public DocumentosTask(Context context) {
        mContext = context;
    }
    int mes=0;
    private void populateList(String JsonStr)
            throws JSONException {
        try {
            documentosListado = new ArrayList<DocumentosModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("documentos"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo
            //documentos:[{doc_id|doc_url|doc_titulo|doc_visto}, {}, {}]
            for(int i = 0; i < mainArray.length(); i++) {

                JSONObject node = mainArray.getJSONObject(i);
                long id = node.getLong("doc_id");
                String doc_url = node.getString("doc_url");
                String doc_visto = node.getString("doc_visto");
                String doc_titulo = node.getString("doc_titulo");

                DocumentosModel documentosModel =
                        new DocumentosModel(doc_titulo,doc_url,doc_visto);
                documentosListado.add(documentosModel);
            }

            insertados = 0;

            if ( documentosListado.size() > 0 ) {
                insertados = documentosListado.size();
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
        /*SharedPreferences preferencias =
                act.getSharedPreferences(act.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        String idUsuario = preferencias.getString(act.getString(R.string.pref_idusuario), "0");*/
        //String sts_id = preferencias.getString(act.getString(R.string.sts_id), "0");
        //Log.i("USR", "" + idUsuario);

        try {

            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            String parametro = c.encriptar(SERVICE_ID + "|" + params[0]);
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
        if(documentosListado == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            ListView lista = (ListView)
                    ((Activity) mContext).findViewById(R.id.listado_documentos); // id del ListView
            Activity activity= (Activity) mContext;
            final DocumentosAdapter adapter =
                    new DocumentosAdapter(activity,1,documentosListado);
            lista.setAdapter(adapter);

           /* lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent activity = new Intent(mContext, Evento.class);
                    //mContext.startActivity(activity);
                    *//*MisEventosModel n = adapter.getItem(position);

                    Intent activity/ = new Intent(mContext, Ferias_Int.class);
                    activity.putExtra("id", n.getId());
                    activity.putExtra("titulo", n.getNombre());
                    activity.putExtra("desc", n.getubicacion_evento());
                    activity.putExtra("fecha_ini", n.getFechaInicio());
                    activity.putExtra("fecha_fin", n.getFechaFin());
                    activity.putExtra("pathFoto", n.getFotoInt());

                    mContext.startActivity(activity);*//*
                    MisEventosModel n = adapter.getItem(position);
                    Intent activity = new Intent(mContext, Evento.class);
                    //activity.putExtra("idEvento", ""+n.getId() );
                    mContext.startActivity(activity);
                    //Log.i("CLIC","123123");
                }
            });*/
            /*Activity acitividad= (Activity)mContext;

            ListView lista = (ListView)acitividad.findViewById(R.id.listadoFerias);

            FeriasIntAdapter adapter = new FeriasIntAdapter(acitividad, R.layout.ferias_item,
                    misEventosListado);
            lista.setAdapter(adapter);*/
            /*FeriasIntAdapter adapter =
                    new FeriasIntAdapter(mContext, );*/
            //paginador.setAdapter(adapter);
            /*ViewPager paginador = (ViewPager)
                    ((Activity) mContext).findViewById(R.id.misEventosListado_contenedor); // id del ViewPager
            ProgramasAdapter adapter =
                    new ProgramasAdapter((
                            (AppCompatActivity) mContext).getSupportFragmentManager(), misEventosListado);
            paginador.setAdapter(adapter);


            ProgramaActivity activity = (ProgramaActivity) mContext;
            activity.dibujarPaginas(misEventosListado.size());*/

        }

    }

}

