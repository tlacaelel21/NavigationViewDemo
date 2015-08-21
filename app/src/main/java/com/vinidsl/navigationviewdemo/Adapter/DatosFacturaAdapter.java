package com.vinidsl.navigationviewdemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vinidsl.navigationviewdemo.DatosFacturaFragment;
import com.vinidsl.navigationviewdemo.Model.DatosFactura;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.ProgramaFragment;

import java.util.ArrayList;

public class DatosFacturaAdapter extends FragmentPagerAdapter {

    ArrayList<DatosFactura> datosFac;

    public DatosFacturaAdapter(FragmentManager fm, ArrayList<DatosFactura> datos) {
        super(fm);
        datosFac = datos;
    }

    @Override
    public Fragment getItem(int position) {
        return DatosFacturaFragment.newInstance(datosFac.get(position));
    }

    @Override
    public int getCount() {
        return datosFac.size();
    }
}