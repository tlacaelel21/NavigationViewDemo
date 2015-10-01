package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Tasks.DetallePonenteTask;
import com.vinidsl.navigationviewdemo.Tasks.RecintoTask;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class RecintoActivity extends AppCompatActivity {

    private LinearLayout mIndicatorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recinto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager pager = (ViewPager) findViewById(R.id.recinto_fotos_paginador);

        mIndicatorContainer = (LinearLayout) findViewById(R.id.recinto_contenedor_paginador);

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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {

            String idEvento = extras.getString("id_evento");

            RecintoTask task = new RecintoTask(this);
            task.execute(idEvento);
        }

    }

    public void dibujarPaginas(int totalPages) {
        for(int i = 0; i<totalPages; i++) {
            ImageView indicador = new ImageView(this);
            indicador.setImageResource(R.drawable.pager_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
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

    }

}
