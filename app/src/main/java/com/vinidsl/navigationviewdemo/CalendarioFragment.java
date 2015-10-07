package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinidsl.navigationviewdemo.Tasks.CalendarioTask;
import com.vinidsl.navigationviewdemo.Tasks.FeriaNacTask;

/**
 * Created by tlacaelel21 on 6/10/15.
 */
public class CalendarioFragment extends Fragment {
    View rootView;
    public CalendarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.calendario_fragment, container, false);


        CalendarioTask calendarioTask=new CalendarioTask(getActivity());
        calendarioTask.execute();


        return rootView;
    }

}
