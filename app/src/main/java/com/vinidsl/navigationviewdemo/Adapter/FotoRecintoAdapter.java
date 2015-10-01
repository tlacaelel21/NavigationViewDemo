package com.vinidsl.navigationviewdemo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vinidsl.navigationviewdemo.FotoRecintoFragment;
import com.vinidsl.navigationviewdemo.FotosFragment;

import java.util.ArrayList;

/**
 * Created by root on 27/08/15.
 */
public class FotoRecintoAdapter extends FragmentPagerAdapter {

    ArrayList<String> fotos;

    public FotoRecintoAdapter(FragmentManager fm, ArrayList<String> datos) {
        super(fm);
        fotos = datos;

    }

    @Override
    public Fragment getItem(int position) {
        return FotoRecintoFragment.newInstance(fotos.get(position));
    }

    @Override
    public int getCount() {
        return fotos.size();
    }


}
