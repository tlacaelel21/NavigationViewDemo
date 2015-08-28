package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 27/08/15.
 */
public class CarruselModel implements Parcelable {

    private long mId;
    private String mFechaInicio;
    private String mFechaFin;
    private String mNombre;
    private String mFoto;
    private String mPonNombre;
    private String mPonEmpresa;
    private String mPonPuesto;

    public CarruselModel(long id, String fechaInicio, String fechaFin, String nombre, String foto,
                   String ponNombre, String ponEmpresa, String ponPuesto) {
        this.mId = id;
        this.mFechaInicio = fechaInicio;
        this.mFechaFin = fechaFin;
        this.mNombre = nombre;
        this.mFoto = foto;
        this.mPonNombre = ponNombre;
        this.mPonEmpresa = ponEmpresa;
        this.mPonPuesto = ponPuesto;
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

    public String getNombreL() {
        return mNombre;
    }

    public String getmFoto() {
        return mFoto;
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

    /*long id, String fechaInicio, String fechaFin, String nombre, String foto,
                   String ponNombre, String ponEmpresa, String ponPuesto*/
    // Parcelling part
    public CarruselModel(Parcel in){
        String[] data = new String[7];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.mId = idValue;
        this.mFechaInicio = data[0];
        this.mFechaFin = data[1];
        this.mNombre = data[2];
        this.mFoto = data[3];
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
                this.mFoto,
                this.mPonNombre,
                this.mPonEmpresa,
                this.mPonPuesto});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CarruselModel createFromParcel(Parcel in) {
            return new CarruselModel(in);
        }

        public CarruselModel[] newArray(int size) {
            return new CarruselModel[size];
        }
    };

}
