package com.vinidsl.navigationviewdemo.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Adapter.AgendaAdapter;
import com.vinidsl.navigationviewdemo.Adapter.FotoRecintoAdapter;
import com.vinidsl.navigationviewdemo.AgendaActivity;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.Cita;
import com.vinidsl.navigationviewdemo.Model.Recinto;
import com.vinidsl.navigationviewdemo.Model.Sala;
import com.vinidsl.navigationviewdemo.R;
import com.vinidsl.navigationviewdemo.RecintoActivity;

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
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by tlacaelel21 on 1/09/15.
 */
public class AgendaTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = AgendaTask.class.getSimpleName();
    private final String SERVICE_ID = "330";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<Sala> salas;
    private ArrayList<Cita> citas;
    private ArrayList<ArrayList<Cita>> citasArrange;
    private String fechaIni;
    private String fechaFin;

    private int insertados;
    long int_id;

    public AgendaTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {


        try {

            /*
            {agenda:{fecha_ini, fecha_fin, salas:[{id_sala, sala_desc},{},...],
            citas:[{id_sala,fecha, id_cita, usuario, confirmado}, {}, ...]}}
             */

            salas = new ArrayList<Sala>();
            citas = new ArrayList<Cita>();

            JSONObject mainNode = new JSONObject(JsonStr);
            JSONObject innerNode = mainNode.getJSONObject("agenda");
            JSONArray innerArrayUno = innerNode.getJSONArray("salas");
            JSONArray innerArrayDos = innerNode.getJSONArray("citas");

            fechaIni = innerNode.getString("fecha_ini");
            fechaFin = innerNode.getString("fecha_fin");

            for(int i = 0; i < innerArrayUno.length() ; i++) {
                JSONObject arrayObj = innerArrayUno.getJSONObject(i);
                String id = arrayObj.getString("id_sala");
                String desc = arrayObj.getString("sala_desc");

                salas.add(new Sala(id,desc));
            }

            for(int i = 0; i < innerArrayDos.length() ; i++) {
                JSONObject arrayObj = innerArrayDos.getJSONObject(i);
                String id = arrayObj.getString("id_cita");
                String fecha = arrayObj.getString("fecha");
                String usuario = arrayObj.getString("usuario");
                String id_sala = arrayObj.getString("id_sala");
                String confirmado = arrayObj.getString("confirmado");

                citas.add(new Cita(id,fecha, usuario, id_sala, confirmado));
            }

            String fechaActual = citas.get(0).getFecha();
            int indiceLista = 0;

            citasArrange =  new ArrayList<ArrayList<Cita>>();
            ArrayList<Cita> c = new ArrayList<Cita>();
            citasArrange.add(c);

            citasArrange.get(indiceLista).add(citas.get(0));

            for(int i = 1; i < citas.size() ; i++) {
                int comparacion = comparaFechas(fechaActual, citas.get(i).getFecha());
                if(comparacion == 0) {
                    citasArrange.get(indiceLista).add(citas.get(i));
                } else if(comparacion == 1) {
                    fechaActual = citas.get(i).getFecha();
                    indiceLista = indiceLista + 1;
                    c = new ArrayList<Cita>();
                    citasArrange.add(c);
                    citasArrange.get(indiceLista).add(citas.get(i));
                }
            }

            insertados = 0;

            if ( citas.size() > 0 ) {
                insertados = 1;
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
            parametro = "VY1r3cyNY2MX7jG5Qn/zkQ==";

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
        if(salas == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {


            AgendaActivity a = (AgendaActivity) mContext;

            TextView salaUnoNom = (TextView) a.findViewById(R.id.agenda_sala_uno);
            TextView salaDosNom = (TextView) a.findViewById(R.id.agenda_sala_dos);
            TextView salaTresNom = (TextView) a.findViewById(R.id.agenda_sala_tres);

            ViewPager pager = (ViewPager) a.findViewById(R.id.agenda_paginador);

            salaUnoNom.setText(salas.get(0).getDesc());
            salaDosNom.setText(salas.get(1).getDesc());
            salaTresNom.setText(salas.get(2).getDesc());

            for(int i = 0; i < citasArrange.size() ; i++) {
                Log.i(LOG_TAG, "----");
                for(int j = 0; j < citasArrange.get(i).size() ; j++) {
                    Log.i(LOG_TAG, citasArrange.get(i).get(j).getFecha());
                }
            }

            AgendaAdapter adapter = new AgendaAdapter(a.getSupportFragmentManager(),
                    citasArrange, salas);
            pager.setAdapter(adapter);

        }

    }

    public int comparaFechas(String fechaAnt, String fechaAct) {
        String[] splitfAnt = fechaAnt.split(" ");
        String[] splitfAct = fechaAct.split(" ");
        String[] splitdAnt = splitfAnt[0].split(Pattern.quote("-"));
        String[] splitdAct = splitfAct[0].split(Pattern.quote("-"));

        Calendar fAnt = Calendar.getInstance();
        Calendar fAct = Calendar.getInstance();

        fAnt.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitdAnt[2]));
        fAnt.set(Calendar.MONTH, Integer.parseInt(splitdAnt[1]));
        fAnt.set(Calendar.YEAR, Integer.parseInt(splitdAnt[0]));
        fAnt.set(Calendar.HOUR_OF_DAY, 0);
        fAnt.set(Calendar.MINUTE, 0);
        fAnt.set(Calendar.SECOND, 0);
        fAnt.set(Calendar.MILLISECOND, 0);

        fAct.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitdAct[2]));
        fAct.set(Calendar.MONTH, Integer.parseInt(splitdAct[1]));
        fAct.set(Calendar.YEAR, Integer.parseInt(splitdAct[0]));
        fAct.set(Calendar.HOUR_OF_DAY, 0);
        fAct.set(Calendar.MINUTE, 0);
        fAct.set(Calendar.SECOND, 0);
        fAct.set(Calendar.MILLISECOND, 0);

        return fAct.compareTo(fAnt);
    }

}

