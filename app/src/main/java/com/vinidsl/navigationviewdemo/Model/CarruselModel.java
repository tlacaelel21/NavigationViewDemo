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
    private String mSala;
    private String mPonNombre;
    private String mPonEmpresa;
    private String mPonPuesto;

    public CarruselModel(long id, String fechaInicio, String fechaFin, String nombre, String sala,
                   String ponNombre, String ponEmpresa, String ponPuesto) {
        this.mId = id;
        this.mFechaInicio = fechaInicio;
        this.mFechaFin = fechaFin;
        this.mNombre = nombre;
        this.mSala = sala;
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

    public String getNombre() {
        return mNombre;
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
    public CarruselModel(Parcel in){
        String[] data = new String[7];
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
        public CarruselModel createFromParcel(Parcel in) {
            return new CarruselModel(in);
        }

        public CarruselModel[] newArray(int size) {
            return new CarruselModel[size];
        }
    };

}
