package com.vinidsl.navigationviewdemo.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Cifrado;
import com.vinidsl.navigationviewdemo.Documentos;
import com.vinidsl.navigationviewdemo.Eventos;
import com.vinidsl.navigationviewdemo.Expositores;
import com.vinidsl.navigationviewdemo.Ferias_Int;
import com.vinidsl.navigationviewdemo.InscripcionFI;
import com.vinidsl.navigationviewdemo.Login;
import com.vinidsl.navigationviewdemo.MainActivity;
import com.vinidsl.navigationviewdemo.Model.EventoModel;
import com.vinidsl.navigationviewdemo.Model.Ponencia;
import com.vinidsl.navigationviewdemo.NoticiasActivity;
import com.vinidsl.navigationviewdemo.PatrocinadoresActivity;
import com.vinidsl.navigationviewdemo.PonentesActivity;
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

/**
 * Created by tlacaelel21 on 1/09/15.
 */
public class TaskEvento extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = TaskEvento.class.getSimpleName();
    private final String SERVICE_ID = "303";

    private final Context mContext;
    private ProgressDialog mDialog;
    private EventoModel evento;
    private ArrayList<Ponencia> eventos;
    private int insertados;
    private AQuery aquery;
    Activity act;
    String sts_id="";
    long int_id;
    String idUsuario;
    String inscrito;

    public TaskEvento(Context context) {
        act=(Activity)context;
        mContext = context;
    }

    private void populateList(String JsonStr)
            throws JSONException {


        try {

            JSONObject mainArray = new JSONObject(JsonStr);
            JSONObject mainNode = mainArray.getJSONObject("detalle");
            int_id=Long.parseLong(mainNode.getString("int_id"));
            String rec_id=mainNode.getString("rec_id");
            String imageEvento = mainNode.getString("int_foto");
            String int_titulo=mainNode.getString("int_titulo");
            String ubicacion_evento=mainNode.getString("int_lugar")+" , "+mainNode.getString("pais_desc");
            String cat_desc=mainNode.getString("cat_desc");
            String fecha_inicio_evento = mainNode.getString("int_inicio");
            String fecha_fin_evento= mainNode.getString("int_final");
            String int_desc=mainNode.getString("int_desc");
            inscrito=mainNode.getString("inscrito");
            String disponibles =mainNode.getString("disponibles");

            evento = new EventoModel(int_id,rec_id,imageEvento,ubicacion_evento,cat_desc,fecha_inicio_evento,fecha_fin_evento,int_titulo,int_desc, disponibles);

            insertados = 0;

            if ( evento != null ) {
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


            Activity activity = (Activity) mContext;
            SharedPreferences preferencias =
                    activity.getSharedPreferences(activity.getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
            idUsuario = preferencias.getString(activity.getString(R.string.pref_idusuario), "0");
            sts_id = preferencias.getString(act.getString(R.string.sts_id), "0");

            //Log.i("ID_USR",""+idUsuario);


            Cifrado c = new Cifrado();

            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            String parametro = c.encriptar(SERVICE_ID + "|" + params[0]+"|"+idUsuario);

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
        if(evento == null) {
            Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN INTERNET");
        } else if(insertados == 0) {
            Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
            Log.i("DESCARGA", "SIN DATOS");

            // ejecución para un caso ideal donde todo resulto exitoso
        } else {

            aquery = new AQuery(mContext);
            Activity a = (Activity) mContext;
            ImageView imageEvento=(ImageView) a.findViewById(R.id.imageEvento);
            //TextView rec_id= (TextView) a.findViewById(R.id.evento_nombre);
            TextView nombre_evento= (TextView) a.findViewById(R.id.nombre_evento);
            TextView ubicacion_evento= (TextView) a.findViewById(R.id.ubicacion_evento);
            TextView cat_desc= (TextView) a.findViewById(R.id.cat_desc);
            TextView fecha_inicio_evento= (TextView) a.findViewById(R.id.fecha_inicio_evento);
            TextView fecha_fin_evento= (TextView) a.findViewById(R.id.fecha_fin_evento);
            TextView titulo_evento= (TextView) a.findViewById(R.id.titulo_evento);
            TextView desc_evento= (TextView) a.findViewById(R.id.desc_evento);
            TextView disponibles= (TextView) a.findViewById(R.id.disponibles);
            final ImageButton inscribete=(ImageButton) a.findViewById(R.id.boton_registro_eve);

            if(Integer.parseInt(inscrito)>0){
                inscribete.setBackground(a.getResources().getDrawable(R.drawable.action_eventok_st));
            }
            //inscribete.setImageDrawable((Drawable) a.findViewById(R.drawable.reader));
            /*String pathFoto = evento.getImageEvento();


            if(pathFoto != null && !pathFoto.isEmpty()) {
                aquery.id(R.id.imageEvento).image(evento.getImageEvento());
            }*/

            nombre_evento.setText(evento.getNombre_evento());
            ubicacion_evento.setText(evento.getUbicacion_evento());
            cat_desc.setText(evento.getCat_desc());
            fecha_inicio_evento.setText(evento.getFecha_inicio_evento());
            fecha_fin_evento.setText(evento.getFecha_fin_evento());
            titulo_evento.setText(evento.getTitulo_evento());
            desc_evento.setText(evento.getDesc_evento());
            disponibles.setText(" "+evento.getDisponibles());
            //aquery.id(holder.fotoIV).image(activityRef.getApplicationContext().getString(R.string.base_img)+feriaIntModel.getFotoInt());
            //Log.i("FOTO",""+evento.getImageEvento());
            aquery.id(imageEvento).image(a.getApplicationContext().getString(R.string.base_img)+evento.getImageEvento());

            final ImageButton button1=inscribete;
            inscribete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.i("REG", "CLIC REG****");

                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(aquery.getContext(), button1);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu_evento, popup.getMenu());
                    if(Integer.parseInt(inscrito)>0) {
                        popup.getMenu().getItem(0).setVisible(false);
                    }
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            String tituloItem = (String) item.getTitle();
                            if (item.getItemId() == R.id.item_inscribirse) {

                                if (Integer.parseInt(idUsuario) > 0) {
                                    if (Integer.parseInt(sts_id) == 2)
                                        Toast.makeText(mContext, "Aún no ha sido validado por el sistema de CPTM", Toast.LENGTH_LONG).show();
                                    else {
                                        //Toast.makeText(aquery.getContext(), "*** INSCRIBIRSE *** " + tituloItem, Toast.LENGTH_SHORT).show();
                                        Intent prog = new Intent(act, InscripcionFI.class);
                                        prog.putExtra("id_evento", "" + int_id);
                                        prog.putExtra("id_usr", "" + idUsuario);
                                        act.startActivity(prog);
                                    }
                                } else {
                                    Toast.makeText(aquery.getContext(), "*** DEBE DE REGISTRARSE O INICIAR SESION *** ", Toast.LENGTH_SHORT).show();
                                    /*MainActivity mainActivity= (MainActivity) act;
                                    Login login=new Login();
                                    mainActivity.MuestraFragment(login);*/
                                }
                            }
                            if (item.getItemId() == R.id.item_recinto) {
                                Toast.makeText(aquery.getContext(), "*** RECINTO *** ", Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_programa) {
                                Intent prog = new Intent(act, ProgramaActivity.class);
                                prog.putExtra("id_evento", "" + int_id);
                                act.startActivity(prog);
                            }
                            if (item.getItemId() == R.id.item_ponentes) {
                                Intent prog = new Intent(act, PonentesActivity.class);
                                prog.putExtra("id_evento", "" + int_id);
                                act.startActivity(prog);
                            }
                            if (item.getItemId() == R.id.item_noticias) {
                                Intent prog = new Intent(act, NoticiasActivity.class);
                                prog.putExtra("id_evento", "" + int_id);
                                act.startActivity(prog);
                            }
                            if (item.getItemId() == R.id.item_patrocinador) {
                                Intent prog = new Intent(act, PatrocinadoresActivity.class);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "** "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_expositores) {
                                Intent prog = new Intent(act, Expositores.class);
                                prog.putExtra("id_evento", "" + int_id);
                                act.startActivity(prog);
                                //Toast.makeText(aquery.getContext(), "*EXPOSITORES* "+tituloItem, Toast.LENGTH_SHORT).show();
                            }
                            if (item.getItemId() == R.id.item_lector) {
                                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                                act.startActivity(intent);
                            }

                            return true;
                        }
                    });




                    popup.show();//showing popup menu
                }
            });

        }

    }

}

