package com.vinidsl.navigationviewdemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.ProgramaFragment;

import java.util.ArrayList;

public class ProgramasAdapter extends FragmentPagerAdapter {

    ArrayList<ArrayList<Horario>> programa;

    public ProgramasAdapter(FragmentManager fm, ArrayList<ArrayList<Horario>> datos) {
        super(fm);
        programa = datos;
    }

    @Override
    public Fragment getItem(int position) {

        return ProgramaFragment.newInstance(programa.get(position));
    }

    @Override
    public int getCount() {
        return programa.size();
    }
}