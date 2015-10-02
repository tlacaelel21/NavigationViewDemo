package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JoseRogelio on 01/10/2015.
 */
public class Cita implements Parcelable {

    String id;
    String fecha;
    String usuario;
    String sala;
    String confirmado;

    public Cita(String id, String fecha, String usuario, String sala, String confirmado) {
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.sala = sala;
        this.confirmado = confirmado;
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSala() {
        return sala;
    }

    public String getConfirmado() {
        return confirmado;
    }


    // Parcelling part
    public Cita(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        this.id = data[0];
        this.fecha = data[1];
        this.usuario = data[2];
        this.sala = data[3];
        this.confirmado = data[4];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.id,
                this.fecha,
                this.usuario,
                this.sala,
                this.confirmado});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cita createFromParcel(Parcel in) {
            return new Cita(in);
        }

        public Cita[] newArray(int size) {
            return new Cita[size];
        }
    };

}
