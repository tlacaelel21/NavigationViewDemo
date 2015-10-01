package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Adapter.FotoRecintoAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Expositores;
import com.vinidsl.navigationviewdemo.InscripcionFI;
import com.vinidsl.navigationviewdemo.Model.EventoModel;
import com.vinidsl.navigationviewdemo.Model.Ponencia;
import com.vinidsl.navigationviewdemo.Model.Recinto;
import com.vinidsl.navigationviewdemo.NoticiasActivity;
import com.vinidsl.navigationviewdemo.PatrocinadoresActivity;
import com.vinidsl.navigationviewdemo.PonentesActivity;
import com.vinidsl.navigationviewdemo.ProgramaActivity;
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

/**
 * Created by tlacaelel21 on 1/09/15.
 */
public class RecintoTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = RecintoTask.class.getSimpleName();
    private final String SERVICE_ID = "309";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<String> fotos;
    private Recinto recinto;
    private int insertados;
    private AQuery aquery;
    long int_id;

    public RecintoTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {


        try {

            /*
            recinto:{rec_titulo, rec_desc, rec_calle, rec_num_ext, rec_num_int,
             rec_colonia,rec_delegacion, rec_ciudad, rec_estado, rec_cp, pais_desc,
              rec_facebook, rec_twitter, rec_telefono, rec_email}, fotos[{rec_foto}, {}, {}]
             */

            fotos = new ArrayList<String>();

            JSONObject mainNode = new JSONObject(JsonStr);
            JSONObject innerNode = mainNode.getJSONObject("recinto");
            JSONArray innerArray = mainNode.getJSONArray("fotos");

            String titulo = innerNode.getString("rec_titulo");
            String desc = innerNode.getString("rec_desc");
            String calle = innerNode.getString("rec_calle");
            String ext = innerNode.getString("rec_num_ext");
            String numInt = innerNode.getString("rec_num_int");
            String colonia = innerNode.getString("rec_colonia");
            String delegacion = innerNode.getString("rec_delegacion");
            String ciudad = innerNode.getString("rec_ciudad");
            String estado = innerNode.getString("rec_estado");
            String cp = innerNode.getString("rec_cp");
            String pais = innerNode.getString("pais_desc");
            String fb = innerNode.getString("rec_facebook");
            String tw = innerNode.getString("rec_twitter");
            String tel = innerNode.getString("rec_telefono");
            String email = innerNode.getString("rec_email");

            recinto = new Recinto(titulo, desc, calle, ext, numInt, colonia, delegacion, ciudad,
                    estado, cp, pais, fb, tw, tel, email);

            for(int i = 0; i < innerArray.length() ; i++) {
                JSONObject arrayObj = innerArray.getJSONObject(i);
                fotos.add(arrayObj.getString("rec_foto"));
            }

            insertados = 0;

            if ( recinto != null ) {
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
        if(recinto == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            aquery = new AQuery(mContext);
            RecintoActivity a = (RecintoActivity) mContext;

            TextView tituloRec = (TextView) a.findViewById(R.id.recinto_nombre);
            TextView lugarRec = (TextView) a.findViewById(R.id.recinto_lugar);
            TextView ubicacionRec = (TextView) a.findViewById(R.id.recinto_ubicacion);
            TextView telRec = (TextView) a.findViewById(R.id.recinto_tel);
            TextView emailRec = (TextView) a.findViewById(R.id.recinto_mail);
            TextView fbRec = (TextView) a.findViewById(R.id.recinto_fb);
            TextView twRec = (TextView) a.findViewById(R.id.recinto_tw);
            TextView descRec = (TextView) a.findViewById(R.id.recinto_desc);
            ViewPager pager = (ViewPager) a.findViewById(R.id.recinto_fotos_paginador);

            tituloRec.setText(recinto.getTitulo());
            lugarRec.setText(recinto.getEstado() + ", " + recinto.getPais());
            ubicacionRec.setText(recinto.getCalle() + " " +
                                recinto.getNumExt() + " " +
                                recinto.getNumInt() + "\n" +
                                recinto.getColonia() + " " +
                                recinto.getCp() + "\n" +
                                recinto.getDelegacion() + "\n\n" +
                                recinto.getEstado() + "\n" +
                                recinto.getPais());

            descRec.setText(recinto.getDescripcion());

            String tel = recinto.getTelefono();
            String mail = recinto.getEmail();
            String fb = recinto.getFacebook();
            String tw = recinto.getTwitter();
            if(tel !=  null && !tel.isEmpty())
                telRec.setText(a.getString(R.string.recinto_tel) + " " + tel);
            else telRec.setVisibility(View.GONE);
            if(mail !=  null && !mail.isEmpty())
                emailRec.setText(a.getString(R.string.recinto_mail) + " " + mail);
            else emailRec.setVisibility(View.GONE);
            if(fb !=  null && !fb.isEmpty())
                fbRec.setText(a.getString(R.string.recinto_fb) + " " + fb);
            else fbRec.setVisibility(View.GONE);
            if(tw !=  null && !tw.isEmpty())
                twRec.setText(a.getString(R.string.recinto_tw) + " " + tw);
            else twRec.setVisibility(View.GONE);

            FotoRecintoAdapter adapter = new FotoRecintoAdapter(a.getSupportFragmentManager(),
                    fotos);
            pager.setAdapter(adapter);

            a.dibujarPaginas(fotos.size());

        }

    }

}

