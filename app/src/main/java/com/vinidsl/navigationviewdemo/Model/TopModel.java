package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tlacaelel21 on 30/09/15.
 */
public class TopModel implements Parcelable {
    private long int_id;
    private String mFechaInicio;
    private String mFechaFin;
    private String mNombre;
    private String int_lugar;
    private String pais_desc;
    private String mFotoInt;
    //new FeriaIntModel(id,int_titulo,int_lugar,pais_desc,foto,int_inicio,int_final);
    public TopModel(long int_id,String int_titulo, String int_lugar, String pais_desc, String foto_int, String int_inicio, String int_final) {
        this.int_id = int_id;
        this.mNombre = int_titulo;
        this.int_lugar = int_lugar;
        this.pais_desc = pais_desc;
        this.mFechaInicio = int_inicio;
        this.mFechaFin = int_final;
        this.mFotoInt = foto_int;
    }

    public long getId() {
        return int_id;
    }

    public String getFechaInicio() {
        String dia="", mes="";
        String Mes[]={"ENE", "FEB", "MZO", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
        if(null!=mFechaInicio&&mFechaInicio.length()>6) {
            String fecha[] = mFechaInicio.split("-");
            dia=fecha[2];
            mes=Mes[Integer.parseInt(fecha[1])-1];
            mFechaInicio=dia+" "+mes;
        }
        return mFechaInicio;
    }

    public String getFechaFin() {
        String dia="", mes="";
        String Mes[]={"ENE", "FEB", "MZO", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
        if(null!=mFechaFin&&mFechaFin.length()>6) {
            String fecha[] = mFechaFin.split("-");
            dia=fecha[2];
            mes=Mes[Integer.parseInt(fecha[1])-1];
            mFechaFin=dia+" "+mes;
        }
        return mFechaFin;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getInt_lugar() {
        return int_lugar;
    }

    public String getPais_desc() {
        return pais_desc;
    }

    public String getFotoInt() {
        return mFotoInt;
    }
    // Parcelling part
    public TopModel(Parcel in){
        String[] data = new String[3];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.int_id = idValue;
        this.mFechaInicio = data[0];
        this.mFechaFin = data[1];
        this.mNombre = data[2];
        this.int_lugar = data[3];
        this.pais_desc = data[4];
        this.mFotoInt = data[5];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.int_id);
        dest.writeStringArray(new String[] {
                this.mFechaInicio,
                this.mFechaFin,
                this.mNombre,
                this.int_lugar,
                this.pais_desc,
                this.mFotoInt});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TopModel createFromParcel(Parcel in) {
            return new TopModel(in);
        }

        public TopModel[] newArray(int size) {
            return new TopModel[size];
        }
    };

}


