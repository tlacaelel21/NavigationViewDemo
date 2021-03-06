package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 24/07/15.
 */
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Adapter.FeriasIntAdapter;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;
import com.vinidsl.navigationviewdemo.Model.FeriaIntModel;
import com.vinidsl.navigationviewdemo.Tasks.CarruselTask;
import com.vinidsl.navigationviewdemo.Tasks.FeriaInterTask;
import com.vinidsl.navigationviewdemo.Tasks.TopTask;

import java.util.ArrayList;
import java.util.List;

public class Pantalla_Princi extends Fragment {
    EditText edittext;
    private LinearLayout mIndicatorContainer;
    View rootView;
    Context context;
    ViewPager viewPager;

    public Pantalla_Princi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.vista_principal, container, false);

        //viewPager= (ViewPager) rootView.findViewById(R.id.fotos_contenedor);

        /*viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("CARRUSEL", "CLIC CLIC");
            }
        });*/

        /*String[][] ferias = {
                {"feria 1","Paris"},
                {"feria 2","Tokio"},
                {"feria 3","Austria"},
                {"feria 4","España"},
                {"feria 5","Argentina"},
        };


        List<FeriaIntModel> listaM = new ArrayList<FeriaIntModel>();

        for (int i=0; i< ferias.length; i++) {
            String nombre = "", ubicacion = "";
            nombre = ferias[i][0];
            ubicacion = ferias[i][1];
            long id=0;
            String pais_desc="", int_lugar="", int_titulo="", foto="", int_final="", int_inicio="";
            FeriaIntModel objeto = new FeriaIntModel(id, nombre,ubicacion,pais_desc,int_titulo,foto,int_final);
            listaM.add(objeto);
        }

        ListView lista = (ListView)rootView.findViewById(R.id.lista_eventos);

        FeriasIntAdapter adapter = new FeriasIntAdapter(getActivity(), R.layout.ferias_item,
                listaM);
        lista.setAdapter(adapter);

*/

        mIndicatorContainer = (LinearLayout) rootView.findViewById(R.id.carrucel_paginador);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.fotos_contenedor);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dibujarSeleccion(position);
            }

            @Override
            public void onPageSelected(int position) {

                //dibujarSeleccion(position);
                Log.i("FFF", "ENTRO");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("TEST", "CLIC2 CLIC2");
            }
        });*/

        /*FeriaInterTask feriasTask= new FeriaInterTask(getActivity());
        feriasTask.execute("");*/
        TopTask feriasTask= new TopTask(getActivity());
        feriasTask.execute("");


        CarruselTask principalTask= new CarruselTask(getActivity());
        principalTask.setFragment(this);
        principalTask.execute("");


        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextInputLayout
    }
    //public void dibujarPaginas(int totalPages) {
    public void dibujarPaginas(int totalPages, Context c){
        for(int i = 0; i<totalPages; i++) {
            ImageView indicador = new ImageView(c);
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
        /*((TextView) findViewById(R.id.programa_cab_indicador_dia))
                .setText(String.format(getString(R.string.programa_list_cab_dia), seleccion + 1));*/
    }


    /*public static ProgramaFragment newInstance(ArrayList<CarruselModel> lista) {

        ProgramaFragment f = new ProgramaFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("valores", lista);
        f.setArguments(b);

        return f;
    }*/

}