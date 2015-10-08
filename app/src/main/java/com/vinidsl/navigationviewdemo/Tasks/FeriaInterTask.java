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
import com.vinidsl.navigationviewdemo.Evento;
import com.vinidsl.navigationviewdemo.Ferias_Int;
import com.vinidsl.navigationviewdemo.Model.EventoModel;
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
import java.util.regex.Pattern;


public class FeriaInterTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FeriaInterTask.class.getSimpleName();
    private final String SERVICE_ID = "302";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<FeriaIntModel> feriaListado;
    private int insertados;

    public FeriaInterTask(Context context) {
        mContext = context;
    }
    int mes=0;
    private void populateList(String JsonStr)
            throws JSONException {
        try {
            feriaListado = new ArrayList<FeriaIntModel>();
            JSONObject mainNode = new JSONObject(JsonStr);
            JSONArray mainArray = mainNode.getJSONArray("calendario"); // este método extrae un arreglo de JSON con el nombre de llave_arreglo

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
                //Log.i("FECHA",int_inicio);
                    //mes=Integer.parseInt(int_inicio.substring(5,7));
                //Log.i("MES",""+mes);

                    FeriaIntModel feriasIntM =
                            new FeriaIntModel(id,int_titulo,int_lugar,pais_desc,foto,int_inicio,int_final);
                feriaListado.add(feriasIntM);
            }
            /*ArrayList<FeriaIntModel> aux = new ArrayList<FeriaIntModel>();
            for(int i = 0; i < feriaListado.size(); i++) {
                if(i == 0){

                    FeriaIntModel cab = new FeriaIntModel(-1,"","","","",""+mes ,"");
                    aux.add(cab);
                }else if(Integer.parseInt(aux.get(i-1).getFechaInicio().substring(5,7))!=mes){
                //Log.i("FECHA",aux.get(i-1).getFechaInicio());
                    FeriaIntModel cab = new FeriaIntModel(-1,"","","","",""+mes,"");
                    aux.add(cab);
                }
                aux.add(feriaListado.get(i));
                //if(mes != messiguiente)
            }*/
            feriaListado=arreglarLista(feriaListado);
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
            String parametro = c.encriptar(SERVICE_ID + "|" + "1");
            parametro=parametro.replaceAll("\\+", "%2B");

            //String parametro = c.encriptar(SERVICE_ID);

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, parametro).build();

            Log.i("SERV",""+builtUri);
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
                    //Intent activity = new Intent(mContext, Evento.class);
                    //mContext.startActivity(activity);
                    /*FeriaIntModel n = adapter.getItem(position);

                    Intent activity/ = new Intent(mContext, Ferias_Int.class);
                    activity.putExtra("id", n.getId());
                    activity.putExtra("titulo", n.getNombre());
                    activity.putExtra("desc", n.getPais_desc());
                    activity.putExtra("fecha_ini", n.getFechaInicio());
                    activity.putExtra("fecha_fin", n.getFechaFin());
                    activity.putExtra("pathFoto", n.getFotoInt());

                    mContext.startActivity(activity);*/
                    FeriaIntModel n = adapter.getItem(position);
                    Intent activity = new Intent(mContext, Evento.class);
                    activity.putExtra("idEvento", ""+n.getId() );
                    mContext.startActivity(activity);
                    //Log.i("CLIC","123123");
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

    /**
     * metodo para ordenar la lista añadiendo  cabeceras
     * @param lista lista original
     * @return lista ordenada con cabeceras
     */
    private ArrayList<FeriaIntModel> arreglarLista(ArrayList<FeriaIntModel> lista) {
        ArrayList<FeriaIntModel> listaOrden = new ArrayList<FeriaIntModel>();
        ArrayList<FeriaIntModel> listaSNOrden = lista;

        int indiceListaOrdenada = 0;
        for(int i = 0; i < listaSNOrden.size() ; i++) {
            if(i == 0) {

                //long int_id,String int_titulo, String int_lugar, String pais_desc,
                // String foto_int, String int_inicio, String int_final
                // se agrega la cabecera
                FeriaIntModel n = new FeriaIntModel(-1 ,
                        obtNombreMes(listaSNOrden.get(i).getFecha()),
                        "Mes",
                        "", "","","");
                listaOrden.add(n);
                indiceListaOrdenada = 0;

            } else {

                if(obtMes(listaSNOrden.get(i).getFecha())
                        < obtMes(listaOrden.get(indiceListaOrdenada).getFecha())) {

                    //l(long int_id,String int_titulo, String int_lugar, String pais_desc, String foto_int, String int_inicio, String int_final) {
                    // se agrega la cabecera
                    FeriaIntModel n = new FeriaIntModel(-1 ,
                            obtNombreMes(listaSNOrden.get(i).getFecha()),
                            "Mes",
                            "", "","","");
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
        //String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaStr.split(Pattern.quote("-"));
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
        //String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = [{2015}, {09}, {01}]
        String[] fecha = fechaStr.split(Pattern.quote("-"));
        Log.i("TA","->"+fechaStr);
        Log.i("TA",""+fecha.length);
        if(!fechaStr.equals(""))
           mes = Integer.parseInt(fecha[1]);
        return mes;
    }

}
