package com.vinidsl.navigationviewdemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vinidsl.navigationviewdemo.AgendaFragment;
import com.vinidsl.navigationviewdemo.Model.Cita;
import com.vinidsl.navigationviewdemo.Model.Sala;
import com.vinidsl.navigationviewdemo.ProgramaFragment;

import java.util.ArrayList;

public class AgendaAdapter extends FragmentPagerAdapter {

    ArrayList<ArrayList<Cita>> lista;
    ArrayList<Sala> salas;

    public AgendaAdapter(FragmentManager fm, ArrayList<ArrayList<Cita>> datos,
                         ArrayList<Sala> valores) {
        super(fm);
        lista = datos;
        salas = valores;
    }

    @Override
    public Fragment getItem(int position) {

        return AgendaFragment.newInstance(lista.get(position), salas);
    }

    @Override
    public int getCount() {
        return lista.size();
    }
}