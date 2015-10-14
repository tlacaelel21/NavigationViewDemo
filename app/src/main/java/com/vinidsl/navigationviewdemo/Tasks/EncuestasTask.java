package com.vinidsl.navigationviewdemo.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.DatosFacturaAdapter;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.EncuestaActivity;
import com.vinidsl.navigationviewdemo.Model.Compania;
import com.vinidsl.navigationviewdemo.Model.DatosFactura;
import com.vinidsl.navigationviewdemo.Model.Pregunta;
import com.vinidsl.navigationviewdemo.Model.Respuesta;
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
public class EncuestasTask extends AsyncTask<String, Void, Void> {

    private final static String TIPO_INPUT_TEXT = "3";
    private final static String TIPO_COMBO_BOX = "4";
    private final static String TIPO_CHECK_BOX = "2";
    private final static String TIPO_MULTI_CHECK = "1";
    private final static String TIPO_PICKER = "0";

    private final String LOG_TAG = EncuestasTask.class.getSimpleName();
    private final String SERVICE_ID = "324";

    private final Context mContext;
    private ProgressDialog mDialog;
    private ArrayList<Pregunta> preguntas;
    private int insertados;

    public EncuestasTask(Context context) {
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {

        try {

            JSONObject mainNode = new JSONObject(JsonStr);

            // preguntas:[{preg_id, preg_caption, preg_tipo, respuestas[{resp_id,resp_caption}]}]

            JSONArray mainArray = mainNode.getJSONArray("preguntas");

            preguntas = new ArrayList<Pregunta>();

            for(int i = 0; i < mainArray.length(); i++) {

                JSONObject node = mainArray.getJSONObject(i);

                long id = node.getLong("id");
                String caption = node.getString("texto_esp");
                String captionIng = node.getString("texto_ing");
                String tipo = node.getString("tipo");


                JSONArray innerArray = node.getJSONArray("respuestas");

                ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();

                for (int j = 0; j< innerArray.length(); j++) {
                    JSONObject innerNode = innerArray.getJSONObject(j);
                    long idArr = innerNode.getInt("id");
                    String resCaption = innerNode.getString("texto_esp");
                    String resCaptionIng = innerNode.getString("texto_ing");
                    respuestas.add(new Respuesta(idArr, resCaption + " / " +
                        resCaptionIng));
                }

                Pregunta p =
                        new Pregunta(id, caption + " / " + captionIng, tipo, respuestas);

                preguntas.add(p);

            }

            insertados = 0;

            if ( preguntas.size() > 0 ) {
                insertados = preguntas.size();
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

            Log.i(LOG_TAG, builtUri.toString());

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
                forecastJsonStr = "{\"preguntas\" : [" +
                        "{\"preg_id\": 1 , \"preg_caption\": \"pregunta 1\", \"preg_tipo\": \"1\"," +
                        " \"respuestas\": []}," +
                        "{\"preg_id\": 2 , \"preg_caption\": \"pregunta 2\", \"preg_tipo\": \"2\"," +
                        " \"respuestas\": [" +
                            "{\"resp_id\" : 1, \"resp_caption\":\"respuesta 1\"}," +
                            "{\"resp_id\" : 2, \"resp_caption\":\"respuesta 2\"}," +
                            "{\"resp_id\" : 1, \"resp_caption\":\"respuesta 3\"}" +
                        "]}" +
                        "]}";
                populateList(forecastJsonStr);
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                forecastJsonStr = "{\"preguntas\" : [" +
                        "{\"preg_id\": 1 , \"preg_caption\": \"pregunta 1\", \"preg_tipo\": \"1\"," +
                        " \"respuestas\": []}," +
                        "{\"preg_id\": 2 , \"preg_caption\": \"pregunta 2\", \"preg_tipo\": \"2\"," +
                        " \"respuestas\": [" +
                        "{\"resp_id\" : 1, \"resp_caption\":\"respuesta 1\"}," +
                        "{\"resp_id\" : 2, \"resp_caption\":\"respuesta 2\"}," +
                        "{\"resp_id\" : 1, \"resp_caption\":\"respuesta 3\"}" +
                        "]}" +
                        "]}";
                populateList(forecastJsonStr);
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
        if(preguntas == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            EncuestaActivity a = (EncuestaActivity) mContext;
            LinearLayout contenedor =
                    (LinearLayout) a.findViewById(R.id.encuesta_preguntas_container);

            for(int i = 0; i<preguntas.size() ; i++) {

                TextView caption = new TextView(mContext);
                caption.setText(preguntas.get(i).getCaption());
                contenedor.addView(caption);

                LinearLayout.LayoutParams params;

                switch(preguntas.get(i).getTipo()) {
                    case TIPO_INPUT_TEXT:
                        EditText inputET = new EditText(mContext);
                        inputET.setHint("Respuesta");
                        inputET.setTextSize(16);
                        contenedor.addView(inputET);
                        params = (LinearLayout.LayoutParams) inputET.getLayoutParams();
                        params.topMargin = 20;
                        params.bottomMargin = 30;
                        params.height = 80;
                        inputET.setLayoutParams(params);
                        break;
                    case TIPO_CHECK_BOX:
                        RadioGroup group = new RadioGroup(mContext);
                        contenedor.addView(group);
                        ArrayList<Respuesta> respuestasG = preguntas.get(i).getRespuestas();
                        for(int j = 0; j < respuestasG.size(); j++) {
                            RadioButton button = new RadioButton(mContext);
                            button.setText(respuestasG.get(j).getRespuesta());
                            button.setId(j);
                            group.addView(button);
                        }
                        break;
                    case TIPO_MULTI_CHECK:

                        break;
                    case TIPO_COMBO_BOX:
                        Spinner inputSP = new Spinner(mContext);
                        ArrayList<Respuesta> respuestas = preguntas.get(i).getRespuestas();
                        ArrayList<String> respuestasTexto = new ArrayList<String>();
                        for(int j = 0; j < respuestas.size(); j++) {
                            respuestasTexto.add(respuestas.get(j).getRespuesta());
                        }
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(mContext,
                                        android.R.layout.simple_expandable_list_item_1,
                                        respuestasTexto);
                        inputSP.setAdapter(adapter);
                        contenedor.addView(inputSP);
                        params = (LinearLayout.LayoutParams) inputSP.getLayoutParams();
                        params.topMargin = 20;
                        params.bottomMargin = 30;
                        params.height = 80;
                        inputSP.setLayoutParams(params);
                        break;
                    case TIPO_PICKER:

                        break;
                }

            }

            a.llenaLista(preguntas);

        }

    }

}
