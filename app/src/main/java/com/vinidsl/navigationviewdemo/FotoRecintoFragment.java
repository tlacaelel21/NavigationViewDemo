package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;

/**
 * Created by root on 27/08/15.
 */
public class FotoRecintoFragment extends Fragment {

    AQuery aquery = new AQuery(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foto_recinto, container, false);


        String url = getArguments().getString("valor");

        ImageView fotoIV = (ImageView) v.findViewById(R.id.pagina_foto_imagen);

        String path=v.getContext().getString(R.string.base_img) + url;

        aquery.id(fotoIV).image(path);

        return v;
    }

    public static FotoRecintoFragment newInstance(String objeto) {

        FotoRecintoFragment f = new FotoRecintoFragment();
        Bundle b = new Bundle();
        b.putString("valor", objeto);
        f.setArguments(b);

        return f;
    }
}

