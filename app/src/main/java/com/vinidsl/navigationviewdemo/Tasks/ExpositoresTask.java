package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.DocumentosAdapter;
import com.vinidsl.navigationviewdemo.Adapter.ExpositoresAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.ExpositoresModel;
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
 * Created by tlacaelel21 on 29/09/15.
 */
public class ExpositoresTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = ExpositoresTask.class.getSimpleName();
    private final String SERVICE_ID = "323";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<ExpositoresModel> expositoresListado;
    private int insertados;

    //String valorEnc=obj.encriptar("315|17");
    public ExpositoresTask(Context context) {
        mContext = context;
    }
    int mes=0;
    private void populateList(String JsonStr)
            throws JSONException {
        try {
            expositoresListado = new ArrayList<ExpositoresModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("expositores"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo
            //expositores:[{com_nom, com_www, act_desc, com_foto}, {}, {}]
            for(int i = 0; i < mainArray.length(); i++) {
                JSONObject node = mainArray.getJSONObject(i);
                //long id = node.getLong("doc_id");
                String expo_titulo = node.getString("com_nom");
                String expo_url = node.getString("com_www");
                String expo_act = node.getString("act_desc");
                String expo_foto = node.getString("com_foto");
                
                Log.i("SERV",expo_titulo);
                ExpositoresModel expositoresModel =
                        new ExpositoresModel(expo_titulo,expo_url,expo_act,expo_foto);
                expositoresListado.add(expositoresModel);
            }

            insertados = 0;

            if ( expositoresListado.size() > 0 ) {
                insertados = expositoresListado.size();
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
            //Log.i("SERV",parametro);
            parametro=parametro.replaceAll("\\+", "%2B");
            parametro=parametro.replaceAll("\\/", "%2F");

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
        if(expositoresListado == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            ListView lista = (ListView)
                    ((Activity) mContext).findViewById(R.id.listado_expositores); // id del ListView
            Activity activity= (Activity) mContext;
            final ExpositoresAdapter adapter =
                    new ExpositoresAdapter(activity,1,expositoresListado);
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