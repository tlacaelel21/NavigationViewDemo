package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tlacaelel21 on 6/10/15.
 */
public class CalendarioModel implements Parcelable {
    private long int_id;
    private String titulo;
    private String urlExterno;

    public CalendarioModel(String cal_titulo,String cal_url) {
        this.titulo = cal_titulo;
        this.urlExterno=cal_url;
        this.int_id=0;
    }

    public long getId() {
        return int_id;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public String getUrlExterno() {
        return urlExterno;
    }

    // Parcelling part
    public CalendarioModel(Parcel in){
        String[] data = new String[2];
        in.readStringArray(data);
        this.titulo = data[0];
        this.urlExterno = data[1];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.titulo,
                this.urlExterno});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CalendarioModel createFromParcel(Parcel in) {
            return new CalendarioModel(in);
        }

        public CalendarioModel[] newArray(int size) {
            return new CalendarioModel[size];
        }
    };

}


