package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.DatosFacturaAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Model.Compania;
import com.vinidsl.navigationviewdemo.Model.DatosFactura;
import com.vinidsl.navigationviewdemo.Model.Usuario;
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
 * Created by JoseRogelio on 15/08/2015.
 */
public class ConsultaPerfilTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = ConsultaPerfilTask.class.getSimpleName();
    private final String SERVICE_ID = "307";

    private final Context mContext;
    private ProgressDialog mDialog;
    private Usuario usuario;
    private Compania compania;
    private ArrayList<DatosFactura> datosFacturas;
    private int insertados;

    public ConsultaPerfilTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {

        try {

            JSONObject mainNode = new JSONObject(JsonStr);

            datosFacturas = new ArrayList<DatosFactura>();

            //usuario:{usu_mail, usu_psw, usu_nom, usu_cargo, usu_telefono}, compania:{ com_nom, com_calle,
            //        com_numext, com_numint, com_colonia, com_cp, com_mun, com_edo, pais_id, pais_desc, com_tel,
            //        com_www, com_mail, com_foto, act_id, act_desc}, datosf: [{df_id, df_rfc, df_rs, df_calle, df_numint,
            //        df_numext, df_colonia, df_cp, df_mun, df_edo}, {}, {}]

            JSONObject userNode = mainNode.getJSONObject("usuario");
            JSONObject companyNode = mainNode.getJSONObject("compania");
            JSONArray mainArray = mainNode.getJSONArray("datosf");

            String nombre = userNode.getString("usu_nom");
            String mail = userNode.getString("usu_mail");
            String cargo = userNode.getString("usu_cargo");
            String contrasenia = userNode.getString("usu_psw");
            String telefono = userNode.getString("usu_telefono");

            String nombreCom = companyNode.getString("com_nom");
            String calleCom = companyNode.getString("com_calle");
            String numExt = companyNode.getString("com_numext");
            String numInt = companyNode.getString("com_numint");
            String colonia = companyNode.getString("com_colonia");
            String codigoPos = companyNode.getString("com_cp");
            String municipio = companyNode.getString("com_mun");
            String estado = companyNode.getString("com_edo");
            long paisId;
            try {
                paisId = companyNode.getLong("pais_id");
            } catch (JSONException e) {
                paisId = 0;
            }
            String paisDesc = companyNode.getString("pais_desc");
            String telefonoCom = companyNode.getString("com_tel");
            String urlSitio = companyNode.getString("com_www");
            String mailCom = companyNode.getString("com_mail");
            String pathFoto = companyNode.getString("com_foto");
            long actId;
            try {
                actId = companyNode.getLong("act_id");
            } catch (JSONException e) {
                actId = 0;
            }
            String actDesc = companyNode.getString("act_desc");

            for(int i = 0; i < mainArray.length(); i++) {

                JSONObject node = mainArray.getJSONObject(i);

                long idArr = node.getLong("df_id");
                String rfc = node.getString("df_rfc");
                String rs = node.getString("df_rs");
                String calleArr = node.getString("df_calle");
                String numIntArr = node.getString("df_numint");
                String numExtArr = node.getString("df_numext");
                String coloniaArr = node.getString("df_colonia");
                String cpArr = node.getString("df_cp");
                String munArr = node.getString("df_mun");
                String edoArr = node.getString("df_edo");

                DatosFactura d =
                        new DatosFactura(idArr, rfc, rs, calleArr, numIntArr, numExtArr, coloniaArr,
                                cpArr, munArr, edoArr);

                datosFacturas.add(d);

            }

            usuario = new Usuario(nombre, mail, contrasenia, cargo, telefono);
            compania = new Compania(paisId, actId, nombreCom, calleCom, numExt, numInt, colonia,
                codigoPos, municipio, estado, paisDesc, telefonoCom, urlSitio, mailCom, pathFoto,
                    actDesc);


            insertados = 0;

            if ( usuario != null ) {
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
        if(usuario == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            AppCompatActivity a = (AppCompatActivity) mContext;
            ViewPager contenedor = (ViewPager) a.findViewById(R.id.perfil_df_contenedor);
            TextView correoTV = (TextView) a.findViewById(R.id.perfil_correo);
            TextView contraseniaTV = (TextView) a.findViewById(R.id.perfil_contrasenia);
            TextView confContraseniaTV = (TextView) a.findViewById(R.id.perfil_conf_contrasenia);
            TextView nombreTV = (TextView) a.findViewById(R.id.perfil_nombre);
            TextView cargoTV = (TextView) a.findViewById(R.id.perfil_cargo);
            TextView telefonoTV = (TextView) a.findViewById(R.id.perfil_telefono);
            TextView nombreComTV = (TextView) a.findViewById(R.id.perfil_nombre_comp);
            TextView calleComTV = (TextView) a.findViewById(R.id.perfil_calle_comp);
            TextView numeroExtComTV = (TextView) a.findViewById(R.id.perfil_num_ext);
            TextView numeroIntComTV = (TextView) a.findViewById(R.id.perfil_num_int);
            TextView cpComTV = (TextView) a.findViewById(R.id.perfil_codigo_comp);
            TextView coloniaComTV = (TextView) a.findViewById(R.id.perfil_colonia_comp);
            TextView municipioComTV = (TextView) a.findViewById(R.id.perfil_municipio);
            TextView estadoComTV = (TextView) a.findViewById(R.id.perfil_estado);
            TextView paisComTV = (TextView) a.findViewById(R.id.perfil_pais);
            TextView sitioComTV = (TextView) a.findViewById(R.id.perfil_pagina);
            TextView actComTV = (TextView) a.findViewById(R.id.perfil_actividad);
            /*TextView rfcTV = (TextView) a.findViewById(R.id.perfil_rfc);
            TextView calleFacTV = (TextView) a.findViewById(R.id.perfil_calle);
            TextView coloniaFacTV = (TextView) a.findViewById(R.id.perfil_colonia);
            TextView delegacionFacTV = (TextView) a.findViewById(R.id.perfil_delegacion);
            TextView cpFacTV = (TextView) a.findViewById(R.id.perfil_codigo_postal);*/

            correoTV.setText(usuario.getMail());
            contraseniaTV.setText(usuario.getContrasenia());
            confContraseniaTV.setText(usuario.getContrasenia());
            nombreTV.setText(usuario.getNombre());
            cargoTV.setText(usuario.getCargo());
            telefonoTV.setText(usuario.getTelefono());
            nombreComTV.setText(compania.getNombre());
            calleComTV.setText(compania.getCalle());
            numeroExtComTV.setText(compania.getNumExt());
            numeroIntComTV.setText(compania.getNumInt());
            cpComTV.setText(compania.getCodigoPostal());
            coloniaComTV.setText(compania.getColonia());
            municipioComTV.setText(compania.getMunicipio());
            estadoComTV.setText(compania.getEstado());
            paisComTV.setText(compania.getPaisDesc());
            sitioComTV.setText(compania.getUrlSitio());
            actComTV.setText(compania.getActDesc());

            Log.i("TAMAÑo", " - " + datosFacturas.size());

            if(datosFacturas.size() > 0) {

                contenedor.setVisibility(View.VISIBLE);

                DatosFacturaAdapter adapter =
                        new DatosFacturaAdapter(a.getSupportFragmentManager(), datosFacturas);

                contenedor.setAdapter(adapter);

                /*rfcTV.setText(datosFacturas.get(0).getRfc());
                calleFacTV.setText(datosFacturas.get(0).getCalle());
                coloniaFacTV.setText(datosFacturas.get(0).getColonia());
                delegacionFacTV.setText(datosFacturas.get(0).getMunicipio());
                cpFacTV.setText(datosFacturas.get(0).getCodigoPostal());*/
            } else {

                contenedor.setVisibility(View.GONE);
            }

        }

    }

}
