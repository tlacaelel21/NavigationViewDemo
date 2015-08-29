package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 10/08/15.
 */
public class FeriasNacModel implements Parcelable {
    private long int_id;
    private String titulo;
    private String nac_foto;
    private String urlExterno;

    //new FeriasNacModel(id,int_titulo,int_lugar,pais_desc,foto,int_inicio,int_final);
    public FeriasNacModel(long nac_id,String nac_titulo,String nac_foto,String nac_url) {
        this.titulo = nac_titulo;
        this.nac_foto = nac_foto;
        this.urlExterno=nac_url;
    }

    public long getId() {
        return int_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNac_foto() {
        return nac_foto;
    }

    public String getUrlExterno() {
        return urlExterno;
    }

    // Parcelling part
    public FeriasNacModel(Parcel in){
        String[] data = new String[3];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.int_id = idValue;
        this.titulo = data[0];
        this.nac_foto = data[1];
        this.urlExterno = data[2];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.int_id);
        dest.writeStringArray(new String[] {
                this.titulo,
                this.nac_foto,
                this.urlExterno});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FeriasNacModel createFromParcel(Parcel in) {
            return new FeriasNacModel(in);
        }

        public FeriasNacModel[] newArray(int size) {
            return new FeriasNacModel[size];
        }
    };

}

