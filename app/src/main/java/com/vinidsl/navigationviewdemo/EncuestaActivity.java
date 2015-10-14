package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.vinidsl.navigationviewdemo.Adapter.PatrocinadoresAdapter;
import com.vinidsl.navigationviewdemo.Model.Patrocinador;
import com.vinidsl.navigationviewdemo.Model.Pregunta;
import com.vinidsl.navigationviewdemo.Model.Respuesta;
import com.vinidsl.navigationviewdemo.Tasks.EncuestasTask;
import com.vinidsl.navigationviewdemo.Tasks.NoticiasTask;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class EncuestaActivity extends AppCompatActivity {

    private final static String TIPO_INPUT_TEXT = "3";
    private final static String TIPO_COMBO_BOX = "4";
    private final static String TIPO_CHECK_BOX = "2";
    private final static String TIPO_MULTI_CHECK = "1";
    private final static String TIPO_PICKER = "0";

    private ArrayList<Pregunta> preguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button boton = (Button) findViewById(R.id.encuetsa_boton_terminar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vp = (View) v.getParent();
                enviarRespuestas(vp);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id_evento=extras.getString("id_evento");

        EncuestasTask task = new EncuestasTask(this);
        task.execute(id_evento);

    }

    public void llenaLista(ArrayList<Pregunta> pregs) {
        preguntas = pregs;
    }

    public void enviarRespuestas(View v) {

        LinearLayout contenedor = (LinearLayout) v.findViewById(R.id.encuesta_preguntas_container);

        //String[] respuestas = new String[preguntas.size()];
        int indice = 0;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id_evento=extras.getString("id_evento");
        String parametro = id_evento;

        for(int i = 1; i < contenedor.getChildCount(); i=i+2) {

            Log.i("PREGUNTA", "-" + i);

            switch (preguntas.get(indice).getTipo()) {
                case TIPO_INPUT_TEXT:
                    EditText inputET = (EditText) contenedor.getChildAt(i);
                    parametro = parametro
                            + "|" +
                            preguntas.get(indice).getId() // id_pregunta
                            + "|" +
                            "0" // id_respuesta
                            + "|" +
                            inputET.getText().toString(); // respuesta
                    //respuestas[indice] = inputET.getText().toString();
                    break;
                case TIPO_CHECK_BOX:
                    RadioGroup group = (RadioGroup) contenedor.getChildAt(i);
                    int indexG = group.getCheckedRadioButtonId();
                    //Log.i("id", "ASD " + indexG + "/" + preguntas.get(indice).getRespuestas().size());
                    String respTextG = preguntas.get(indice).getRespuestas().get(indexG).getRespuesta();
                    long respIdG = preguntas.get(indice).getRespuestas().get(indexG).getId();
                    parametro = parametro
                            + "|" +
                            preguntas.get(indice).getId() // id_pregunta
                            + "|" +
                            respIdG // id_respuesta
                            + "|" +
                            respTextG; // respuesta
                    break;
                case TIPO_MULTI_CHECK:

                    break;
                case TIPO_COMBO_BOX:
                    Spinner inputSP = (Spinner) contenedor.getChildAt(i);
                    parametro = parametro
                            +  "|" +
                            preguntas.get(indice).getId() // id_pregunta
                            + "|" +
                            "0" // id_respuesta
                            + "|" +
                            inputSP.getSelectedItem().toString(); // respuesta
                    //respuestas[indice] = inputSP.getSelectedItem().toString();
                    break;
                case TIPO_PICKER:

                    break;
            }

            indice++;


        }

        Log.i("resp", parametro);

        /*
            id_evento|id_pregunta|id_respuesta|
            texto_respuesta@@@id_evento|id_pregunta|id_respuesta|texto_respuesta

        */

    }

}