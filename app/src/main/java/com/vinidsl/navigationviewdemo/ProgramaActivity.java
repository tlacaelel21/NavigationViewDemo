package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Adapter.ProgramasAdapter;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.Noticia;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 07/08/2015.
 */
public class ProgramaActivity extends AppCompatActivity {

    private LinearLayout mIndicatorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[][] horarios = {
                {"1", "08:00", "Nombre del evento", "Sala"},
                {"2", "10:00", "Nombre del evento", "Sala"},
                {"3", "12:00", "Nombre del evento", "Sala"},
                {"4", "14:00", "Nombre del evento", "Sala"},
                {"5", "16:00", "Nombre del evento", "Sala"},
                {"6", "18:00", "Nombre del evento", "Sala"}
        };

        ArrayList<ArrayList<Horario>> listamM = new ArrayList<ArrayList<Horario>>();

        for(int j=0; j < 5; j++) {
            ArrayList<Horario> listaM = new ArrayList<Horario>();
            for (int i=0; i< horarios.length; i++) {
                long id = Long.parseLong(horarios[i][0]);
                String horario = "", nombre = "", sala = "";
                horario = horarios[i][1];
                nombre = horarios[i][2];
                sala = horarios[i][3];
                Horario objeto = new Horario(id, horario, nombre, sala);
                listaM.add(objeto);
            }
            listamM.add(listaM);
        }


        mIndicatorContainer = (LinearLayout) findViewById(R.id.programa_paginador);
        ViewPager pager = (ViewPager) findViewById(R.id.programa_contenedor);
        ProgramasAdapter adapter =
                new ProgramasAdapter(getSupportFragmentManager(), listamM);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                dibujarSeleccion(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dibujarPaginas(listamM.size());

    }

    public void dibujarPaginas(int totalPages) {
        for(int i = 0; i<totalPages; i++) {
            ImageView indicador = new ImageView(this);
            indicador.setImageResource(R.drawable.pager_indicator);
            LayoutParams params = new LayoutParams(20, 20);
            params.leftMargin = 5;
            params.rightMargin = 5;
            indicador.setLayoutParams(params);
            mIndicatorContainer.addView(indicador);
        }
        dibujarSeleccion(0);
    }

    public void dibujarSeleccion(int seleccion) {
        for(int i = 0; i< mIndicatorContainer.getChildCount() ; i++) {
            ImageView indicador = (ImageView) mIndicatorContainer.getChildAt(i);
            indicador.setImageResource(R.drawable.pager_indicator);
            if(i ==  seleccion)
                indicador.setImageResource(R.drawable.pager_indicator_active);
        }
        ((TextView) findViewById(R.id.programa_cab_indicador_dia))
                .setText(String.format(getString(R.string.programa_list_cab_dia), seleccion + 1));
    }



}
