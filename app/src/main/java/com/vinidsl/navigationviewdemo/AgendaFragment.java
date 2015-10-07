package com.vinidsl.navigationviewdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.vinidsl.navigationviewdemo.Adapter.HorarioAdapter;
import com.vinidsl.navigationviewdemo.Model.Cita;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Model.Sala;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class AgendaFragment extends Fragment {

    ArrayList<Sala> salas;

    @Override
    public View onCreateView(LayoutInflater inflater,
                 ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agenda, container, false);

        salas = getArguments().getParcelableArrayList("salas");

        ArrayList<Cita> lista = getArguments().getParcelableArrayList("valores");

        for(int i = 0; i < lista.size(); i++) {

            ImageButton boton = null;

            switch (getHora(lista.get(i).getFecha())) {
                case 7:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "7 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_7_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "7 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_7_2);
                    } else {
                        Log.i("BOTON", "7 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_7_3);
                    }
                    break;
                case 8:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "8 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_8_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "8 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_8_2);
                    } else {
                        Log.i("BOTON", "8 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_8_3);
                    }
                    break;
                case 9:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "9 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_9_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "9 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_9_2);
                    } else {
                        Log.i("BOTON", "9 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_9_3);
                    }
                    break;
                case 10:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "10 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_10_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "10 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_10_2);
                    } else {
                        Log.i("BOTON", "10 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_10_3);
                    }
                    break;
                case 11:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "11 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_11_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "11 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_11_2);
                    } else {
                        Log.i("BOTON", "11 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_11_3);
                    }
                    break;
                case 12:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "12 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_12_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "12 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_12_2);
                    } else {
                        Log.i("BOTON", "12 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_12_3);
                    }
                    break;
                case 13:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "13 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_13_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "13 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_13_2);
                    } else {
                        Log.i("BOTON", "13 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_13_3);
                    }
                    break;
                case 14:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "14 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_14_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "14 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_14_2);
                    } else {
                        Log.i("BOTON", "14 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_14_3);
                    }
                    break;
                case 15:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "15 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_15_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "15 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_15_2);
                    } else {
                        Log.i("BOTON", "15 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_15_3);
                    }
                    break;
                case 16:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "16 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_16_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "16 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_16_2);
                    } else {
                        Log.i("BOTON", "16 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_16_3);
                    }
                    break;
                case 17:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "17 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_17_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "17 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_17_2);
                    } else {
                        Log.i("BOTON", "17 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_17_3);
                    }
                    break;
                case 18:
                    if(salas.get(0).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "18 1");
                        boton = (ImageButton) v.findViewById(R.id.celda_18_1);
                    } else if(salas.get(1).getId().equals(lista.get(i).getSala())) {
                        Log.i("BOTON", "18 2");
                        boton = (ImageButton) v.findViewById(R.id.celda_18_2);
                    } else {
                        Log.i("BOTON", "18 3");
                        boton = (ImageButton) v.findViewById(R.id.celda_18_3);
                    }
                    break;
            }

            try {
                if (lista.get(i).getConfirmado().equals("1")) {
                    boton.setBackgroundColor(Color.GREEN);
                } else if (lista.get(i).getConfirmado().equals("2")) {
                    boton.setBackgroundColor(Color.YELLOW);
                } else if (!lista.get(i).getConfirmado().equals("0")) {
                    boton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Activity activity = getActivity();

                            Toast.makeText(activity, "ENTRO", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } catch(NullPointerException e) {
                Log.i("BOTON", lista.get(i).getFecha());
            }

        }

        return v;
    }

    public static AgendaFragment newInstance(ArrayList<Cita> lista,
                                             ArrayList<Sala> listaS) {

        AgendaFragment f = new AgendaFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("valores", lista);
        b.putParcelableArrayList("salas", listaS);
        f.setArguments(b);

        return f;
    }

    public int getHora(String fecha) {
        String[] splitf = fecha.split(" ");
        String[] splith = splitf[1].split(Pattern.quote(":"));
        return Integer.parseInt(splith[0]);
    }
}
