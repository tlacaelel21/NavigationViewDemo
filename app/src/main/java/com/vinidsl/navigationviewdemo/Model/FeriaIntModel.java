package com.vinidsl.navigationviewdemo.Model;

/**
 * Created by root on 4/08/15.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class FeriaIntModel implements Parcelable {

    private long mId;
    private String mFechaInicio;
    private String mFechaFin;
    private String mNombre;
    private String mSala;
    private String mPonNombre;
    private String mPonEmpresa;
    private String mPonPuesto;

    public FeriaIntModel(long id,String pais_desc, String int_lugar, String int_titulo, String foto, String int_final, String int_inicio) {
        this.mId = id;
        this.mFechaInicio = int_inicio;
        this.mFechaFin = int_final;
        this.mNombre = pais_desc;
        this.mSala = int_lugar;
        this.mPonNombre = pais_desc;
        this.mPonEmpresa = int_lugar;
        this.mPonPuesto = foto;
    }

    public long getId() {
        return mId;
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
    public String getUbicacion() {
        return mPonEmpresa;
    }

    public String getSala() {
        return mSala;
    }

    public String getPonNombre() {
        return mPonNombre;
    }

    public String getPonEmpresa() {
        return mPonEmpresa;
    }

    public String getPonPuesto() {
        return mPonPuesto;
    }

    // Parcelling part
    public FeriaIntModel(Parcel in){
        String[] data = new String[3];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.mId = idValue;
        this.mFechaInicio = data[0];
        this.mFechaFin = data[1];
        this.mNombre = data[2];
        this.mSala = data[3];
        this.mPonNombre = data[4];
        this.mPonEmpresa = data[5];
        this.mPonPuesto = data[6];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeStringArray(new String[] {
                this.mFechaInicio,
                this.mFechaFin,
                this.mNombre,
                this.mSala,
                this.mPonNombre,
                this.mPonEmpresa,
                this.mPonPuesto});
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

