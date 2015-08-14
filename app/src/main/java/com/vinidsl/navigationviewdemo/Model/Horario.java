package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JoseRogelio on 11/08/2015.
 */
public class Horario implements Parcelable {

    private long mId;
    private String mHorario;
    private String mNombre;
    private String mSala;

    public Horario(long id, String horario, String nombre, String sala) {
        this.mId = id;
        this.mHorario = horario;
        this.mNombre = nombre;
        this.mSala = sala;
    }

    public long getId() {
        return mId;
    }

    public String getHorario() {
        return mHorario;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getSala() {
        return mSala;
    }

    // Parcelling part
    public Horario(Parcel in){
        String[] data = new String[3];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.mId = idValue;
        this.mHorario = data[0];
        this.mNombre = data[1];
        this.mSala = data[2];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeStringArray(new String[] {
                this.mHorario,
                this.mNombre,
                this.mSala});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Horario createFromParcel(Parcel in) {
            return new Horario(in);
        }

        public Horario[] newArray(int size) {
            return new Horario[size];
        }
    };

}
