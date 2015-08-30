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
import com.vinidsl.navigationviewdemo.Tasks.EliminaDatosFacturaTask;

import java.util.ArrayList;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class DatosFacturaFragment extends Fragment {

    Long id;

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
        Button botonGuardar = (Button) v.findViewById(R.id.datos_fiscales_boton_actualizar);
        Button botonEliminar = (Button) v.findViewById(R.id.datos_fiscales_boton_eliminar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vp = (View) v.getParent();
                callUpdate(vp);
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDelete();
            }
        });

        id = args.getLong("id");
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

        String idDatosFactura = "" + id;

        String rfc = rfcTV.getText().toString();
        String rs= rsTV.getText().toString();
        String calle = calleFacTV.getText().toString();
        String numExt = numExtTV.getText().toString();
        String numInt = numIntTV.getText().toString();
        String colonia = coloniaFacTV.getText().toString();
        String cp = cpFacTV.getText().toString();
        String municipio = delegacionFacTV.getText().toString();
        String estado = estadoTV.getText().toString();
        String poblacion = "";

        /*

                id_datosfactura|rfc|razon_social|calle|noext|noint|colonia|cp|municipio|
                poblacion|estado
         */
        String parametro = idDatosFactura + "|" + rfc + "|" + rs + "|" + calle + "|" +
                numExt + "|" + numInt + "|" + colonia + "|" + cp + "|" + municipio + "|" +
                "|" + poblacion + "|" + estado;

        ActualizaDatosFacturaTask task = new ActualizaDatosFacturaTask(getActivity());
        task.execute(parametro);

    }

    public void callDelete() {
        String parametro = "" + id;
        EliminaDatosFacturaTask task = new EliminaDatosFacturaTask(getActivity());
        task.execute(parametro);
    }
}
