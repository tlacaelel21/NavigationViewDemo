package com.vinidsl.navigationviewdemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vinidsl.navigationviewdemo.FotosFragment;
import com.vinidsl.navigationviewdemo.Model.CarruselModel;

import java.util.ArrayList;

/**
 * Created by root on 27/08/15.
 */
public class CarruselAdapter extends FragmentPagerAdapter {

    ArrayList<CarruselModel> programa;

    public CarruselAdapter(FragmentManager fm, ArrayList<CarruselModel> datos) {
        super(fm);
        programa = datos;

        //aquery.id(holder.foto).image("http://desarrollo.smartthinking.com.mx:8080/Cptm/" +feriaIntModel.getFotoInt());
    }

    @Override
    public Fragment getItem(int position) {
        return FotosFragment.newInstance(programa.get(position));
    }

    @Override
    public int getCount() {
        return programa.size();
    }


}
