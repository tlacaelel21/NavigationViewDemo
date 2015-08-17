package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 31/07/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.vinidsl.navigationviewdemo.Adapter.NoticiasAdapter;
import com.vinidsl.navigationviewdemo.Model.Noticia;

import java.util.ArrayList;

public class NoticiasFragment extends Fragment {

    View rootView;

    public NoticiasFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);

        String[][] noticias = {
                {"-1", "Julio, 2015", "Ir a sitio" , "http://farm8.static.flickr.com/7281/8718400698_dbf9d7d2ae_m.jpg"},
                {"1", "Título del articulo", "Julio 15, 2015" , "https://disability.workforce3one.org/ws/disability/images/thumbs/newspaper.jpg"},
                {"2", "Título del articulo", "Julio 16, 2015" , "https://disability.workforce3one.org/ws/disability/images/thumbs/newspaper.jpg"},
                {"3", "Título del articulo", "Julio 18, 2015" , "https://disability.workforce3one.org/ws/disability/images/thumbs/newspaper.jpg"},
                {"-1", "Agosto, 2015", "Ir a sitio" , "http://farm8.static.flickr.com/7281/8718400698_dbf9d7d2ae_m.jpg"},
                {"4", "Título del articulo", "Agosto 8, 2015" , "https://disability.workforce3one.org/ws/disability/images/thumbs/newspaper.jpg"},
        };

        ArrayList<Noticia> listaM = new ArrayList<Noticia>();

        for (int i=0; i< noticias.length; i++) {
            long id = Long.parseLong(noticias[i][0]);
            String titulo = "", fecha = "", path = "", contenido = "";
            titulo = noticias[i][1];
            fecha = noticias[i][2];
            path = noticias[i][3];
            contenido = "";
            Noticia objeto = new Noticia(id, titulo, fecha, path, contenido);
            listaM.add(objeto);
        }

        ListView lista = (ListView)rootView.findViewById(R.id.noticias_lista);

        NoticiasAdapter adapter = new NoticiasAdapter((Activity) getActivity(), listaM);
        lista.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}