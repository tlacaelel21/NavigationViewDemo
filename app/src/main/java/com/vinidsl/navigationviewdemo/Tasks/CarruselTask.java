package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.CarruselAdapter;
import com.vinidsl.navigationviewdemo.Adapter.FeriasIntAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Evento;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.TopModel;
import com.vinidsl.navigationviewdemo.Pantalla_Princi;
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
 * Created by root on 24/08/15.
 */

public class CarruselTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = CarruselTask.class.getSimpleName();
    private final String SERVICE_ID = "301";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<FeriaIntModel> feriaListado;
    private int insertados;
    private ArrayList<CarruselModel> carrusel;
    private Fragment fragmento;

    public CarruselTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {
        try {
            feriaListado = new ArrayList<FeriaIntModel>();
            carrusel = new ArrayList<CarruselModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("abajo"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo
            JSONArray carruselArray = mainNode.getJSONArray("carrusel"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

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

            for(int i = 0; i < carruselArray.length(); i++) {

                // programa:[[{pro_id, pro_nombre, pro_fecha_ini, pro_fecha_fin, pro_fecha_ini, pro_fecha_fin, pro_lugar, pro_foto, pon_nombre,
                //pon_empresa, pon_puesto}, {}, {}], [dia2], [dia3]]

                /*JSONArray innerArray2 = mainArray.getJSONArray(i);

                ArrayList<CarruselModel> imagenesCarrusel = new ArrayList<CarruselModel>();

                for(int j = 0; j < innerArray2.length(); j++) {*/
                    JSONObject node = carruselArray.getJSONObject(i);

                    /*long id = node.getLong("pro_id");
                    String nombre = node.getString("pro_nombre");
                    String fechaIni = node.getString("pro_fecha_ini");
                    String fechaFin = node.getString("pro_fecha_fin");
                    String lugar = node.getString("pro_lugar");
                    String foto = node.getString("pro_foto");
                    String ponenteNom = node.getString("pon_nombre");
                    String ponenteEmp = node.getString("pon_empresa");
                    String ponentePues = node.getString("pon_puesto");*/

                    long id = node.getLong("int_id");
                    String nombre = "NOMBRE";
                    String fechaIni = "inicio";
                    String fechaFin = "fin";
                    String lugar = "lugar";
                    String foto = node.getString("int_foto");
                    String ponenteNom = node.getString("int_titulo");
                    String ponenteEmp = node.getString("pais_desc")+" ,"+node.get("int_lugar");
                    String ponentePues = "puesto";

                    CarruselModel h =
                            new CarruselModel(id, fechaIni, fechaFin, lugar, foto,
                                    ponenteNom, ponenteEmp, ponentePues);

                    //imagenesCarrusel.add(h);
                //}

                carrusel.add(h);

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

            Activity actividad= (Activity)mContext;

            ListView lista = (ListView)actividad.findViewById(R.id.listadoFerias);


            /*FeriasIntAdapter adapter = new FeriasIntAdapter(actividad, R.layout.ferias_item,
                    feriaListado);
            lista.setAdapter(adapter);*/

            /************ Para el carrusel ******************/
            ViewPager paginador = (ViewPager)
                    actividad.findViewById(R.id.fotos_contenedor); // id del ViewPager
            final CarruselAdapter adapterCarrusel =
                    new CarruselAdapter((
                            (AppCompatActivity) mContext).getSupportFragmentManager(), carrusel);
            paginador.setAdapter(adapterCarrusel);

            Pantalla_Princi activity = (Pantalla_Princi) fragmento;
            //activity.dibujarPaginas(carrusel.size());
            activity.dibujarPaginas(carrusel.size(), mContext);
           // paginador.onTouchEvent()

            /*paginador.setOnItemClickListener(new View.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });*/
        }

    }

    public void setFragment(Fragment f){
        fragmento = f;
    }

}
