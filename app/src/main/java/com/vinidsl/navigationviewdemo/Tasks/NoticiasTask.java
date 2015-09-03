package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.NoticiasAdapter;
import com.vinidsl.navigationviewdemo.Adapter.PatrocinadoresAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.Noticia;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.NoticiaActivity;
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
import java.util.regex.Pattern;

/**
 * Created by JoseRogelio on 15/08/2015.
 */
public class NoticiasTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = NoticiasTask.class.getSimpleName();
    private final String SERVICE_ID = "322";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<Noticia> noticias;
    private int insertados;

    public NoticiasTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {

        Log.i(LOG_TAG, JsonStr);

        try {
            noticias = new ArrayList<Noticia>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("noticias"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

            for(int i = 0; i < mainArray.length(); i++) {

                // noticias:[{not_id, not_titulo, not_desc, not_imagen}, {}, {}]

                JSONObject node = mainArray.getJSONObject(i);

                long id = node.getLong("not_id");
                String titulo = node.getString("not_titulo");
                String desc = node.getString("not_desc");
                String pathFoto = node.getString("not_imagen");
                String fecha = node.getString("not_insert");

                Noticia n =
                        new Noticia(id, titulo, fecha, pathFoto, desc);

                noticias.add(n);

            }

            noticias =  arreglarLista(noticias);

            insertados = 0;

            if ( noticias.size() > 0 ) {
                insertados = noticias.size();
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
        if(noticias == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            ListView lista = (ListView)
                    ((Activity) mContext).findViewById(R.id.noticias_lista); // id del ListView
            final NoticiasAdapter adapter =
                    new NoticiasAdapter((Activity) mContext, noticias);
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*Noticia n = adapter.getItem(position);

                    Log.i(LOG_TAG, "id -> " + n.getId());

                    if(n.getId() != -1) {

                        Intent activity = new Intent(mContext, NoticiaActivity.class);
                        activity.putExtra("id", n.getId());
                        activity.putExtra("titulo", n.getTitulo());
                        activity.putExtra("desc", n.getContenido());
                        activity.putExtra("fecha", n.getFecha());
                        activity.putExtra("pathFoto", n.getPathFoto());

                        mContext.startActivity(activity);
                    }*/
                }
            });

        }

    }

    /**
     * metodo para ordenar la lista añadiendo  cabeceras
     * @param lista lista original
     * @return lista ordenada con cabeceras
     */
    private ArrayList<Noticia> arreglarLista(ArrayList<Noticia> lista) {
        ArrayList<Noticia> listaOrden = new ArrayList<Noticia>();
        ArrayList<Noticia> listaSNOrden = lista;

        int indiceListaOrdenada = 0;
        for(int i = 0; i < listaSNOrden.size() ; i++) {
            if(i == 0) {

                // se agrega la cabecera
                Noticia n = new Noticia(-1 ,
                        obtNombreMes(listaSNOrden.get(i).getFecha()),
                        "Ir a sitio",
                        "", "");
                listaOrden.add(n);
                indiceListaOrdenada = 0;

            } else {

                if(obtMes(listaSNOrden.get(i).getFecha())
                        < obtMes(listaOrden.get(indiceListaOrdenada).getFecha())) {

                    // se agrega la cabecera
                    Noticia n = new Noticia(-1 ,
                            obtNombreMes(listaSNOrden.get(i).getFecha()),
                            "Ir a sitio",
                            "", "");
                    listaOrden.add(n);

                }
            }

            listaOrden.add(listaSNOrden.get(i));
            indiceListaOrdenada++;

        }
        return listaOrden;
    }

    private String obtNombreMes(String fechaStr) {
        int mes = 1;
        // fechaYHORA = [{2015-09-01}, {14:16:01.0}]
        String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaYHora[0].split(Pattern.quote("-"));
        mes = Integer.parseInt(fecha[1]);
        switch(mes) {
            case 1:
                return "Enero " + fecha[0];
            case 2:
                return "Febrero " + fecha[0];
            case 3:
                return "Marzo " + fecha[0];
            case 4:
                return "Abril " + fecha[0];
            case 5:
                return "Mayo " + fecha[0];
            case 6:
                return "Junio " + fecha[0];
            case 7:
                return "Julio " + fecha[0];
            case 8:
                return "Agosto " + fecha[0];
            case 9:
                return "Septiembre " + fecha[0];
            case 10:
                return "Octubre " + fecha[0];
            case 11:
                return "Noviembre " + fecha[0];
            case 12:
                return "Diciembre " + fecha[0];
            default:
                return "Enero 2015";
        }
    }

    /**
     * metodo para obtener el mes de una fecha con el formato 2015-09-01 14:16:01.0
     * @param fechaStr cadena que contiene la fecha
     * @return valor del mes (1-12)
     */
    private int obtMes(String fechaStr) {
        int mes = 1;
        // fechaYHORA = [{2015-09-01}, {14:16:01.0}]
        String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaYHora[0].split(Pattern.quote("-"));
        mes = Integer.parseInt(fecha[1]);
        return mes;
    }

}
