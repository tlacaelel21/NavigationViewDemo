package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by root on 4/08/15.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class FeriaIntModel implements Parcelable {
    private long int_id;
    private String mFechaInicio;
    private String mFechaFin;
    private String mNombre;
    private String int_lugar;
    private String pais_desc;
    private String mFoto;

    public FeriaIntModel(long int_id,String int_titulo, String int_lugar, String pais_desc, String foto, String int_inicio, String int_final) {
        this.int_id = int_id;
        this.mNombre = int_titulo;
        this.int_lugar = int_lugar;
        this.pais_desc = pais_desc;
        this.mFechaInicio = int_inicio;
        this.mFechaFin = int_final;
        this.mFoto = foto;
    }

    public long getId() {
        return int_id;
    }

    public String getFechaInicio() {
        return mFechaInicio;
    }

    public String getFechaFin() {
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
        return mFoto;
    }
    // Parcelling part
    public FeriaIntModel(Parcel in){
        String[] data = new String[3];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.int_id = idValue;
        this.mFechaInicio = data[0];
        this.mFechaFin = data[1];
        this.mNombre = data[2];
        this.int_lugar = data[3];
        this.pais_desc = data[4];
        this.mFoto = data[5];
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
                this.mFoto});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeriaIntModel createFromParcel(Parcel in) {
            return new FeriaIntModel(in);
        }

        public FeriaIntModel[] newArray(int size) {
            return new FeriaIntModel[size];
        }
    };

}

