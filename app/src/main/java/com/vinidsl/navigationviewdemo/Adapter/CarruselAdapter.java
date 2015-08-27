package com.vinidsl.navigationviewdemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vinidsl.navigationviewdemo.FotosFragmente;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Pantalla_Princi;
import com.vinidsl.navigationviewdemo.ProgramaFragment;

import java.util.ArrayList;

/**
 * Created by root on 27/08/15.
 */
public class CarruselAdapter extends FragmentPagerAdapter {

    ArrayList<CarruselModel> programa;

    public CarruselAdapter(FragmentManager fm, ArrayList<CarruselModel> datos) {
        super(fm);
        programa = datos;
    }

    @Override
    public Fragment getItem(int position) {

        //return FotosFragmente.newInstance(programa.get(position));
        return new Fragment();//esto solo lo puse para q me dejara hacer el push

    }

    @Override
    public int getCount() {
        return programa.size();
    }
}
