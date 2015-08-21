package com.vinidsl.navigationviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Adapter.HorarioAdapter;
import com.vinidsl.navigationviewdemo.Model.DatosFactura;
import com.vinidsl.navigationviewdemo.Model.Horario;
import com.vinidsl.navigationviewdemo.Tasks.ActualizaDatosFacturaTask;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class DatosFacturaFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater,
                 ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datos_fiscales, container, false);

        Bundle args = getArguments();
        TextView rfcTV = (TextView) v.findViewById(R.id.perfil_rfc);
        TextView rsTV = (TextView) v.findViewById(R.id.perfil_rs);
        TextView calleFacTV = (TextView) v.findViewById(R.id.perfil_calle_fac);
        TextView numExtTV = (TextView) v.findViewById(R.id.perfil_num_ext_fac);
        TextView numIntTV = (TextView) v.findViewById(R.id.perfil_num_int_fac);
        TextView coloniaFacTV = (TextView) v.findViewById(R.id.perfil_colonia_fac);
        TextView delegacionFacTV = (TextView) v.findViewById(R.id.perfil_delegacion_fac);
        TextView cpFacTV = (TextView) v.findViewById(R.id.perfil_codigo_postal_fac);
        TextView estadoTV = (TextView) v.findViewById(R.id.perfil_estado_fac);
        Button boton = (Button) v.findViewById(R.id.datos_fiscales_boton_actualizar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vp = (View) v.getParent();
                callUpdate(vp);
            }
        });

        rfcTV.setText(args.getString("rfc"));
        rsTV.setText(args.getString("rs"));
        calleFacTV.setText(args.getString("calle"));
        numExtTV.setText(args.getString("numExt"));
        numIntTV.setText(args.getString("numInt"));
        coloniaFacTV.setText(args.getString("colonia"));
        delegacionFacTV.setText(args.getString("municipio"));
        cpFacTV.setText(args.getString("cp"));
        estadoTV.setText(args.getString("estado"));

        return v;
    }

    public static DatosFacturaFragment newInstance(DatosFactura df) {

        DatosFacturaFragment f = new DatosFacturaFragment();
        Bundle b = new Bundle();
        b.putString("calle", df.getCalle());
        b.putString("cp", df.getCodigoPostal());
        b.putString("numExt", df.getNumExt());
        b.putString("numInt", df.getNumInt());
        b.putString("colonia", df.getColonia());
        b.putString("municipio", df.getMunicipio());
        b.putString("estado", df.getEstado());
        b.putString("rfc", df.getRfc());
        b.putString("rs", df.getRs());
        b.putLong("id", df.getId());
        f.setArguments(b);

        return f;
    }

    public void callUpdate(View v) {

        TextView rfcTV = (TextView) v.findViewById(R.id.perfil_rfc);
        TextView rsTV = (TextView) v.findViewById(R.id.perfil_rs);
        TextView calleFacTV = (TextView) v.findViewById(R.id.perfil_calle_fac);
        TextView numExtTV = (TextView) v.findViewById(R.id.perfil_num_ext_fac);
        TextView numIntTV = (TextView) v.findViewById(R.id.perfil_num_int_fac);
        TextView coloniaFacTV = (TextView) v.findViewById(R.id.perfil_colonia_fac);
        TextView delegacionFacTV = (TextView) v.findViewById(R.id.perfil_delegacion_fac);
        TextView cpFacTV = (TextView) v.findViewById(R.id.perfil_codigo_postal_fac);
        TextView estadoTV = (TextView) v.findViewById(R.id.perfil_estado_fac);


        String idDatosFactura = "1";
        String parametro = "";

        ActualizaDatosFacturaTask task = new ActualizaDatosFacturaTask(getActivity());
        task.execute(parametro);

    }
}
