package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.RegistroModel;
import com.vinidsl.navigationviewdemo.R;
import com.vinidsl.navigationviewdemo.Registro2;

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
 * Created by tlacaelel21 on 14/10/15.
 */
public class TaskRegistroActividades extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = TaskRegistroActividades.class.getSimpleName();
    private final String SERVICE_ID = "335";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<RegistroModel> registroListado;
    private int insertados;
    long valoresActi[];
    String idsP;
    Registro2 miFrag;

    ArrayList<String> actividades_des=new ArrayList<String>();

    public TaskRegistroActividades(Context context) {
        mContext = context;
    }
    int mes=0;
    private void populateList(String JsonStr)
            throws JSONException {
        try {
            registroListado = new ArrayList<RegistroModel>();

            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("actividades"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

            valoresActi=new long[mainArray.length()];
            for(int i = 0; i < mainArray.length(); i++) {
                JSONObject node = mainArray.getJSONObject(i);;
                String desc = node.getString("desc");
                long id_pais = node.getLong("id");
                RegistroModel calendarioM=new RegistroModel(id_pais,desc);
                registroListado.add(calendarioM);
                actividades_des.add(desc);
                valoresActi[i]= id_pais;
                idsP=idsP+id_pais+",";
            }
            insertados = 0;

            if ( registroListado.size() > 0 ) {
                insertados = registroListado.size();
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
            Log.i("SERV_A",parametro);

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
        if(registroListado == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            Activity a = (Activity) mContext;
            Spinner paises=(Spinner)a.findViewById(R.id.spinnerActi);

            miFrag.addItemsOnSpinnerAct(paises,mContext,actividades_des);
            miFrag.setValoresAct(valoresActi);


            /*Spinner inputSP = new Spinner(mContext);
            ArrayList<String> respuestas = new ArrayList<String>();

            for(int i = 0; i<registroListado.size() ; i++) {
                Respuesta[] r = registroListado.get(i).getRespuestas();

            }*/
            //RegistroModel registroModel=new RegistroModel();
            /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RegistroModel n = adapter.getItem(position);

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
                    registroListado);
            lista.setAdapter(adapter);*/
            /*FeriasIntAdapter adapter =
                    new FeriasIntAdapter(mContext, );*/
            //paginador.setAdapter(adapter);
            /*ViewPager paginador = (ViewPager)
                    ((Activity) mContext).findViewById(R.id.registroListado_contenedor); // id del ViewPager
            ProgramasAdapter adapter =
                    new ProgramasAdapter((
                            (AppCompatActivity) mContext).getSupportFragmentManager(), registroListado);
            paginador.setAdapter(adapter);


            ProgramaActivity activity = (ProgramaActivity) mContext;
            activity.dibujarPaginas(registroListado.size());*/

        }

    }
    public void setFragment(Registro2 frag) {
        miFrag=frag;
    }

    public long getId(int opc){
        return valoresActi[opc];
    }

}

